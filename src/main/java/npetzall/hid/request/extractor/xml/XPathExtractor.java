package npetzall.hid.request.extractor.xml;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.request.HIDDataExtractor;
import npetzall.hid.api.request.HIDRequest;
import npetzall.hid.api.xml.HIDXMLElement;
import npetzall.hid.exception.xml.XMLStreamReaderException;
import npetzall.hid.xml.HIDXPath;
import npetzall.hid.xml.HIDXPathProcessor;
import npetzall.hid.xml.XMLElementStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.util.HashMap;
import java.util.Map;

public class XPathExtractor implements HIDDataExtractor {

    private HIDXPathProcessor xPathProcessor;
    private HashMap<String,String> xpathVariableMap = new HashMap<>();


    public XPathExtractor() {
        xPathProcessor = new HIDXPathProcessor(null);
    }

    public XPathExtractor(Map<String,String> namespaceMap) {
        xPathProcessor = new HIDXPathProcessor(namespaceMap);
    }

    public XPathExtractor xpath(String xPath, String variableName) {
        xPathProcessor.addXPath(xPath);
        xpathVariableMap.put(xPath,variableName);
        return this;
    }

    @Override
    public void extract(HIDRequest request, HIDExchangeContext exchangeContext) {
        xPathProcessor.reset();
        try {
            HIDXMLElement element;
            XMLElementStream xmlElementStream = new XMLElementStream(XMLInputFactory.newFactory().createXMLStreamReader(request.getBodyStream()));
            while(xmlElementStream.hasNext()) {
                element = xmlElementStream.next();
                if (element.isStartElement() && xPathProcessor.startElement(element)) {
                    populateExchangeContext(element, exchangeContext);
                }
                if (element.isEndElement()) {
                    xPathProcessor.endElement(element);
                }
            }
        } catch (XMLStreamException e) {
            throw new XMLStreamReaderException("Failed to read XML during XPathExtraction",e);
        }
    }

    private void populateExchangeContext(HIDXMLElement element, HIDExchangeContext context) {
        for(HIDXPath xPath : xPathProcessor.getMatches()) {
            if (xPath.isAttributeRequested()) {
                context.addAttribute(xpathVariableMap.get(xPath.getXPathString()), element.getAttributeValue(xPath.getAttributePart().getName()));
            } else if (xPath.isTextRequested()) {
                context.addAttribute(xpathVariableMap.get(xPath.getXPathString()), element.getText());
            }
        }
    }
}
