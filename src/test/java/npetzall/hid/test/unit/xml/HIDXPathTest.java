package npetzall.hid.test.unit.xml;

import npetzall.hid.test.DummyStartXMLElement;
import npetzall.hid.xml.HIDXPath;
import npetzall.hid.xml.HIDXPathPart;
import org.testng.annotations.Test;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HIDXPathTest {

    @Test
    public void xPathEndsWithAttribute() {
        HIDXPath hidxPath = new HIDXPath(Collections.EMPTY_MAP,"/something/@attribute");
        assertThat(hidxPath.isAttributeRequested()).isTrue();
    }

    @Test
    public void xPathWithNamespace() {
        Map<String,String> namespaceMap = new HashMap<>();
        namespaceMap.put("hid", "http://npetzall/hid");
        namespaceMap.put("nohid","http://npetzall/nohid");
        HIDXPath hidxPath = new HIDXPath(namespaceMap,"/hid:exchange/hid:request/hid:reverse/nohid:message");
        List<HIDXPathPart> hidxPathParts = hidxPath.getHidxPathParts();
        assertThat(hidxPathParts.get(0).getName().equals(new QName("http://npetzall/hid","exchange"))).isTrue();
        assertThat(hidxPathParts.get(1).getName().equals(new QName("http://npetzall/hid","request"))).isTrue();
        assertThat(hidxPathParts.get(hidxPathParts.size()-1).getName().equals(new QName("http://npetzall/nohid","message"))).isTrue();
    }

    @Test(enabled = false)
    public void xPathWithRequirement() {
        HIDXPath hidxPath = new HIDXPath(null, "/exchange/request/message[@id = '123']/text()");
        List<HIDXPathPart> hidxPathParts = hidxPath.getHidxPathParts();
        assertThat(hidxPathParts.get(0).getName().getLocalPart()).isEqualTo("exchange");
        assertThat(hidxPathParts.get(1).getName().getLocalPart()).isEqualTo("request");
        assertThat(hidxPathParts.get(2).getName().getLocalPart()).isEqualTo("message");
        DummyStartXMLElement dummyStartXMLElement = new DummyStartXMLElement("message").addAttributeWithValue("","id","123");
        assertThat(hidxPathParts.get(2).matches(dummyStartXMLElement)).isTrue();
    }

}
