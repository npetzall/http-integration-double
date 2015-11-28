package npetzall.hid.test;

import javax.xml.namespace.QName;

/**
 * Created by nosse on 2015-11-22.
 */
public class DummyXmlAttribute {

    public final QName attributeQName;
    public final String value;

    public DummyXmlAttribute(String namespace, String localpart, String value) {
        attributeQName = new QName(namespace, localpart);
        this.value = value;
    }

}
