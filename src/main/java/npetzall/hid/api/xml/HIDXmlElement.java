package npetzall.hid.api.xml;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;

public interface HIDXmlElement {

    QName getName();
    Location getLocation();

    String getAttributeValue(String namespaceURI, String localName);
    int getAttributeCount();
    QName getAttributeName(int index);
    String getAttributeNamespace(int index);
    String getAttributeLocalName(int index);
    String getAttributePrefix(int index);
    String getAttributeValue(int index);

}

