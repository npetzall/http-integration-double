package npetzall.hid.request.matchers.xml;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;
import npetzall.hid.request.matchers.xml.exception.XMLStreamReaderException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by nosse on 2015-11-12.
 */
public abstract class AbstractQNameMatcher implements HIDMatcher {

    private static final Logger LOGGER = Logger.getLogger(AbstractQNameMatcher.class.getName());

    protected final QName qName;

    public AbstractQNameMatcher(String nameSpace, String localPart) {
        qName = new QName(nameSpace, localPart);
    }

    @Override
    public boolean matches(HIDRequest hidRequest) {
        InputStream inputStream = hidRequest.getBodyStream();
        try {
            XMLStreamReader xmlStreamReader = XMLInputFactory.newFactory().createXMLStreamReader(inputStream);
            while(xmlStreamReader.hasNext()) {
                if(matches(xmlStreamReader))
                    return true;
            }
        } catch (XMLStreamException e) {
            throw new XMLStreamReaderException("Failed creating XMLStreamReader", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOGGER.log(Level.FINE, "Exception during InputStream.close()", e);
            }
        }
        return false;
    }

    protected static boolean isStartElement(int next) {
        return next == XMLStreamConstants.START_ELEMENT;
    }

    protected abstract boolean matches(XMLStreamReader xmlStreamReader) throws XMLStreamException;
}
