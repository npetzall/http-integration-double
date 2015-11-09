package npetzall.hid.response;

import com.sun.net.httpserver.HttpExchange;
import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.response.HIDResponse;
import npetzall.hid.exchange.HIDExchangeContextImpl;
import npetzall.hid.io.SlowOutputStreamWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HIDResponseDecorator {

    private long delayBeforeStatusResponse = 0;
    private int statusCode = 200;
    private long delayBeforeBody = 0;
    private long timeToWriteResponseBody = 0;
    private boolean shouldClose = true;

    private final HIDResponse hidResponse;

    public HIDResponseDecorator(HIDResponse hidResponse) {
        this.hidResponse = hidResponse;
    }

    public HIDResponseDecorator delayBeforeStatusResponse(long delayBeforeStatusResponse) {
        this.delayBeforeStatusResponse = delayBeforeStatusResponse;
        return this;
    }

    public HIDResponseDecorator statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public HIDResponseDecorator delayBeforeBody(long delayBeforeBody) {
        this.delayBeforeBody = delayBeforeBody;
        return this;
    }

    public HIDResponseDecorator timeToWriteResponseBody(long timeToWriteResponseBody) {
        this.timeToWriteResponseBody = timeToWriteResponseBody;
        return this;
    }

    public HIDResponseDecorator shouldClose(boolean shouldClose) {
        this.shouldClose = shouldClose;
        return this;
    }

    @Deprecated
    public InputStream getInputStream(HIDExchangeContext hidExchangeContext) {
        return hidResponse.getInputStream(hidExchangeContext);
    }

    public void writeToOutputStream(HIDExchangeContextImpl exchangeContext, HttpExchange httpExchange) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final InputStream responseIn = hidResponse.getInputStream(exchangeContext);
        final byte[] buff = new byte[4096];
        int read;
        while((read = responseIn.read(buff)) != -1) {
            byteArrayOutputStream.write(buff, 0, read);
        }
        final long delay = delayBeforeStatusResponse - (System.currentTimeMillis() - exchangeContext.getExchangeStarted());
        if (delay > 0) {
            sleep(delay);
        }
        httpExchange.sendResponseHeaders(statusCode, byteArrayOutputStream.size());
        sleep(delayBeforeBody);
        if (timeToWriteResponseBody > 0) {
            SlowOutputStreamWriter.slowWrite(byteArrayOutputStream.toByteArray(), httpExchange.getResponseBody(), timeToWriteResponseBody);
        } else {
            httpExchange.getResponseBody().write(byteArrayOutputStream.toByteArray());
        }
        if (shouldClose)
            httpExchange.close();
    }

    private void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }

}
