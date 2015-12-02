package npetzall.hid.test.unit.xml;

import npetzall.hid.test.DummyStartXMLElement;
import npetzall.hid.xml.HIDXPath;
import npetzall.hid.xml.HIDXPathProcessor;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HIDXPathProcessorTest {

    private static final String XPATH_NO_NAMESPACE = "/exchange/request/reverse/message";
    private static final String XPATH_WITH_NAMESPACE = "/hid:exchange/hid:request/hid:reverse/nohid:message";

    private static final Map<String,String> namespaceMap = new HashMap<>();

    static {
        namespaceMap.put("hid", "http://npetzall/hid");
        namespaceMap.put("nohid","http://npetzall/nohid");
    }

    @Test
    public void xpathProcessorNoNamespace() {
        HIDXPathProcessor hidxPathProcessor = new HIDXPathProcessor(null);
        hidxPathProcessor.addXPath(XPATH_NO_NAMESPACE);
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("exchange"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("request"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("reverse"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyStartXMLElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("message"))).isTrue();
        HIDXPath path = hidxPathProcessor.getMatches().get(0);
        assertThat(path.getXPathString()).isEqualTo(XPATH_NO_NAMESPACE);
    }

    @Test
    public void xpathProcessorNoNamespaceNoMatch() {
        HIDXPathProcessor hidxPathProcessor = new HIDXPathProcessor(null);
        hidxPathProcessor.addXPath(XPATH_NO_NAMESPACE);
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("exchange"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("request"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("reverse"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyStartXMLElement("someother"))).isFalse();

        assertThat(hidxPathProcessor.getMatches().size()).isEqualTo(0);
    }

    @Test
    public void xpathProcessorNoNamespaceMatchOnStartAndEnd() {
        HIDXPathProcessor hidxPathProcessor = new HIDXPathProcessor(null);
        hidxPathProcessor.addXPath(XPATH_NO_NAMESPACE);
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("exchange"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("request"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("reverse"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyStartXMLElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("message"))).isTrue();
        HIDXPath path = hidxPathProcessor.getMatches().get(0);
        assertThat(path.getXPathString()).isEqualTo(XPATH_NO_NAMESPACE);
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyStartXMLElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyStartXMLElement("message"))).isTrue();
        path = hidxPathProcessor.getMatches().get(0);
        assertThat(path.getXPathString()).isEqualTo(XPATH_NO_NAMESPACE);
    }

    @Test
    public void xpathProcessorWithNamespaceMatchOnStartAndEnd() {
        HIDXPathProcessor hidxPathProcessor = new HIDXPathProcessor(namespaceMap);
        hidxPathProcessor.addXPath(XPATH_WITH_NAMESPACE);
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("http://npetzall/hid","exchange"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("http://npetzall/hid","request"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("http://npetzall/hid","reverse"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyStartXMLElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("http://npetzall/nohid","message"))).isTrue();
        HIDXPath path = hidxPathProcessor.getMatches().get(0);
        assertThat(path.getXPathString()).isEqualTo(XPATH_WITH_NAMESPACE);
        assertThat(hidxPathProcessor.startElement(new DummyStartXMLElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyStartXMLElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyStartXMLElement("http://npetzall/nohid","message"))).isTrue();
        path = hidxPathProcessor.getMatches().get(0);
        assertThat(path.getXPathString()).isEqualTo(XPATH_WITH_NAMESPACE);
    }

}
