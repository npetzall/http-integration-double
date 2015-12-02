package npetzall.hid.api.xml;

import javax.xml.namespace.QName;

public interface HIDXMLElement {

    boolean isStartElement();
    boolean isEndElement();

    QName getName();
    boolean hasText();
    String getText();

    boolean hasAttribute(QName attributeName);
    String getAttributeValue(QName attributeName);

}
