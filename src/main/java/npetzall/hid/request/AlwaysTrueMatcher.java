package npetzall.hid.request;

import npetzall.hid.api.request.HIDMatcher;

/**
 * Created by nosse on 2015-11-06.
 */
public class AlwaysTrueMatcher  implements HIDMatcher {
    @Override
    public boolean matches(HIDRequest hidRequest) {
        return true;
    }
}
