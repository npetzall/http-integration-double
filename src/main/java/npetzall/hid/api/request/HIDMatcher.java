package npetzall.hid.api.request;

public interface HIDMatcher {
    boolean matches(HIDRequest hidRequest);
}
