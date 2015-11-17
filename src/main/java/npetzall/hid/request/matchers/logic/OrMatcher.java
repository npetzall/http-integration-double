package npetzall.hid.request.matchers.logic;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;

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
