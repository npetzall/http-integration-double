package npetzall.hid.request.matchers;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;

import java.util.regex.Pattern;

public class RegExpMatcher implements HIDMatcher {

    private final Pattern pattern;

    public RegExpMatcher(String regexp) {
        pattern = Pattern.compile(regexp);
    }

    @Override
    public boolean matches(HIDRequest hidRequest) {
        return pattern.matcher(hidRequest.getBodyString()).matches();
    }
}
