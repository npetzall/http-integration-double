package npetzall.hid.test.unit.xml;

import npetzall.hid.test.DummyStartXMLElement;
import npetzall.hid.xml.HIDXPathPredicate;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HIDXPathPredicateTest {

    @Test
    public void createPredicate() {
        HIDXPathPredicate hidxPathPredicate = new HIDXPathPredicate("@id = '123'");
    }

    @Test
    public void predicateMatchesAttribute() {
        HIDXPathPredicate hidxPathPredicate = new HIDXPathPredicate("@id = '123'");
        DummyStartXMLElement hidXmlElement = new DummyStartXMLElement("something").addAttributeWithValue("id","123");
        assertThat(hidxPathPredicate.matches(hidXmlElement)).isTrue();
    }

    @Test
    public void predicateDoesntMatcheAttribute() {
        HIDXPathPredicate hidxPathPredicate = new HIDXPathPredicate("@id = '456'");
        DummyStartXMLElement hidXmlElement = new DummyStartXMLElement("something").addAttributeWithValue("id","123");
        assertThat(hidxPathPredicate.matches(hidXmlElement)).isFalse();
    }

    @Test
    public void predicateMatchesElementText() {
        HIDXPathPredicate hidxPathPredicate = new HIDXPathPredicate("text() = 'something'");
        DummyStartXMLElement hidXmlElement = new DummyStartXMLElement("something").addAttributeWithValue("id","123");
        assertThat(hidxPathPredicate.matches(hidXmlElement)).isTrue();
    }

    @Test(enabled = false)
    public void predicateDoesntMatcheElementText() {
        HIDXPathPredicate hidxPathPredicate = new HIDXPathPredicate("text() = 'somethingelse'");
        DummyStartXMLElement hidXmlElement = new DummyStartXMLElement("something").addAttributeWithValue("id","123");
        assertThat(hidxPathPredicate.matches(hidXmlElement)).isFalse();
    }
}
