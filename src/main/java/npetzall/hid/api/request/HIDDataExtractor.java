package npetzall.hid.api.request;

import npetzall.hid.api.exchange.HIDExchangeContext;

public interface HIDDataExtractor {
    void extract(HIDRequest request, HIDExchangeContext exchangeContext);
}
