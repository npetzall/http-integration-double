package npetzall.hid.test.unit.xml;

import npetzall.hid.api.xml.HIDXMLElement;
import npetzall.hid.test.TestUtil;
import npetzall.hid.xml.XMLElementStream;
import org.testng.annotations.Test;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class XMLElementStreamTest {

    @Test
    public void xmlElementStreamTest() throws IOException, XMLStreamException {
        XMLStreamReader xmlStreamReader = XMLInputFactory.newFactory().createXMLStreamReader(TestUtil.getResourceStream("/matchers/QNameMatcherReverse.xml"));
        XMLElementStream xmlElementStream = new XMLElementStream(xmlStreamReader);
        assertThat(xmlElementStream.hasNext()).isTrue();
        HIDXMLElement element = xmlElementStream.next();
        assertThat(element.isStartElement()).isTrue();
        assertThat(element.getName().equals(new QName("http://npetzall/hid","exchange"))).isTrue();
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isStartElement()).isTrue();
        assertThat(element.getName().equals(new QName("http://npetzall/hid","request"))).isTrue();
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isStartElement()).isTrue();
        assertThat(element.getName().equals(new QName("http://npetzall/hid","reverse"))).isTrue();
        assertThat(element.hasAttribute(new QName("http://npetzall/nohid","someother"))).isTrue();
        assertThat(element.getAttributeValue(new QName("http://npetzall/nohid","someother"))).isEqualTo("no");
        assertThat(element.hasAttribute(new QName("","someother"))).isTrue();
        assertThat(element.getAttributeValue(new QName("","someother"))).isEqualTo("yes");
        assertThat(element.hasAttribute(new QName("","exists"))).isTrue();
        assertThat(element.getAttributeValue(new QName("","exists"))).isEqualTo("true");
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isStartElement()).isTrue();
        assertThat(element.getName().equals(new QName("http://npetzall/hid","message"))).isTrue();
        assertThat(element.getText()).isEqualTo("npetzall");
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isEndElement()).isTrue();
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.getName().equals(new QName("http://npetzall/nohid","message"))).isTrue();
        assertThat(element.getText()).isEqualTo("nils");
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isEndElement()).isTrue();
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isEndElement()).isTrue();
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isStartElement()).isTrue();
        assertThat(element.getName().equals(new QName("http://npetzall/hid","reverse"))).isTrue();
        assertThat(element.hasAttribute(new QName("","someother"))).isTrue();
        assertThat(element.getAttributeValue(new QName("","someother"))).isEqualTo("false");
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isStartElement()).isTrue();
        assertThat(element.getName().equals(new QName("http://npetzall/hid","message"))).isTrue();
        assertThat(element.getText()).isEqualTo("someotherperson");
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isEndElement()).isTrue();
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isEndElement()).isTrue();
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isEndElement()).isTrue();
        assertThat(xmlElementStream.hasNext()).isTrue();
        element = xmlElementStream.next();
        assertThat(element.isEndElement()).isTrue();
        assertThat(xmlElementStream.hasNext()).isFalse();
    }

}
