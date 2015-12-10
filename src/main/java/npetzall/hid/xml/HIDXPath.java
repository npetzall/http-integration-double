package npetzall.hid.xml;

import npetzall.hid.api.xml.HIDXMLElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HIDXPath {

    private static Pattern predicateOrIndexPattern = Pattern.compile("\\[(?<requirement>.*)\\]");

    private final String xPathString;
    private List<HIDXPathPart> hidxPathPartList = new ArrayList<>();
    private HIDXPathPart attributePart = null;
    private HIDXPathPart textPart = null;
    private int hidxPathPartListIndex = 0;

    public HIDXPath(Map<String,String> namespaceMap, String xPathString) {
        this.xPathString = xPathString;
        parse(namespaceMap);
    }

    private void parse(Map<String,String> namespaceMap) {
        String[] pathParts;
        if (xPathString.startsWith("//")) {
            pathParts = xPathString.substring(2).split("/");
        } else {
            pathParts = xPathString.substring(1).split("/");
        }
        for(String pathPart : pathParts) {
            String namespace = "*";
            String localPart= pathPart;
            if(pathPart.contains(":")) {
                String[] tokens = pathPart.split(":",2);
                namespace = namespaceMap.get(tokens[0]);
                localPart = tokens[1];
            }
            if(localPart.charAt(0) == '@') {
                attributePart = HIDXPathPart.attribute(namespace, localPart.substring(1));
            } else if ("text()".equals(localPart)) {
                textPart = HIDXPathPart.text();
            } else {
                Matcher matcher;
                if((matcher = predicateOrIndexPattern.matcher(localPart)).find()) {
                    String preidcateOrIndex = matcher.group("requirement");
                    localPart = localPart.replace("["+preidcateOrIndex+"]","");
                    if (preidcateOrIndex.matches("\\d+")) {
                        hidxPathPartList.add(HIDXPathPart.elementWithIndex(namespace,localPart,Integer.parseInt(preidcateOrIndex)));
                    } else {
                        hidxPathPartList.add(HIDXPathPart.elementWithPredicate(namespace, localPart, preidcateOrIndex));
                    }
                } else {
                    hidxPathPartList.add(HIDXPathPart.element(namespace, localPart));
                }
            }
        }
    }

    public List<HIDXPathPart> getHidxPathParts() {
        return Collections.unmodifiableList(hidxPathPartList);
    }

    public boolean isAttributeRequested() {
        return attributePart != null;
    }

    public boolean isTextRequested() {
        return attributePart == null;
    }

    public HIDXPathPart getAttributePart() {
        return attributePart;
    }

    public String getXPathString() {
        return xPathString;
    }

    public void reset() {
        hidxPathPartListIndex = 0;
    }

    public boolean startElement(HIDXMLElement hidXmlElement) {
        if(hidxPathPartListIndex == hidxPathPartList.size()) {
            return false;
        }
        if (hidxPathPartList.get(hidxPathPartListIndex).matches(hidXmlElement)) {
            hidxPathPartListIndex++;
        }
        return isMatch(hidXmlElement);
    }

    private boolean isMatch(HIDXMLElement hidXmlElement) {
        if(hidxPathPartList.size() == hidxPathPartListIndex) {
            if (attributePart != null) {
                return attributePart.matches(hidXmlElement);
            } else if (textPart != null) {
                return hidXmlElement.hasText();
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean endElement(HIDXMLElement hidXmlElement) {
        if(hidxPathPartListIndex == 0) {
            return false;
        }
        if (hidxPathPartList.get(hidxPathPartListIndex-1).matches(hidXmlElement)) {
            hidxPathPartListIndex--;
            if(hidxPathPartListIndex == hidxPathPartList.size() -1) {
                return true;
            }
        }
        return false;
    }
}
