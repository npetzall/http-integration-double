package npetzall.hid.api.exchange;

import java.util.List;
import java.util.Set;

public interface HIDExchangeContext {
    void addAttribute(String key, String value);
    boolean contains(String key);
    String getAttribute(String key);
    Set<String> getAttributeKeys();
    List<String> getAttributeValues();
    int size();
}
