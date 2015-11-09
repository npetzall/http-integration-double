package npetzall.hid.exchange;

import npetzall.hid.api.exchange.HIDExchangeContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nosse on 2015-11-08.
 */
public class HIDExchangeContextImpl implements HIDExchangeContext {

    private final long exchangeStarted;
    private Map<String,String> attributes = new HashMap<>();

    public HIDExchangeContextImpl() {
        exchangeStarted = System.currentTimeMillis();
    }

    public long getExchangeStarted() {
        return exchangeStarted;
    }

    @Override
    public void addAttribute(String key, String value) {
        attributes.put(key,value);
    }

    @Override
    public boolean contains(String key) {
        return attributes.containsKey(key);
    }

    @Override
    public String getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public int size() {
        return attributes.size();
    }

    @Override
    public void clear() {
        attributes.clear();
    }
}
