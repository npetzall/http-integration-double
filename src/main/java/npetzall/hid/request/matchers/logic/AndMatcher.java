package npetzall.hid.request.matchers.logic;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;

/**
 * Created by nosse on 2015-11-09.
 */
public class AndMatcher implements HIDMatcher {

    private HIDMatcher[] matchers;

    public AndMatcher(HIDMatcher...matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(HIDRequest hidRequest) {
        for(HIDMatcher matcher: matchers) {
            if (!matcher.matches(hidRequest)) {
                return false;
            }
        }
        return true;
    }
}
