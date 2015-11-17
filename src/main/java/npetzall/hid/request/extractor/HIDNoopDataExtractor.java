package npetzall.hid.request.extractor;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.request.*;

public class HIDNoopDataExtractor implements HIDDataExtractor {
    @Override
    public void extract(final HIDRequest request, final HIDExchangeContext exchangeContext) {
        //Default no-op implementation
    }
}
