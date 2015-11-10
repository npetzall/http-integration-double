package npetzall.hid.request.matchers;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.request.DummyRequest;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static npetzall.hid.TestUtil.getResourceStream;
import static npetzall.hid.TestUtil.readInputStreamToByteArray;
import static npetzall.hid.request.HIDMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nosse on 2015-11-09.
 */
public class MatchersTest {

    @Test
    public void alwaysTrueMatcherTest() {
        HIDMatcher matcher = alwaysTrue();
        assertThat(matcher.matches(new DummyRequest())).isTrue();
    }

    @Test
    public void alwaysFalseMatcherTest() {
        HIDMatcher matcher = alwaysFalse();
        assertThat(matcher.matches(new DummyRequest())).isFalse();
    }

    @Test
    public void httpMethodMatherTest() {
        HIDMatcher matcher = httpMethodMatcher("HEAD");
        assertThat(matcher.matches(new DummyRequest().method("HEAD"))).isTrue();
        assertThat(matcher.matches(new DummyRequest().method("GET"))).isFalse();
    }

    @Test
    public void regExpMatcherTest() {
        HIDMatcher matcher = regExpMatcher("\\d{3}abc");
        assertThat(matcher.matches(new DummyRequest().request("123abc".getBytes(StandardCharsets.UTF_8)))).isTrue();
        assertThat(matcher.matches(new DummyRequest().request("123aaa".getBytes(StandardCharsets.UTF_8)))).isFalse();
    }

    @Test
    public void elementQNameTest() throws IOException {
        HIDMatcher matcher = elementQNameMatcher("http://npetzall/hid", "reverse");
        assertThat(
                matcher.matches(
                        new DummyRequest().request(
                                readInputStreamToByteArray(
                                        getResourceStream("/matchers/QNameMatcherReverse.xml"))))
        ).isTrue();
        assertThat(
                matcher.matches(
                        new DummyRequest().request(
                                readInputStreamToByteArray(
                                        getResourceStream("/matchers/QNameMatcherUppercase.xml"))))
        ).isFalse();
    }

    @Test
    public void attributeQNameTest() throws IOException {
        HIDMatcher matcher = attributeQNameMatcher("", "exists");
        assertThat(
                matcher.matches(
                        new DummyRequest().request(
                                readInputStreamToByteArray(
                                        getResourceStream("/matchers/QNameMatcherReverse.xml"))))
        ).isTrue();
        assertThat(
                matcher.matches(
                        new DummyRequest().request(
                                readInputStreamToByteArray(
                                        getResourceStream("/matchers/QNameMatcherUppercase.xml"))))
        ).isFalse();
    }

    @Test
    public void andTrueTrueTest() {
        HIDMatcher matcher = and(alwaysTrue(),alwaysTrue());
        assertThat(matcher.matches(new DummyRequest())).isTrue();
    }

    @Test
    public void andTrueFalseTest() {
        HIDMatcher matcher = and(alwaysTrue(), alwaysFalse());
        assertThat(matcher.matches(new DummyRequest())).isFalse();
    }

    @Test
    public void orTrueTrueTest() {
        HIDMatcher matcher = or(alwaysTrue(),alwaysTrue());
        assertThat(matcher.matches(new DummyRequest())).isTrue();
    }

    @Test
    public void orFalseTrue() {
        HIDMatcher matcher = or(alwaysFalse(),alwaysTrue());
        assertThat(matcher.matches(new DummyRequest())).isTrue();
    }

    @Test
    public void orFalseFalse() {
        HIDMatcher matcher = or(alwaysFalse(),alwaysFalse());
        assertThat(matcher.matches(new DummyRequest())).isFalse();
    }

    @Test
    public void hasHeaderName() {
        HIDMatcher matcher = headerNameExists("X-Test");
        assertThat(matcher.matches(new DummyRequest().addHeader("X-Test","yes"))).isTrue();
        assertThat(matcher.matches(new DummyRequest().addHeader("Y-Test","no"))).isFalse();
    }

    @Test
    public void hasHeaderNameValue() {
        HIDMatcher matcher = headerNameValueExists("X-Test","yes");
        assertThat(matcher.matches(new DummyRequest().addHeader("X-Test","yes"))).isTrue();
        assertThat(matcher.matches(new DummyRequest().addHeader("X-Test", "no"))).isFalse();
        assertThat(matcher.matches(new DummyRequest().addHeader("Y-Test", "no"))).isFalse();
    }

    @Test
    public void uriMatcherTest() {
        HIDMatcher matcher = uriMatcher("/[a-z]*");
        assertThat(matcher.matches(new DummyRequest().uri(URI.create("http://localhost:1234/path")))).isTrue();
        assertThat(matcher.matches(new DummyRequest().uri(URI.create("http://localhost:1234/1")))).isFalse();
    }

}
