package npetzall.hid.exchange.extractor.xml;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.request.HIDDataExtractor;
import npetzall.hid.api.request.HIDRequest;

import java.util.HashMap;

/**
 * Created by nosse on 2015-11-11.
 */
public class XPathExtractor implements HIDDataExtractor {

    private HashMap<String, String> xpathVariableMap = new HashMap<>();

    public XPathExtractor(String xPath, String variableName) {
        xpathVariableMap.put(xPath, variableName);
    }

    public XPathExtractor extract(String xPath, String variableName) {
        xpathVariableMap.put(xPath, variableName);
        return this;
    }

    @Override
    public void extract(HIDRequest request, HIDExchangeContext exchangeContext) {

    }
}
