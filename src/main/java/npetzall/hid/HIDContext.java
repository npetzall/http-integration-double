package npetzall.hid;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import npetzall.hid.io.SlowOutputStreamWriter;
import npetzall.hid.request.HIDRequest;
import npetzall.hid.response.HIDResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nosse on 2015-11-04.
 */
public class HIDContext implements HttpHandler {
    public static HIDContext newContext() {
        return new HIDContext();
    }

    private String path;
    private final List<HIDExchange> hidExchanges = new ArrayList<>();

    public HIDContext path(final String path) {
        this.path = path;
        return this;
    }

    public HIDContext addExchange(final HIDExchange hidExchange) {
        hidExchanges.add(hidExchange);
        return this;
    }

    public String getPath() {
        return path;
    }

    @Override
    public void handle(final HttpExchange httpExchange) throws IOException {
        final long exchangeStarted = System.currentTimeMillis();
        final HIDRequest hidRequest = new HIDRequest(httpExchange);
        for(final HIDExchange exchange: hidExchanges) {
            if (exchange.matches(hidRequest)) {
                sendResponse(httpExchange, exchangeStarted, exchange.getResponse());
            }
        }
    }

    private void sendResponse(HttpExchange httpExchange, long exchangeStarted, HIDResponse response) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final InputStream responseIn = response.getInputStream();
        final byte[] buff = new byte[4096];
        int read;
        while((read = responseIn.read(buff)) != -1) {
            byteArrayOutputStream.write(buff, 0, read);
        }
        final long delay = response.getDelayBeforeStatusResponse() - (System.currentTimeMillis() - exchangeStarted);
        if (delay > 0) {
            sleep(delay);
        }
        httpExchange.sendResponseHeaders(response.getStatusCode(), byteArrayOutputStream.size());
        sleep(response.getDelayBeforeBody());
        if (response.getTimeToWriteResponseBody() > 0) {
            SlowOutputStreamWriter.slowWrite(byteArrayOutputStream.toByteArray(), httpExchange.getResponseBody(), response.getTimeToWriteResponseBody());
        } else {
            httpExchange.getResponseBody().write(byteArrayOutputStream.toByteArray());
        }
        if (response.getShouldClose())
        httpExchange.close();
    }

    private void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }
}
