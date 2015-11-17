package npetzall.hid.exchange;

import npetzall.hid.api.exchange.HIDExchangeContext;

import java.util.*;

public class HIDExchangeContextImpl implements HIDExchangeContext {

    private final long exchangeStarted;
    private final Map<String,String> attributes = new HashMap<>();

    public HIDExchangeContextImpl() {
        exchangeStarted = System.currentTimeMillis();
    }

    public long getExchangeStarted() {
        return exchangeStarted;
    }

    @Override
    public void addAttribute(final String key, final String value) {
        attributes.put(key,value);
    }

    @Override
    public boolean contains(final String key) {
        return attributes.containsKey(key);
    }

    @Override
    public String getAttribute(final String key) {
        return attributes.get(key);
    }

    @Override
    public Set<String> getAttributeKeys() {
        return attributes.keySet();
    }

    @Override
    public List<String> getAttributeValues() {
        return new ArrayList<>(attributes.values());
    }

    @Override
    public int size() {
        return attributes.size();
    }

}
