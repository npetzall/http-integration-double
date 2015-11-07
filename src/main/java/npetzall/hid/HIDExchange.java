package npetzall.hid;

import npetzall.hid.request.HIDMatcher;
import npetzall.hid.request.HIDMatchers;
import npetzall.hid.request.HIDRequest;
import npetzall.hid.response.HIDResponse;

/**
 * Created by nosse on 2015-11-04.
 */
public class HIDExchange {

    public static HIDExchange newExchange() {
        return new HIDExchange();
    }

    private HIDMatcher hidMatcher = HIDMatchers.alwaysTrue();
    private HIDResponse hidResponse;

    public HIDExchange matcher(HIDMatcher hidMatcher) {
        this.hidMatcher = hidMatcher;
        return this;
    }

    public HIDExchange response(HIDResponse hidResponse) {
        this.hidResponse = hidResponse;
        return this;
    }

    public HIDResponse getResponse() {
        return hidResponse;
    }


    public boolean matches(HIDRequest hidRequest) {
        return hidMatcher.matches(hidRequest);
    }
}
