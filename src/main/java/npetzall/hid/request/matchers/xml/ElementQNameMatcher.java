package npetzall.hid.request.matchers.xml;

import npetzall.hid.api.request.HIDMatcher;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by nosse on 2015-11-04.
 */
public class ElementQNameMatcher extends AbstractQNameMatcher implements HIDMatcher {

    public ElementQNameMatcher(String nameSpace, String localPart) {
        super(nameSpace, localPart);
    }

    @Override
    protected boolean matches(XMLStreamReader xmlStreamReader) throws XMLStreamException {
        if (isStartElement(xmlStreamReader.next()) && qName.equals(xmlStreamReader.getName())) {
            return true;
        }
        return false;
    }
}
