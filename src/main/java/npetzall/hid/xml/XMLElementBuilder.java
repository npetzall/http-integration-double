package npetzall.hid.xml;

import npetzall.hid.api.xml.HIDXMLElement;

import javax.xml.namespace.QName;
import java.util.HashMap;

public class XMLElementBuilder {

    private QName name;
    private String text;
    private HashMap<QName,String> attributes = new HashMap<>();

    public void setName(QName name) {
        this.name = name;
        text = null;
        attributes.clear();
    }

    public void setText(String text) {
        if (text.trim().isEmpty()) {
            return;
        }
        this.text = text.trim();
    }

    public void addAttribute(QName name, String value) {
        attributes.put(name, value);
    }

    public HIDXMLElement createStartXMLElement() {
        return new StartXMLElement(name, text, new HashMap<QName, String>(attributes));
    }

    public HIDXMLElement createEndXMLElement() {
        return new EndXMLElement(name);
    }
}
