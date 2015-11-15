package npetzall.hid.response;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.response.HIDResponse;
import npetzall.hid.io.TokenReplaceInputStream;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
        return new TokenReplaceInputStream(inputStream, StandardCharsets.UTF_8, exchangeContext);
    }
}
