package npetzall.hid.request;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.request.HIDDataExtractor;

public class HIDNoopDataExtractor implements HIDDataExtractor {
    @Override
    public void extractData(HIDExchangeContext hidExchangeContext) {}
}
