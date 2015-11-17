package npetzall.hid.response;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.response.HIDResponse;
import npetzall.hid.exception.io.RuntimeIOException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HIDStaticURL implements HIDResponse {

    private final URL resourceUrl;

    public HIDStaticURL(URL resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    @Override
    public InputStream getInputStream(HIDExchangeContext exchangeContext) {
        try {
            return resourceUrl.openStream();
        } catch (IOException e) {
            throw new RuntimeIOException("Unable to create inputStream from URL: " + resourceUrl.toExternalForm() , e);
        }
    }
}
