package npetzall.hid.request.matchers.xml;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;
import npetzall.hid.api.xml.HIDXMLElement;
import npetzall.hid.exception.xml.XMLStreamReaderException;
import npetzall.hid.xml.HIDXPathProcessor;
import npetzall.hid.xml.XMLElementStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
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
            HIDXMLElement element;
            XMLElementStream xmlElementStream = new XMLElementStream(XMLInputFactory.newFactory().createXMLStreamReader(hidRequest.getBodyStream()));
            while(xmlElementStream.hasNext()) {
                element = xmlElementStream.next();
                if (element.isStartElement() && xPathProcessor.startElement(element)) {
                    return true;
                }
                if (element.isEndElement()) {
                    xPathProcessor.endElement(element);
                }
            }
        } catch (XMLStreamException e) {
            throw new XMLStreamReaderException("Failed to read XML during XPathExtraction",e);
        }
        return false;
    }

}
