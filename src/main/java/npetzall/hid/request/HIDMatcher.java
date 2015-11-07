package npetzall.hid.request;

/**
 * Created by nosse on 2015-11-05.
 */
public interface HIDMatcher {
    boolean matches(HIDRequest hidRequest);
}
