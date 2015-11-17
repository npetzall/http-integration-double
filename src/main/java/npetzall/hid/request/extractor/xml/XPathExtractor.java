package npetzall.hid.request.extractor.xml;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.request.HIDDataExtractor;
import npetzall.hid.api.request.HIDRequest;
import npetzall.hid.exception.xml.XMLStreamReaderException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.HashMap;

public class XPathExtractor implements HIDDataExtractor {

    private HashMap<String, String> xpathVariableMap = new HashMap<>();

    public XPathExtractor(String xPath, String variableName) {
        xpathVariableMap.put(xPath, variableName);
    }

    public XPathExtractor extract(String xPath, String variableName) {
        xpathVariableMap.put(xPath, variableName);
        return this;
    }

    @Override
    public void extract(HIDRequest request, HIDExchangeContext exchangeContext) {
        try {
            String path = "";
            XMLStreamReader xmlStreamReader = XMLInputFactory.newFactory().createXMLStreamReader(request.getBodyStream());
            while(xmlStreamReader.hasNext()) {
                xmlStreamReader.next();
                if(xmlStreamReader.isStartElement()) {
                    path = addPathElement(path, xmlStreamReader.getLocalName());
                    checkPath(exchangeContext, path, xmlStreamReader);
                } else if (xmlStreamReader.isEndElement()) {
                    path= removePathElement(path, xmlStreamReader.getLocalName());
                }
            }
        } catch (XMLStreamException e) {
            throw new XMLStreamReaderException("Failed to read XML during XPathExtraction",e);
        }

    }

    private static String addPathElement(String path, String element) {
        return path + "/" + element;
    }

    private static String removePathElement(String path, String element) {
        return path.substring(0, path.length() - (element.length() + 1));
    }

    private void checkPath(HIDExchangeContext exchangeContext, String path, XMLStreamReader xmlStreamReader) throws XMLStreamException {
        String variable = xpathVariableMap.get(path);
        if (variable != null) {
            xmlStreamReader.next();
            exchangeContext.addAttribute(variable, xmlStreamReader.getText());
        }
    }
}
