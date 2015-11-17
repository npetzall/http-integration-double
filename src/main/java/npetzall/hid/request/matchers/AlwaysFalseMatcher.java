package npetzall.hid.request.matchers;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;

public class AlwaysFalseMatcher   implements HIDMatcher {
    @Override
    public boolean matches(HIDRequest hidRequest) {
        return false;
    }
}
