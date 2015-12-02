package npetzall.hid.xml;

import npetzall.hid.api.xml.HIDXMLElement;

import javax.xml.namespace.QName;

public class EndXMLElement implements HIDXMLElement {

    private final QName name;

    public EndXMLElement(QName name) {
        this.name = name;
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
        return name;
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
