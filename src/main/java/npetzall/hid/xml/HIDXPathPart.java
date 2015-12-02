package npetzall.hid.xml;

import npetzall.hid.api.xml.HIDXMLElement;

import javax.xml.namespace.QName;

public abstract class HIDXPathPart {

    enum Type{
        ELEMENT, ELEMENT_WITH_REQUIREMENTS, ATTRIBUTE, TEXT
    }

    protected QName qName;
    protected boolean wildCard;
    protected Type type;

    public static HIDXPathPart element(String namespace,String localName) {
        return new HIDXPathElementPart(namespace, localName);
    }

    public static HIDXPathPart attribute(String namespace, String localName) {
        return new HIDXPathAttributePart(namespace,localName);
    }

    public static HIDXPathPart text() {
        return new HIDXPathTextPart();
    }

    public abstract boolean matches(HIDXMLElement hidXmlElement);

    protected boolean qNameEquals(QName other) {
        if (wildCard) {
            return qName.getLocalPart().equals(other.getLocalPart());
        }
        return qName.equals(other);
    }

    public QName getName() {
        return qName;
    }

    static class HIDXPathElementPart extends HIDXPathPart {
        private HIDXPathElementPart(String namespace, String localName) {
            qName = new QName(namespace,localName);
            type = Type.ELEMENT;
            wildCard = "*".equals(namespace);
        }

        @Override
        public boolean matches(HIDXMLElement hidXmlElement) {
            return qNameEquals(hidXmlElement.getName());
        }
    }

    static class HIDXPathAttributePart extends HIDXPathPart {
        private HIDXPathAttributePart(String namespace, String localName) {
            qName = new QName(namespace,localName);
            type = Type.ATTRIBUTE;
            wildCard = "*".equals(namespace);
        }

        @Override
        public boolean matches(HIDXMLElement hidXmlElement) {
            return hidXmlElement.hasAttribute(qName);
        }
    }

    static class HIDXPathTextPart extends HIDXPathPart {

        @Override
        public boolean matches(HIDXMLElement hidXmlElement) {
            return hidXmlElement.hasText();
        }
    }

}
