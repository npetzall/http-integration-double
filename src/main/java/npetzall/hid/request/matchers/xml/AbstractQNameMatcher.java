package npetzall.hid.request.matchers.xml;

import npetzall.hid.api.request.HIDMatcher;
import npetzall.hid.api.request.HIDRequest;
import npetzall.hid.exception.xml.XMLStreamReaderException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

import static npetzall.hid.io.IOUtils.closeQuietly;

/**
 * Created by nosse on 2015-11-12.
 */
public abstract class AbstractQNameMatcher implements HIDMatcher {

    protected final QName qName;

    public AbstractQNameMatcher(String nameSpace, String localPart) {
        qName = new QName(nameSpace, localPart);
    }

    @Override
    public boolean matches(HIDRequest hidRequest) {
        InputStream inputStream = hidRequest.getBodyStream();
        try {
            XMLStreamReader xmlStreamReader = XMLInputFactory.newFactory().createXMLStreamReader(inputStream);
            return doMatches(xmlStreamReader);
        } catch (XMLStreamException e) {
            throw new XMLStreamReaderException("Failed creating XMLStreamReader when matching QName", e);
        } finally {
            closeQuietly(inputStream);
        }
    }

    private boolean doMatches(XMLStreamReader xmlStreamReader) throws XMLStreamException {
        while(xmlStreamReader.hasNext()) {
            if(matches(xmlStreamReader))
                return true;
        }
        return false;
    }

    protected static boolean isStartElement(int next) {
        return next == XMLStreamConstants.START_ELEMENT;
    }

    protected abstract boolean matches(XMLStreamReader xmlStreamReader) throws XMLStreamException;
}
