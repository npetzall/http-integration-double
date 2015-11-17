package npetzall.hid.request.extractor;

import npetzall.hid.request.extractor.xml.XPathExtractor;

public class HIDExtractors {

    private HIDExtractors() {
        //Should only be used thru static methods
    }

    public static XPathExtractor xPathExtractor(String xPath, String variableName) {
        return new XPathExtractor(xPath, variableName);
    }

    public static RegExExtractor regExExtractor(String regEx) {
        return new RegExExtractor(regEx);
    }
}
