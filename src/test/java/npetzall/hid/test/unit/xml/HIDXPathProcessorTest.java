package npetzall.hid.test.unit.xml;

import npetzall.hid.test.DummyXmlElement;
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
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("exchange"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("request"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("reverse"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyXmlElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("message"))).isTrue();
        HIDXPath path = hidxPathProcessor.getMatches().get(0);
        assertThat(path.getXPathString()).isEqualTo(XPATH_NO_NAMESPACE);
    }

    @Test
    public void xpathProcessorNoNamespaceNoMatch() {
        HIDXPathProcessor hidxPathProcessor = new HIDXPathProcessor(null);
        hidxPathProcessor.addXPath(XPATH_NO_NAMESPACE);
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("exchange"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("request"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("reverse"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyXmlElement("someother"))).isFalse();

        assertThat(hidxPathProcessor.getMatches().size()).isEqualTo(0);
    }

    @Test
    public void xpathProcessorNoNamespaceMatchOnStartAndEnd() {
        HIDXPathProcessor hidxPathProcessor = new HIDXPathProcessor(null);
        hidxPathProcessor.addXPath(XPATH_NO_NAMESPACE);
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("exchange"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("request"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("reverse"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyXmlElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("message"))).isTrue();
        HIDXPath path = hidxPathProcessor.getMatches().get(0);
        assertThat(path.getXPathString()).isEqualTo(XPATH_NO_NAMESPACE);
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyXmlElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyXmlElement("message"))).isTrue();
        path = hidxPathProcessor.getMatches().get(0);
        assertThat(path.getXPathString()).isEqualTo(XPATH_NO_NAMESPACE);
    }

    @Test
    public void xpathProcessorWithNamespaceMatchOnStartAndEnd() {
        HIDXPathProcessor hidxPathProcessor = new HIDXPathProcessor(namespaceMap);
        hidxPathProcessor.addXPath(XPATH_WITH_NAMESPACE);
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("http://npetzall/hid","exchange"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("http://npetzall/hid","request"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("http://npetzall/hid","reverse"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyXmlElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("http://npetzall/nohid","message"))).isTrue();
        HIDXPath path = hidxPathProcessor.getMatches().get(0);
        assertThat(path.getXPathString()).isEqualTo(XPATH_WITH_NAMESPACE);
        assertThat(hidxPathProcessor.startElement(new DummyXmlElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyXmlElement("someother"))).isFalse();
        assertThat(hidxPathProcessor.endElement(new DummyXmlElement("http://npetzall/nohid","message"))).isTrue();
        path = hidxPathProcessor.getMatches().get(0);
        assertThat(path.getXPathString()).isEqualTo(XPATH_WITH_NAMESPACE);
    }

}
