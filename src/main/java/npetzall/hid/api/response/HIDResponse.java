package npetzall.hid.api.response;

import npetzall.hid.api.exchange.HIDExchangeContext;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface HIDResponse {

    public static Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    InputStream getInputStream(HIDExchangeContext exchangeContext);
}
