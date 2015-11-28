package npetzall.hid.xml;

import npetzall.hid.api.xml.HIDXmlElement;

import javax.xml.namespace.QName;

public abstract class HIDXPathPart {

    enum Type{
        ELEMENT, ELEMENT_WITH_REQUIREMENTS, ATTRIBUTE
    }

    protected QName qName;
    protected Type type;

    private HIDXPathPart(String namespace, String localName, Type type) {
        qName = new QName(namespace,localName);
        this.type = type;
    }

    public static HIDXPathPart element(String namespace,String localName) {
        return new HIDXPathElementPart(namespace, localName);
    }

    public static HIDXPathPart attribute(String namespace, String localName) {
        return new HIDXPathAttributePart(namespace,localName);
    }

    public abstract boolean matches(HIDXmlElement hidXmlElement);

    public QName getName() {
        return qName;
    }

    public boolean isAttribute() {
        return Type.ATTRIBUTE.equals(type);
    }

    protected boolean lenientQNameEqual(QName other) {
        return ("".equals(qName.getNamespaceURI())
                && qName.getLocalPart().equals(other.getLocalPart()))
                || qName.equals(other);
    }

    static class HIDXPathElementPart extends HIDXPathPart {
        private HIDXPathElementPart(String namespace, String localName) {
            super(namespace,localName,Type.ELEMENT);
        }

        @Override
        public boolean matches(HIDXmlElement hidXmlElement) {
            return lenientQNameEqual(hidXmlElement.getName());
        }
    }

    static class HIDXPathAttributePart extends HIDXPathPart {
        private HIDXPathAttributePart(String namespace, String localName) {
            super(namespace, localName,Type.ATTRIBUTE);
        }

        @Override
        public boolean matches(HIDXmlElement hidXmlElement) {
            for(int i = 0; i< hidXmlElement.getAttributeCount(); i++) {
                if(lenientQNameEqual(hidXmlElement.getAttributeName(i))) {
                    return true;
                }
            }
            return false;
        }
    }

}
