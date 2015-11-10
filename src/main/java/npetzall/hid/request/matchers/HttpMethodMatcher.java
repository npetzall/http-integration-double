package npetzall.hid.request.matchers;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;

/**
 * Created by nosse on 2015-11-09.
 */
public class HttpMethodMatcher implements HIDMatcher {

    private final String method;

    public HttpMethodMatcher(String method) {
        this.method = method;
    }
    @Override
    public boolean matches(HIDRequest hidRequest) {
        return hidRequest.getMethod().equals(method);
    }
}
