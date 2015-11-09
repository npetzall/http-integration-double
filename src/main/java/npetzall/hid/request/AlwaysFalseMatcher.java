package npetzall.hid.request;

import npetzall.hid.api.request.HIDMatcher;

/**
 * Created by nosse on 2015-11-09.
 */
public class AlwaysFalseMatcher   implements HIDMatcher {
    @Override
    public boolean matches(HIDRequest hidRequest) {
        return false;
    }
}
