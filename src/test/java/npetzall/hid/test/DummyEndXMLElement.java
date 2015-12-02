package npetzall.hid.test;

import npetzall.hid.api.xml.HIDXMLElement;

import javax.xml.namespace.QName;

public class DummyEndXMLElement implements HIDXMLElement {

    private final QName elementQName;

    public DummyEndXMLElement(String localpart) {
        this(null, localpart);
    }

    public DummyEndXMLElement(String namespace, String localpart) {
        elementQName = new QName(namespace,localpart);
    }

    @Override
    public boolean isStartElement() {
        return false;
    }

    @Override
    public boolean isEndElement() {
        return true;
    }

    @Override
    public QName getName() {
        return elementQName;
    }

    @Override
    public boolean hasText() {
        return false;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public boolean hasAttribute(QName attributeName) {
        return false;
    }

    @Override
    public String getAttributeValue(QName attributeName) {
        return null;
    }
}
