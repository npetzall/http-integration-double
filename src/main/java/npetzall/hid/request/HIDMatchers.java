package npetzall.hid.request;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.request.matchers.*;
import npetzall.hid.request.matchers.logic.AndMatcher;
import npetzall.hid.request.matchers.logic.OrMatcher;
import npetzall.hid.request.matchers.xml.AttributeQNameMatcher;
import npetzall.hid.request.matchers.xml.ElementQNameMatcher;

/**
 * Created by nosse on 2015-11-04.
 */
public class HIDMatchers {

    private static final AlwaysTrueMatcher ALWAYS_TRUE_MATCHER = new AlwaysTrueMatcher();
    private static final AlwaysFalseMatcher ALWAYS_FALSE_MATCHER = new AlwaysFalseMatcher();

    private HIDMatchers() {
        //Should only be used thru static methods
    }

    public static AlwaysTrueMatcher alwaysTrue() {
        return ALWAYS_TRUE_MATCHER;
    }
    public static AlwaysFalseMatcher alwaysFalse() {
        return ALWAYS_FALSE_MATCHER;
    }

    public static AndMatcher and(HIDMatcher...matchers) {
        return new AndMatcher(matchers);
    }

    public static OrMatcher or(HIDMatcher...matchers) {
        return new OrMatcher(matchers);
    }

    public static ElementQNameMatcher elementQNameMatcher(String nameSpace, String localPart) {
        return new ElementQNameMatcher(nameSpace, localPart);
    }

    public static AttributeQNameMatcher attributeQNameMatcher(String nameSpace, String localPart) {
        return new AttributeQNameMatcher(nameSpace, localPart);
    }

    public static HttpMethodMatcher httpMethodMatcher(String method) {
        return new HttpMethodMatcher(method);
    }

    public static RegExpMatcher regExpMatcher(String pattern) {
        return new RegExpMatcher(pattern);
    }

    public static HeaderMatcher headerNameExists(String headerName) {
        return new HeaderMatcher(headerName);
    }

    public static HeaderMatcher headerNameValueExists(String headerName, String headerValue) {
        return new HeaderMatcher(headerName,headerValue);
    }

    public static URIMatcher uriMatcher(String regexp) {
        return new URIMatcher(regexp);
    }
}
