package npetzall.hid.xml;

import java.util.HashMap;

public class NamespaceMap extends HashMap<String,String> {

    public NamespaceMap namespace(String prefix, String namespace) {
        put(prefix,namespace);
        return this;
    }

}
