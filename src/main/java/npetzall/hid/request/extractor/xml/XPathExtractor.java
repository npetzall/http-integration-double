package npetzall.hid.request.extractor.xml;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.request.HIDDataExtractor;
import npetzall.hid.api.request.HIDRequest;
import npetzall.hid.exception.xml.XMLStreamReaderException;
import npetzall.hid.xml.HIDStreamReaderWrapper;
import npetzall.hid.xml.HIDXPath;
import npetzall.hid.xml.HIDXPathPart;
import npetzall.hid.xml.HIDXPathProcessor;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            XMLStreamReader xmlStreamReader = XMLInputFactory.newFactory().createXMLStreamReader(request.getBodyStream());
            while(xmlStreamReader.hasNext()) {
                xmlStreamReader.next();
                if (xmlStreamReader.isStartElement() && xPathProcessor.startElement(new HIDStreamReaderWrapper(xmlStreamReader))) {
                    processMatches(xmlStreamReader, exchangeContext);
                }
                if (xmlStreamReader.isEndElement()) {
                    xPathProcessor.endElement(new HIDStreamReaderWrapper(xmlStreamReader));
                }
            }
        } catch (XMLStreamException e) {
            throw new XMLStreamReaderException("Failed to read XML during XPathExtraction",e);
        }

    }

    private void processMatches(XMLStreamReader xmlStreamReader, HIDExchangeContext exchangeContext) throws XMLStreamException {
        List<HIDXPath> attributesValueRequested = new ArrayList<>();
        List<HIDXPath> elementTextRequested = new ArrayList<>();
        for (HIDXPath hidxPath : xPathProcessor.getMatches()) {
            if (hidxPath.isAttributeRequested()){
                attributesValueRequested.add(hidxPath);
            } else {
                elementTextRequested.add(hidxPath);
            }
        }
        extractAttributeValue(attributesValueRequested,xmlStreamReader,exchangeContext);
        extractElementText(elementTextRequested,xmlStreamReader,exchangeContext);
    }

    private void extractAttributeValue(List<HIDXPath> attributesValueRequested, XMLStreamReader xmlStreamReader, HIDExchangeContext exchangeContext) {
        for(HIDXPath hidxPath: attributesValueRequested) {
            HIDXPathPart lastPart =  hidxPath.getAttributePart();
            for(int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                if (leniantQNameEquals(lastPart.getName(),xmlStreamReader.getAttributeName(i))) {
                    exchangeContext.addAttribute(xpathVariableMap.get(hidxPath.getXPathString()), xmlStreamReader.getAttributeValue(i));
                }
            }
        }
    }

    private static boolean leniantQNameEquals(QName expected, QName actual) {
        return ("".equals(expected.getNamespaceURI())
                        && expected.getLocalPart().equals(actual.getLocalPart()))
                        || expected.equals(actual);
    }

    private void extractElementText(List<HIDXPath> elementTextRequested, XMLStreamReader xmlStreamReader, HIDExchangeContext exchangeContext) throws XMLStreamException {
        if (!elementTextRequested.isEmpty()) {
            xmlStreamReader.next();
            String elementText = xmlStreamReader.getText();
            for(HIDXPath hidxPath: elementTextRequested) {
                exchangeContext.addAttribute(xpathVariableMap.get(hidxPath.getXPathString()), elementText);
            }
        }
    }

}
