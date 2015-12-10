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

    public static HIDXPathPart element(String namespace,String localPart) {
        return new HIDXPathElementPart(namespace, localPart);
    }

    public static HIDXPathPart elementWithIndex(String namespace, String localPart, int index) {
        return new HIDXPathElementIndexPart(namespace, localPart, index);
    }

    public static HIDXPathPart elementWithPredicate(String namespace, String localPart, String predicate) {
        return new HIDXPathElementWithPredicatePart(namespace, localPart, predicate);
    }

    public static HIDXPathPart attribute(String namespace, String localPart) {
        return new HIDXPathAttributePart(namespace,localPart);
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

        private HIDXPathElementPart(String namespace, String localPart) {
            qName = new QName(namespace,localPart);
            type = Type.ELEMENT;
            wildCard = "*".equals(namespace);
        }

        @Override
        public boolean matches(HIDXMLElement hidXmlElement) {
            return qNameEquals(hidXmlElement.getName());
        }
    }

    static class HIDXPathElementIndexPart extends HIDXPathPart {

        private int currentCount;
        private final int matchingCount;

        private HIDXPathElementIndexPart(String namespace, String localPart, int count) {
            qName = new QName(namespace,localPart);
            type = Type.ELEMENT_WITH_REQUIREMENTS;
            matchingCount = count;
        }

        @Override
        public boolean matches(HIDXMLElement hidXmlElement) {
            return qNameEquals(hidXmlElement.getName()) && ++currentCount == matchingCount;
        }
    }

    static class HIDXPathElementWithPredicatePart extends HIDXPathPart {

        private HIDXPathElementWithPredicatePart(String namespace, String localPart, String predicate) {
            qName = new QName(namespace,localPart);
            type = Type.ELEMENT_WITH_REQUIREMENTS;
        }

        @Override
        public boolean matches(HIDXMLElement hidXmlElement) {
            return false;
        }
    }

    static class HIDXPathAttributePart extends HIDXPathPart {

        private HIDXPathAttributePart(String namespace, String localPart) {
            qName = new QName(namespace,localPart);
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
