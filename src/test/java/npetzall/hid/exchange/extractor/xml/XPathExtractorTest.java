package npetzall.hid.exchange.extractor.xml;

import npetzall.hid.exchange.HIDExchangeContextImpl;
import npetzall.hid.request.DummyRequest;
import org.testng.annotations.Test;

import java.io.IOException;

import static npetzall.hid.TestUtil.getResourceURL;
import static npetzall.hid.TestUtil.readURLToByteArray;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nosse on 2015-11-14.
 */
public class XPathExtractorTest {

    @Test
    public void createXPathExtractor() {
        XPathExtractor xPathExtractor = new XPathExtractor("/request/message","message");
    }

    @Test
    public void extract() throws IOException {
        XPathExtractor xPathExtractor = new XPathExtractor("/request/message","message");
        HIDExchangeContextImpl exchangeContext = new HIDExchangeContextImpl();
        DummyRequest dummyRequest = new DummyRequest().request(
                readURLToByteArray(
                        getResourceURL("/requests/EchoRequest.xml")));
        xPathExtractor.extract(dummyRequest,exchangeContext);
        assertThat(exchangeContext.getAttribute("message")).isEqualTo("hello");
    }
}
