package npetzall.hid.api.exchange;

public interface HIDExchangeContext {
    void addAttribute(String key, String value);
    boolean contains(String key);
    String getAttribute(String key);
    int size();
}
