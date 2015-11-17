package npetzall.hid.exception.xml;

import javax.xml.stream.XMLStreamException;

public class XMLStreamReaderException extends RuntimeException {
    public XMLStreamReaderException(String message, XMLStreamException e) {
        super(message,e);
    }
}
