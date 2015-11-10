package npetzall.hid.exchange.extractor;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.request.*;

public class HIDNoopDataExtractor implements HIDDataExtractor {
    @Override
    public void extract(final HIDRequest request, final HIDExchangeContext exchangeContext) {

    }
}
