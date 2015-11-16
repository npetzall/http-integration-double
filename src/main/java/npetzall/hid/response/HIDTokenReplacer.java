package npetzall.hid.response;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.response.HIDResponse;
import npetzall.hid.exception.io.RuntimeIOException;
import npetzall.hid.io.TokenReplaceInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by nosse on 2015-11-11.
 */
public class HIDTokenReplacer implements HIDResponse {

    private URL template;

    public HIDTokenReplacer(URL template) {
        this.template = template;
    }

    @Override
    public InputStream getInputStream(HIDExchangeContext exchangeContext) {
        try {
            return new TokenReplaceInputStream(template.openStream(), StandardCharsets.UTF_8, exchangeContext);
        } catch (IOException e) {
            throw new RuntimeIOException("Unable to create inputStream from URL: " + template.toExternalForm() , e);
        }
    }
}
