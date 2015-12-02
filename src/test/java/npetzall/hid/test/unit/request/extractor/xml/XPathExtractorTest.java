package npetzall.hid.test.unit.request.extractor.xml;

import npetzall.hid.exchange.HIDExchangeContextImpl;
import npetzall.hid.request.extractor.xml.XPathExtractor;
import npetzall.hid.test.DummyRequest;
import npetzall.hid.test.TestUtil;
import npetzall.hid.xml.NamespaceMap;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class XPathExtractorTest {

    @Test
    public void xPathExtractorElementNoNamespace() throws IOException {
        XPathExtractor xPathExtractor = new XPathExtractor();
        xPathExtractor.xpath("/exchange/request/reverse/message/text()","message");
        DummyRequest dummyRequest = new DummyRequest()
                .request(TestUtil.readURLToByteArray(
                        TestUtil.getResourceURL("/matchers/QNameMatcherReverse.xml")));
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        xPathExtractor.extract(dummyRequest, context);
        assertThat(context.getAttribute("message")).isEqualTo("someotherperson");
    }

    @Test
    public void xPathExtractorElementWithNamespace() throws IOException {
        XPathExtractor xPathExtractor = new XPathExtractor(new NamespaceMap().namespace("nohid","http://npetzall/nohid"));
        xPathExtractor.xpath("/exchange/request/reverse/nohid:message/text()","message");
        DummyRequest dummyRequest = new DummyRequest()
                .request(TestUtil.readURLToByteArray(
                        TestUtil.getResourceURL("/matchers/QNameMatcherReverse.xml")));
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        xPathExtractor.extract(dummyRequest, context);
        assertThat(context.getAttribute("message")).isEqualTo("nils");
    }

    @Test
    public void xPathExtractorAttributeNoNamespace() throws IOException {
        XPathExtractor xPathExtractor = new XPathExtractor();
        xPathExtractor.xpath("/exchange/request/reverse/@someother","someother");
        DummyRequest dummyRequest = new DummyRequest()
                .request(TestUtil.readURLToByteArray(
                        TestUtil.getResourceURL("/matchers/QNameMatcherReverse.xml")));
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        xPathExtractor.extract(dummyRequest, context);
        assertThat(context.getAttribute("someother")).isEqualTo("false");
    }

    @Test
    public void xPathExtractorAttributeWithNamespace() throws IOException {
        XPathExtractor xPathExtractor = new XPathExtractor(new NamespaceMap().namespace("nohid","http://npetzall/nohid"));
        xPathExtractor.xpath("/exchange/request/reverse/nohid:@someother","someother");
        DummyRequest dummyRequest = new DummyRequest()
                .request(TestUtil.readURLToByteArray(
                        TestUtil.getResourceURL("/matchers/QNameMatcherReverse.xml")));
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        xPathExtractor.extract(dummyRequest, context);
        assertThat(context.getAttribute("someother")).isEqualTo("no");
    }
}
