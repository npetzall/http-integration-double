package npetzall.hid.response;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.response.HIDResponse;

import java.io.InputStream;

public class HIDHalfResponse implements HIDResponse {

    private final HIDResponse hidResponse;

    public HIDHalfResponse (HIDResponse hidResponse) {
        this.hidResponse = hidResponse;
    }

    @Override
    public InputStream getInputStream(HIDExchangeContext exchangeContext) {
        return null;
    }
}
