package npetzall.hid;

import com.sun.net.httpserver.HttpExchange;
import npetzall.hid.api.request.HIDDataExtractor;
import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.response.HIDResponse;
import npetzall.hid.exchange.HIDExchangeContextImpl;
import npetzall.hid.request.HIDMatchers;
import npetzall.hid.request.HIDNoopDataExtractor;
import npetzall.hid.request.HIDRequest;
import npetzall.hid.response.HIDResponseDecorator;

import java.io.IOException;

/**
 * Created by nosse on 2015-11-04.
 */
public class HIDExchange {

    public static HIDExchange newExchange() {
        return new HIDExchange();
    }

    private HIDMatcher hidMatcher = HIDMatchers.alwaysTrue();
    private HIDDataExtractor hidDataExtractor = new HIDNoopDataExtractor();
    private HIDResponseDecorator hidResponse;

    public HIDExchange matcher(HIDMatcher hidMatcher) {
        this.hidMatcher = hidMatcher;
        return this;
    }

    public HIDExchange dataExtractor(HIDDataExtractor hidDataExtractor) {
        this.hidDataExtractor = hidDataExtractor;
        return this;
    }

    public HIDExchange response(HIDResponse hidResponse) {
        this.hidResponse = new HIDResponseDecorator(hidResponse);
        return this;
    }

    @Deprecated
    public HIDResponseDecorator getResponse() {
        return hidResponse;
    }


    public boolean matches(HIDRequest hidRequest) {
        return hidMatcher.matches(hidRequest);
    }

    public void extractData(HIDExchangeContextImpl exchangeContext) {
        hidDataExtractor.extractData(exchangeContext);
    }

    public void sendResponse(HIDExchangeContextImpl exchangeContext, HttpExchange httpExchange) throws IOException {
        hidResponse.writeToOutputStream(exchangeContext, httpExchange);
    }

    public HIDExchange delayBeforeStatusResponse(long delayBeforeStatusResponse) {
        hidResponse.delayBeforeStatusResponse(delayBeforeStatusResponse);
        return this;
    }

    public HIDExchange statusCode(int statusCode) {
        hidResponse.statusCode(statusCode);
        return this;
    }

    public HIDExchange delayBeforeBody(long delayBeforeBody) {
        hidResponse.delayBeforeBody(delayBeforeBody);
        return this;
    }

    public HIDExchange timeToWriteResponseBody(long timeToWriteResponseBody) {
        hidResponse.timeToWriteResponseBody(timeToWriteResponseBody);
        return this;
    }

    public HIDExchange shouldClose(boolean shouldClose) {
        hidResponse.shouldClose(shouldClose);
        return this;
    }
}
