package npetzall.hid.api.request;

import npetzall.hid.api.exchange.HIDExchangeContext;

/**
 * Created by nosse on 2015-11-07.
 */
public interface HIDDataExtractor {
    void extractData(HIDExchangeContext hidExchangeContext);
}
