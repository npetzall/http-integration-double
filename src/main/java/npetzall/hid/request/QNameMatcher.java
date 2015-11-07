package npetzall.hid.request;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nosse on 2015-11-04.
 */
public class QNameMatcher implements HIDMatcher {

    @Override
    public boolean matches(HIDRequest hidRequest) {
        return matches(hidRequest.getRequestBodyInputStream());
    }

    public enum ForType {
        ELEMENT, ATTRIBUTE
    }

    private final QName qName;
    private final ForType forType;

    public QNameMatcher(String nameSpace, String localPart, ForType forType) {
        qName = new QName(nameSpace, localPart);
        this.forType = forType;
    }

    public boolean matches(InputStream inputStream) {
        if (inputStream == null) {
            throw new RuntimeException("Inputstream is null");
        }
        try {
            XMLStreamReader xmlStreamReader = XMLInputFactory.newFactory().createXMLStreamReader(inputStream);
            while(xmlStreamReader.hasNext()) {
                if (xmlStreamReader.next() == XMLStreamConstants.START_ELEMENT) {
                    if (qName.equals(xmlStreamReader.getName()) && forType.equals(ForType.ELEMENT)) {
                        return true;
                    }
                    if (forType.equals(ForType.ATTRIBUTE)) {
                        for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++) {
                            if (qName.equals(xmlStreamReader.getAttributeName(i))) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException("Failed creating XMLStreamReader", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
