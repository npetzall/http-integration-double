package npetzall.hid.response;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.response.HIDResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HIDStaticString implements HIDResponse {

    private final Charset charset;
    private final String response;

    public HIDStaticString(String response) {
        this(response, StandardCharsets.UTF_8);
    }

    public HIDStaticString(String response, Charset charset) {
        this.response = response;
        this.charset = charset;
    }

    @Override
    public InputStream getInputStream(HIDExchangeContext exchangeContext) {
        return new ByteArrayInputStream(response.getBytes(charset));
    }
}
