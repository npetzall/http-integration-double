package npetzall.hid.request;

/**
 * Created by nosse on 2015-11-04.
 */
public class HIDMatchers {

    private static final AlwaysTrueMatcher ALWAYS_TRUE_MATCHER = new AlwaysTrueMatcher();

    public static AlwaysTrueMatcher alwaysTrue() {
        return ALWAYS_TRUE_MATCHER;
    }

    public static QNameMatcher elementQNameMatcher(String nameSpace, String localPart) {
        return new QNameMatcher(nameSpace, localPart, QNameMatcher.ForType.ELEMENT);
    }

    public static QNameMatcher attributeQNameMatcher(String nameSpace, String localPart) {
        return new QNameMatcher(nameSpace, localPart, QNameMatcher.ForType.ATTRIBUTE);
    }
}
