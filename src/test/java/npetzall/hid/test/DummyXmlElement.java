package npetzall.hid.test;

import npetzall.hid.api.xml.HIDXmlElement;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nosse on 2015-11-22.
 */
public class DummyXmlElement implements HIDXmlElement {

    private final QName elementQName;
    private List<DummyXmlAttribute> attributes = new ArrayList<>();

    public DummyXmlElement(String localpart) {
        this(null, localpart);
    }

    public DummyXmlElement( String namespace, String localpart) {
        elementQName = new QName(namespace, localpart);
    }

    public DummyXmlElement addAttribute(String localpart) {
        addAttributeWithValue(null,localpart, null);
        return this;
    }

    public DummyXmlElement addAttribute(String namespace, String localpart) {
        addAttributeWithValue(namespace,localpart, null);
        return this;
    }

    public DummyXmlElement addAttributeWithValue(String localpart, String value) {
        addAttributeWithValue(null,localpart, null);
        return this;
    }

    public DummyXmlElement addAttributeWithValue(String namespace, String localpart, String value) {
        attributes.add(new DummyXmlAttribute(namespace,localpart,value));
        return this;
    }

    @Override
    public QName getName() {
        return elementQName;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public String getAttributeValue(String namespaceURI, String localName) {
        QName requested = new QName(namespaceURI,localName);
        for (DummyXmlAttribute attribute: attributes) {
            if (attribute.attributeQName.equals(requested)) {
                return attribute.value;
            }
        }
        return null;
    }

    @Override
    public int getAttributeCount() {
        return attributes.size();
    }

    @Override
    public QName getAttributeName(int index) {
        return attributes.get(index).attributeQName;
    }

    @Override
    public String getAttributeNamespace(int index) {
        return attributes.get(index).attributeQName.getNamespaceURI();
    }

    @Override
    public String getAttributeLocalName(int index) {
        return attributes.get(index).attributeQName.getLocalPart();
    }

    @Override
    public String getAttributePrefix(int index) {
        return attributes.get(index).attributeQName.getPrefix();
    }

    @Override
    public String getAttributeValue(int index) {
        return attributes.get(index).value;
    }
}
