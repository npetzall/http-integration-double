package npetzall.hid.request.matchers.logic;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.request.HIDRequest;

/**
 * Created by nosse on 2015-11-09.
 */
public class OrMatcher implements HIDMatcher {

    HIDMatcher[] matchers;

    public OrMatcher(HIDMatcher...matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(HIDRequest hidRequest) {
        for(HIDMatcher matcher : matchers) {
            if (matcher.matches(hidRequest)) {
                return true;
            }
        }
        return false;
    }
}
