package npetzall.hid.request;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nosse on 2015-11-04.
 */
public class QNameMatcherTest {

    @Test
    public void foundElementQNameMatcher() {
        QNameMatcher qNameMatcher = new QNameMatcher("http://npetzall/hid","reverse", QNameMatcher.ForType.ELEMENT);
        assertThat(qNameMatcher.matches(getClass().getResourceAsStream("/matchers/QNameMatcher.xml"))).isTrue();
    }

    @Test
    public void notFoundElementQNameMatcher() {
        QNameMatcher qNameMatcher = new QNameMatcher("http://something/else", "reverse", QNameMatcher.ForType.ELEMENT);
        assertThat(qNameMatcher.matches(getClass().getResourceAsStream("/matchers/QNameMatcher.xml"))).isFalse();
    }

    @Test
    public void foundAttributeQNameMatcher() {
        QNameMatcher qNameMatcher = new QNameMatcher("", "exists", QNameMatcher.ForType.ATTRIBUTE);
        assertThat(qNameMatcher.matches(getClass().getResourceAsStream("/matchers/QNameMatcher.xml"))).isTrue();
    }

    @Test
    public void notFoundAttributeQNameMatcher() {
        QNameMatcher qNameMatcher = new QNameMatcher("http://something/else", "exists", QNameMatcher.ForType.ATTRIBUTE);
        assertThat(qNameMatcher.matches(getClass().getResourceAsStream("/matchers/QNameMatcher.xml"))).isFalse();
    }
}
