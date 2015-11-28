package npetzall.hid.request.matchers.xml;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;
import npetzall.hid.exception.xml.XMLStreamReaderException;
import npetzall.hid.xml.HIDStreamReaderWrapper;
import npetzall.hid.xml.HIDXPathProcessor;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Map;

public class XPathMatcher implements HIDMatcher {

    private HIDXPathProcessor xPathProcessor;

    public XPathMatcher(Map<String,String> namespaceMap) {
        xPathProcessor = new HIDXPathProcessor(namespaceMap);
    }

    public XPathMatcher xpath(String xPath) {
        xPathProcessor.addXPath(xPath);
        return this;
    }

    @Override
    public boolean matches(HIDRequest hidRequest) {
        xPathProcessor.reset();
        try {
            XMLStreamReader xmlStreamReader = XMLInputFactory.newFactory().createXMLStreamReader(hidRequest.getBodyStream());
            while(xmlStreamReader.hasNext()) {
                xmlStreamReader.next();
                if (xmlStreamReader.isStartElement() && xPathProcessor.startElement(new HIDStreamReaderWrapper(xmlStreamReader))) {
                    return true;
                }
                if (xmlStreamReader.isEndElement()) {
                    xPathProcessor.endElement(new HIDStreamReaderWrapper(xmlStreamReader));
                }
            }
        } catch (XMLStreamException e) {
            throw new XMLStreamReaderException("Failed to read XML during XPathExtraction",e);
        }
        return false;
    }
}
