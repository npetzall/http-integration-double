package npetzall.hid.exception.xml;

import javax.xml.stream.XMLStreamException;

/**
 * Created by nosse on 2015-11-12.
 */
public class XMLStreamReaderException extends RuntimeException {
    public XMLStreamReaderException(String message, XMLStreamException e) {
        super(message,e);
    }
}
