package npetzall.hid.xml;

import npetzall.hid.api.xml.HIDXMLElement;

import javax.xml.namespace.QName;

public abstract class PredicateValueAccessor {

    public PredicateValueAccessor elementText() {
        return new ElementText();
    }

    public PredicateValueAccessor attributeText(QName attributeName) {
        return new AttributeText(attributeName);
    }

    public abstract String getText(HIDXMLElement hidxmlElement);

    class ElementText extends PredicateValueAccessor {

        @Override
        public String getText(HIDXMLElement hidxmlElement) {
            return hidxmlElement.getText();
        }
    }

    class AttributeText extends PredicateValueAccessor {

        private final QName attributeName;

        private AttributeText(QName attributeName) {
            this.attributeName = attributeName;
        }

        @Override
        public String getText(HIDXMLElement hidxmlElement) {
            return hidxmlElement.getAttributeValue(attributeName);
        }
    }

}
