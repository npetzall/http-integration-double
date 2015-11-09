package npetzall.hid.api.exchange;

/**
 * Created by nosse on 2015-11-07.
 */
public interface HIDExchangeContext {
    void addAttribute(String key, String value);
    boolean contains(String key);
    String getAttribute(String key);
    int size();
    void clear();
}
