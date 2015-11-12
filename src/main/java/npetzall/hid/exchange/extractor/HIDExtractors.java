package npetzall.hid.exchange.extractor;

import npetzall.hid.exchange.extractor.xml.XPathExtractor;

public class HIDExtractors {

    private HIDExtractors() {
        //Should only be used thru static methods
    }

    public static XPathExtractor xPathExtractor(String xPath, String variableName) {
        return new XPathExtractor(xPath, variableName);
    }
}
