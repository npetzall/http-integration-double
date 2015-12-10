package npetzall.hid.xml;

import npetzall.hid.api.xml.HIDXMLElement;

import java.util.regex.Pattern;

public class HIDXPathPredicate {

    Pattern predicatePattern = Pattern.compile("(?<right>.*)(?<operator>=|<=|>=|!=)(<?left>.*)");

    public HIDXPathPredicate(String predicate) {

    }

    public boolean matches(HIDXMLElement hidXmlElement) {
        return true;
    }
}
