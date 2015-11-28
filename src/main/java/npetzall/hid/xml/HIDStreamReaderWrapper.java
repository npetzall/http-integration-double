package npetzall.hid.xml;

import npetzall.hid.api.xml.HIDXmlElement;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;

public class HIDStreamReaderWrapper implements HIDXmlElement {

    private final XMLStreamReader xmlStreamReader;

    public HIDStreamReaderWrapper(XMLStreamReader xmlStreamReader) {
        this.xmlStreamReader = xmlStreamReader;
    }

    @Override
    public QName getName() {
        return xmlStreamReader.getName();
    }

    @Override
    public Location getLocation() {
        return xmlStreamReader.getLocation();
    }

    @Override
    public String getAttributeValue(String namespaceURI, String localName) {
        return xmlStreamReader.getAttributeValue(namespaceURI, localName);
    }

    @Override
    public int getAttributeCount() {
        return xmlStreamReader.getAttributeCount();
    }

    @Override
    public QName getAttributeName(int index) {
        return xmlStreamReader.getAttributeName(index);
    }

    @Override
    public String getAttributeNamespace(int index) {
        return xmlStreamReader.getAttributeNamespace(index);
    }

    @Override
    public String getAttributeLocalName(int index) {
        return xmlStreamReader.getAttributeLocalName(index);
    }

    @Override
    public String getAttributePrefix(int index) {
        return xmlStreamReader.getAttributePrefix(index);
    }

    @Override
    public String getAttributeValue(int index) {
        return xmlStreamReader.getAttributeValue(index);
    }
}
