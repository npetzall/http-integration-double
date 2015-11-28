package npetzall.hid.xml;

import npetzall.hid.api.xml.HIDXmlElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HIDXPathProcessor {

    private Map<String,String> namespaceMap;

    private List<HIDXPath> xpaths = new ArrayList<>();
    private List<HIDXPath> matches = new ArrayList<>();

    public HIDXPathProcessor(Map<String, String> namespaceMap) {
        if (namespaceMap == null) {
            this.namespaceMap = Collections.emptyMap();
        } else {
            this.namespaceMap = namespaceMap;
        }
    }

    public void addXPath(String xPath) {
        xpaths.add(new HIDXPath(namespaceMap,xPath));
    }

    public void reset() {
        for(HIDXPath hidxPath : xpaths) {
            hidxPath.reset();
        }
    }

    public boolean startElement(HIDXmlElement hidXmlElement) {
        matches.clear();
        for(HIDXPath hidxPath : xpaths) {
            if(hidxPath.startElement(hidXmlElement)) {
                matches.add(hidxPath);
            }
        }
        return !matches.isEmpty();
    }

    public List<HIDXPath> getMatches() {
        return matches;
    }

    public boolean endElement(HIDXmlElement hidXmlElement) {
        matches.clear();
        for(HIDXPath hidxPath : xpaths) {
            if(hidxPath.endElement(hidXmlElement)) {
                matches.add(hidxPath);
            }
        }
        return !matches.isEmpty();
    }
}
