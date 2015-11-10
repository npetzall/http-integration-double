package npetzall.hid.api.response;

import npetzall.hid.api.exchange.HIDExchangeContext;

import java.io.InputStream;

public interface HIDResponse {
    InputStream getInputStream(HIDExchangeContext exchangeContext);
}
