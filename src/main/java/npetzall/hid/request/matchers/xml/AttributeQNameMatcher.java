package npetzall.hid.request.matchers.xml;

import npetzall.hid.api.request.HIDMatcher;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class AttributeQNameMatcher extends AbstractQNameMatcher implements HIDMatcher {

    public AttributeQNameMatcher(String nameSpace, String localPart) {
        super(nameSpace,localPart);
    }

    @Override
    protected boolean matches(XMLStreamReader xmlStreamReader) throws XMLStreamException {
        if (isStartElement(xmlStreamReader.next())) {
            for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                if (qName.equals(xmlStreamReader.getAttributeName(i))) {
                    return true;
                }
            }
        }
        return false;
    }
}
