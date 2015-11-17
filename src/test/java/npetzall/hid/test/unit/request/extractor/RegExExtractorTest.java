package npetzall.hid.test.unit.request.extractor;

import npetzall.hid.exchange.HIDExchangeContextImpl;
import npetzall.hid.request.extractor.RegExExtractor;
import npetzall.hid.test.DummyRequest;
import org.testng.annotations.Test;

import java.io.IOException;

import static npetzall.hid.request.extractor.HIDExtractors.regExExtractor;
import static npetzall.hid.test.TestUtil.getResourceURL;
import static npetzall.hid.test.TestUtil.readURLToByteArray;
import static org.assertj.core.api.Assertions.assertThat;

public class RegExExtractorTest {

    @Test
    public void extractMessage() throws IOException {
        RegExExtractor regExExtractor = regExExtractor("<message>(?<message>[a-zA-Z]*)</message>");
        DummyRequest dummyRequest = new DummyRequest().request(
                readURLToByteArray(
                        getResourceURL("/requests/EchoRequest.xml")));
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        regExExtractor.extract(dummyRequest,context);
        assertThat(context.getAttribute("message_1")).isEqualTo("hello");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void noNamedGroups() {
        RegExExtractor regExExtractor = regExExtractor("<message>([a-zA-Z]*)</message>");
    }

    @Test
    public void noMatches() throws IOException {
        RegExExtractor regExExtractor = regExExtractor("<message>((?<messageNum>[0-9]*)|(?<messageStr>[a-zA-Z]*))</message>");
        DummyRequest dummyRequest = new DummyRequest().request(
                readURLToByteArray(
                        getResourceURL("/requests/EchoRequest.xml")));
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        regExExtractor.extract(dummyRequest,context);
        assertThat(context.getAttributeKeys().size()).isEqualTo(1);
    }
}
