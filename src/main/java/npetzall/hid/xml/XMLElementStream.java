package npetzall.hid.xml;

import npetzall.hid.api.xml.HIDXMLElement;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLElementStream {

    private final XMLStreamReader xmlStreamReader;
    private XMLElementBuilder xmlElementBuilder = new XMLElementBuilder();
    private HIDXMLElement current;
    private HIDXMLElement next;

    private boolean hasNext;

    public XMLElementStream(XMLStreamReader xmlStreamReader) throws XMLStreamException {
        this.xmlStreamReader = xmlStreamReader;
        xmlStreamReader.nextTag();
        if(!xmlStreamReader.isStartElement()) {
            throw new XMLStreamException("Failed to find startElement!");
        }
        hasNext = createHIDXMLElement();
    }

    public boolean hasNext() {
        return hasNext;
    }

    public HIDXMLElement next() throws XMLStreamException {
        current = next;
        hasNext = createHIDXMLElement();
        return current;
    }

    private boolean createHIDXMLElement() throws XMLStreamException {
        if(xmlStreamReader.isStartElement()) {
            return createStartElement();
        } else if (xmlStreamReader.isEndElement()) {
            return createEndElement();
        }
        return false;
    }

    private boolean createStartElement() throws XMLStreamException {
        xmlElementBuilder.setName(xmlStreamReader.getName());
        populateAttributesInElementBuilder();
        if (xmlStreamReader.hasNext()) {
            xmlStreamReader.next();
            if (xmlStreamReader.isCharacters()) {
                xmlElementBuilder.setText(xmlStreamReader.getText());
                next = xmlElementBuilder.createStartXMLElement();
                if (xmlStreamReader.hasNext()) {
                    xmlStreamReader.next();
                }
                return true;
            }
            next = xmlElementBuilder.createStartXMLElement();
            return true;
        } else {
            next = xmlElementBuilder.createStartXMLElement();
            return true;
        }
    }

    private void populateAttributesInElementBuilder() {
        for(int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
            xmlElementBuilder.addAttribute(xmlStreamReader.getAttributeName(i), xmlStreamReader.getAttributeValue(i));
        }
    }

    private boolean createEndElement() throws XMLStreamException {
        xmlElementBuilder.setName(xmlStreamReader.getName());
        next = xmlElementBuilder.createEndXMLElement();
        if (xmlStreamReader.hasNext()) {
            nextTagOrEOF();
        }
        return true;
    }

    private void nextTagOrEOF() throws XMLStreamException {
        while(xmlStreamReader.hasNext()) {
            xmlStreamReader.next();
            if (xmlStreamReader.isStartElement() || xmlStreamReader.isEndElement()) {
                return;
            }
        }
    }

}
