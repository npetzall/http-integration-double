package npetzall.hid.request.extractor;

import npetzall.hid.request.extractor.xml.XPathExtractor;

import java.util.Map;

public class HIDExtractors {

    private HIDExtractors() {
        //Should only be used thru static methods
    }

    public static XPathExtractor xPathExtractor() {
        return new XPathExtractor(null);
    }

    public static XPathExtractor xPathExtractor(Map<String,String> namespaceMap) {
        return new XPathExtractor(namespaceMap);
    }

    public static RegExExtractor regExExtractor(String regEx) {
        return new RegExExtractor(regEx);
    }
}
