package npetzall.hid.response;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.response.HIDResponse;
import npetzall.hid.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HIDHalfResponse implements HIDResponse {

    private final HIDResponse hidResponse;

    public HIDHalfResponse(HIDResponse hidResponse) {
        this.hidResponse = hidResponse;
    }

    @Override
    public InputStream getInputStream(HIDExchangeContext exchangeContext) {
        byte[] response = IOUtils.intputStreamToBytes(hidResponse.getInputStream(exchangeContext));
        return new ByteArrayInputStream(response,0, response.length/2);
    }
}
