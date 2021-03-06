package npetzall.hid.gwt;

import npetzall.hid.api.request.HIDDataExtractor;
import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.response.HIDResponse;
import npetzall.hid.request.extractor.HIDNoopDataExtractor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class HIDGWTContext {

    final String contextPath;
    HIDMatcher hidMatcher;
    Map<String,String> tokenReplacements = new HashMap<>();
    HIDDataExtractor hidDataExtractor = new HIDNoopDataExtractor();
    HIDResponse hidResponse;
    Map<String,Set<String>> headers = new HashMap<>();
    long delayStatusFor = 0;
    int respondWithStatusCode = 200;
    long delayResponseBodyFor = 0;
    long writeBodyFor = 0;
    boolean shouldClose = true;

    public HIDGWTContext(String contextPath) {
        this.contextPath = contextPath;
    }

    public HIDGWTContext whenRequestMatches(HIDMatcher hidMatcher) {
        this.hidMatcher = hidMatcher;
        return this;
    }

    public HIDGWTContext addTokenReplacement(String token, String replacement) {
        tokenReplacements.put(token, replacement);
        return this;
    }

    public HIDGWTContext thenExtract(HIDDataExtractor hidDataExtractor) {
        this.hidDataExtractor = hidDataExtractor;
        return this;
    }

    public HIDGWTContext thenRespondWith(HIDResponse hidResponse) {
        this.hidResponse = hidResponse;
        return this;
    }

    public HIDGWTContext addHeader(String name, String value) {
        Set<String> values = headers.get(name);
        if (values == null) {
            values = new TreeSet<>();
            headers.put(name, values);
        }
        values.add(value);
        return this;
    }

    public HIDGWTContext delayStatusFor(long milliSeconds) {
        delayStatusFor = milliSeconds;
        return this;
    }

    public HIDGWTContext respondWithStatusCode(int statusCode) {
        this.respondWithStatusCode = statusCode;
        return this;
    }

    public HIDGWTContext delayResponseBodyFor(long milliSeconds) {
        this.delayResponseBodyFor = milliSeconds;
        return this;
    }

    public HIDGWTContext writeBodyFor(long milliSeconds) {
        this.writeBodyFor = milliSeconds;
        return this;
    }

    public HIDGWTContext shouldClose(boolean shouldClose) {
        this.shouldClose = shouldClose;
        return this;
    }
}
