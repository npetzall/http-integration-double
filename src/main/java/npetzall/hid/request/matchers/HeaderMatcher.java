package npetzall.hid.request.matchers;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;

import java.util.List;

/**
 * Created by nosse on 2015-11-09.
 */
public class HeaderMatcher implements HIDMatcher {

    private final String key;
    private final String value;

    public HeaderMatcher(String key) {
        this(key, null);
    }

    public HeaderMatcher(String key, String value) {
        this.key = key;
        this.value = value;
    }
    @Override
    public boolean matches(HIDRequest hidRequest) {
        if (value == null) {
            return headerNameExists(hidRequest);
        } else {
            return headerNameValueExists(hidRequest);
        }
    }

    private boolean headerNameExists(HIDRequest hidRequest) {
        return hidRequest.getHeaders().containsKey(key);
    }

    private boolean headerNameValueExists(HIDRequest hidRequest) {
        List<String> values = hidRequest.getHeaders().get(key);
        return values != null && values.contains(value);
    }
}
