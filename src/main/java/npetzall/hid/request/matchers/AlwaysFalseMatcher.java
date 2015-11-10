package npetzall.hid.request.matchers;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;

/**
 * Created by nosse on 2015-11-09.
 */
public class AlwaysFalseMatcher   implements HIDMatcher {
    @Override
    public boolean matches(HIDRequest hidRequest) {
        return false;
    }
}
