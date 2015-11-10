package npetzall.hid.request.matchers;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;

import java.util.regex.Pattern;

public class URIMatcher implements HIDMatcher{

    private final Pattern regexp;

    public URIMatcher(String pattern) {
        this.regexp = Pattern.compile(pattern);
    }

    @Override
    public boolean matches(HIDRequest hidRequest) {
        return regexp.matcher(hidRequest.getURI().getPath()).matches();
    }

}
