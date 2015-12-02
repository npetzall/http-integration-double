package npetzall.hid.test;

import npetzall.hid.api.xml.HIDXMLElement;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

public class DummyStartXMLElement implements HIDXMLElement {

    private final QName elementQName;
    private final String text;
    private Map<QName, String> attributes = new HashMap<>();

    public DummyStartXMLElement(String localpart) {
        this(null, localpart, null);
    }

    public DummyStartXMLElement(String namespace, String localpart) {
        this(namespace,localpart,null);
    }

    public DummyStartXMLElement(String namespace, String localpart, String text) {
        elementQName = new QName(namespace,localpart);
        this.text = text;
    }

    public DummyStartXMLElement addAttribute(String localpart) {
        addAttributeWithValue(null,localpart, null);
        return this;
    }

    public DummyStartXMLElement addAttribute(String namespace, String localpart) {
        addAttributeWithValue(namespace,localpart, null);
        return this;
    }

    public DummyStartXMLElement addAttributeWithValue(String localpart, String value) {
        addAttributeWithValue(null,localpart, null);
        return this;
    }

    public DummyStartXMLElement addAttributeWithValue(String namespace, String localpart, String value) {
        attributes.put(new QName(namespace,localpart),value);
        return this;
    }

    @Override
    public boolean isStartElement() {
        return true;
    }

    @Override
    public boolean isEndElement() {
        return false;
    }

    @Override
    public QName getName() {
        return elementQName;
    }

    @Override
    public boolean hasText() {
        return text != null && !text.isEmpty();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean hasAttribute(QName attributeName) {
        return attributes.containsKey(attributeName);
    }

    @Override
    public String getAttributeValue(QName attributeName) {
        return attributes.get(attributeName);
    }

}
