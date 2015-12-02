package npetzall.hid.xml;

import npetzall.hid.api.xml.HIDXMLElement;

import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class StartXMLElement implements HIDXMLElement {

    private final QName name;
    private final String text;

    private final Map<QName, String> attributes;

    public StartXMLElement(QName name, String text, Map<QName,String> attributes) {
        this.name = name;
        this.text = text;
        this.attributes = attributes;
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
        return name;
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
        if ("*".equals(attributeName.getNamespaceURI())) {
            return hasAttributeUsingLocalPart(attributeName.getLocalPart());
        }
        return attributes.containsKey(attributeName);
    }

    private boolean hasAttributeUsingLocalPart(String localPart) {
        Iterator<QName> keyIterator = attributes.keySet().iterator();
        while(keyIterator.hasNext()) {
            if (localPart.equals(keyIterator.next().getLocalPart())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getAttributeValue(QName attributeName) {
        if ("*".equals(attributeName.getNamespaceURI())) {
            return getAttributeUsingLocalPart(attributeName.getLocalPart());
        }
        return attributes.get(attributeName);
    }

    private String getAttributeUsingLocalPart(String localPart) {
        Iterator<Entry<QName,String>> entryIterator = attributes.entrySet().iterator();
        Entry<QName,String> entry;
        while(entryIterator.hasNext()) {
            entry = entryIterator.next();
            if(localPart.equals(entry.getKey().getLocalPart())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
