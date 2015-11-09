package npetzall.hid.request.matchers;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.request.HIDRequest;

import java.util.regex.Pattern;

/**
 * Created by nosse on 2015-11-09.
 */
public class RegExpMatcher implements HIDMatcher {

    private final Pattern pattern;

    public RegExpMatcher(String regexp) {
        pattern = Pattern.compile(regexp);
    }

    @Override
    public boolean matches(HIDRequest hidRequest) {
        return pattern.matcher(new String(hidRequest.getRequestBodyBytes())).matches();
    }
}
