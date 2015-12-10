package npetzall.hid.xml;

import javax.xml.namespace.QName;

public abstract class QNameMatcher {

    public static QNameMatcher localPartOnly() {
        return new LocalPartOnlyMatcher();
    }

    public static QNameMatcher namespaceAndLocalPart() {
        return new NamespaceAndLocalPartMatcher();
    }

    public abstract boolean equals(QName expected, QName actual);

    private static class LocalPartOnlyMatcher extends QNameMatcher {
        @Override
        public boolean equals(QName expected, QName actual) {
            return actual.getLocalPart().equals(expected.getLocalPart());
        }
    }

    private static class NamespaceAndLocalPartMatcher extends QNameMatcher {
        @Override
        public boolean equals(QName expected, QName actual) {
            return actual.equals(expected);
        }
    }
}
