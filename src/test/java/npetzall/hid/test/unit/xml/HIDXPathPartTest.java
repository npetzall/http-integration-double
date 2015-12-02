package npetzall.hid.test.unit.xml;

import npetzall.hid.test.DummyStartXMLElement;
import npetzall.hid.xml.HIDXPathPart;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nosse on 2015-11-26.
 */
public class HIDXPathPartTest {

    @Test
    public void canMatchElement() {
        HIDXPathPart hidxPathPart = HIDXPathPart.element("","message");
        DummyStartXMLElement xmlElement = new DummyStartXMLElement("message");
        assertThat(hidxPathPart.matches(xmlElement)).isTrue();
    }

    @Test
    public void canMatchElementWithNamespace() {
        HIDXPathPart hidxPathPart = HIDXPathPart.element("http://npetzall/hid","message");
        DummyStartXMLElement xmlElement = new DummyStartXMLElement("http://npetzall/hid","message");
        assertThat(hidxPathPart.matches(xmlElement)).isTrue();
    }
}
