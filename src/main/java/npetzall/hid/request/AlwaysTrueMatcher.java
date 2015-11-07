package npetzall.hid.request;

/**
 * Created by nosse on 2015-11-06.
 */
public class AlwaysTrueMatcher  implements HIDMatcher {
    @Override
    public boolean matches(HIDRequest hidRequest) {
        return true;
    }
}
