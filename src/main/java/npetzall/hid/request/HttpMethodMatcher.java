package npetzall.hid.request;

import npetzall.hid.api.request.HIDMatcher;

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
        return hidRequest.getRequestMethod().equals(method);
    }
}
