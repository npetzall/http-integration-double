package npetzall.hid.test.unit.request.matchers.xml;

import npetzall.hid.request.matchers.xml.XPathMatcher;
import npetzall.hid.test.DummyRequest;
import npetzall.hid.test.TestUtil;
import npetzall.hid.xml.NamespaceMap;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class XPathMatcherTest {

    @Test
    public void xPathMatcherElementWithNoNamespaceTest() throws IOException {
        XPathMatcher xPathMatcher = new XPathMatcher(null)
                .xpath("/exchange/request/reverse/message");
        DummyRequest dummyRequest = new DummyRequest()
                .request(TestUtil.readURLToByteArray(
                        TestUtil.getResourceURL("/matchers/QNameMatcherReverse.xml")));
        assertThat(xPathMatcher.matches(dummyRequest)).isTrue();
    }

    @Test
    public void xPathMatcherElementWithNamespaceTest() throws IOException {
        XPathMatcher xPathMatcher = new XPathMatcher(new NamespaceMap()
                .namespace("hid","http://npetzall/hid")
                .namespace("nohid","http://npetzall/nohid"))
                .xpath("/hid:exchange/hid:request/hid:reverse/nohid:message");
        DummyRequest dummyRequest = new DummyRequest()
                .request(TestUtil.readURLToByteArray(
                        TestUtil.getResourceURL("/matchers/QNameMatcherReverse.xml")));
        assertThat(xPathMatcher.matches(dummyRequest)).isTrue();
    }

    @Test
    public void xPathMatcherAttributeWithNamespaceTest() throws IOException {
        XPathMatcher xPathMatcher = new XPathMatcher(null)
                .xpath("/exchange/request/reverse/@someother");
        DummyRequest dummyRequest = new DummyRequest()
                .request(TestUtil.readURLToByteArray(
                        TestUtil.getResourceURL("/matchers/QNameMatcherReverse.xml")));
        assertThat(xPathMatcher.matches(dummyRequest)).isTrue();
    }

    @Test
    public void xPathMatcherAttributeWithNoNamespaceTest() throws IOException {
        XPathMatcher xPathMatcher = new XPathMatcher(new NamespaceMap()
                .namespace("hid","http://npetzall/hid")
                .namespace("nohid","http://npetzall/nohid"))
                .xpath("/hid:exchange/hid:request/hid:reverse/@someother");
        DummyRequest dummyRequest = new DummyRequest()
                .request(TestUtil.readURLToByteArray(
                        TestUtil.getResourceURL("/matchers/QNameMatcherReverse.xml")));
        assertThat(xPathMatcher.matches(dummyRequest)).isTrue();
    }

}
