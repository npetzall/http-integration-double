package npetzall.hid.test.unit.request.extractor.xml;

import npetzall.hid.exchange.HIDExchangeContextImpl;
import npetzall.hid.request.extractor.HIDExtractors;
import npetzall.hid.request.extractor.xml.XPathExtractor;
import npetzall.hid.test.DummyRequest;
import org.testng.annotations.Test;

import java.io.IOException;

import static npetzall.hid.test.TestUtil.getResourceURL;
import static npetzall.hid.test.TestUtil.readURLToByteArray;
import static org.assertj.core.api.Assertions.assertThat;

public class XPathExtractorTest {

    @Test
    public void extract() throws IOException {
        XPathExtractor xPathExtractor = HIDExtractors.xPathExtractor("/request/message","message");
        HIDExchangeContextImpl exchangeContext = new HIDExchangeContextImpl();
        DummyRequest dummyRequest = new DummyRequest().request(
                readURLToByteArray(
                        getResourceURL("/requests/EchoRequest.xml")));
        xPathExtractor.extract(dummyRequest,exchangeContext);
        assertThat(exchangeContext.getAttribute("message")).isEqualTo("hello");
    }
}
