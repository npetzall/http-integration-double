package npetzall.hid.response;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.response.HIDResponse;

import java.io.InputStream;

/**
 * Created by nosse on 2015-11-11.
 */
public class HIDTokenReplacer implements HIDResponse {

    private InputStream inputStream;

    public HIDTokenReplacer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public InputStream getInputStream(HIDExchangeContext exchangeContext) {
        return null;
    }
}
