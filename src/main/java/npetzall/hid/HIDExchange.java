package npetzall.hid;

import com.sun.net.httpserver.HttpExchange;
import npetzall.hid.api.request.HIDDataExtractor;
import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;
import npetzall.hid.api.response.HIDResponse;
import npetzall.hid.exchange.HIDExchangeContextImpl;
import npetzall.hid.exchange.extractor.HIDNoopDataExtractor;
import npetzall.hid.request.HIDHttpExchangeWrapper;
import npetzall.hid.request.HIDMatchers;
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

    public HIDExchange setMatcher(final HIDMatcher hidMatcher) {
        this.hidMatcher = hidMatcher;
        return this;
    }

    public HIDExchange setDataExtractor(final HIDDataExtractor hidDataExtractor) {
        this.hidDataExtractor = hidDataExtractor;
        return this;
    }

    public HIDExchange setResponse(final HIDResponse hidResponse) {
        this.hidResponse = new HIDResponseDecorator(hidResponse);
        return this;
    }

    public boolean matches(final HIDHttpExchangeWrapper hidRequest) {
        return hidMatcher.matches(hidRequest);
    }

    public void extractData(HIDRequest request, HIDExchangeContextImpl exchangeContext) {
        hidDataExtractor.extract(request, exchangeContext);
    }

    public void sendResponse(final HIDExchangeContextImpl exchangeContext, final HttpExchange httpExchange) throws IOException {
        hidResponse.writeToOutputStream(exchangeContext, httpExchange);
    }

    public HIDExchange setDelayBeforeStatusResponse(final long delayBeforeStatusResponse) {
        hidResponse.delayBeforeStatusResponse(delayBeforeStatusResponse);
        return this;
    }

    public HIDExchange setStatusCode(final int statusCode) {
        hidResponse.statusCode(statusCode);
        return this;
    }

    public HIDExchange setDelayBeforeBody(final long delayBeforeBody) {
        hidResponse.delayBeforeBody(delayBeforeBody);
        return this;
    }

    public HIDExchange setTimeToWriteResponseBody(final long timeToWriteResponseBody) {
        hidResponse.timeToWriteResponseBody(timeToWriteResponseBody);
        return this;
    }

    public HIDExchange setShouldClose(final boolean shouldClose) {
        hidResponse.shouldClose(shouldClose);
        return this;
    }
}
