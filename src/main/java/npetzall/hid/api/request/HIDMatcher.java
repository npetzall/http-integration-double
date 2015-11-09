package npetzall.hid.api.request;

import npetzall.hid.request.HIDRequest;

public interface HIDMatcher {
    boolean matches(HIDRequest hidRequest);
}
