package npetzall.hid.test.unit.response;

import npetzall.hid.response.HIDResponses;
import npetzall.hid.test.TestUtil;
import npetzall.hid.api.response.HIDResponse;
import npetzall.hid.exchange.HIDExchangeContextImpl;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class HIDTokenReplacerTest {

    @Test
    public void canReplace() throws IOException {
        URL template = TestUtil.getResourceURL("/responses/EchoResponse.xml");
        HIDResponse response = HIDResponses.tokenReplacer(template);
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        context.addAttribute("message", "hello");
        InputStream tokenReplaced = response.getInputStream(context);
        byte[] data = TestUtil.readInputStreamToByteArray(tokenReplaced);
        String dataStr = new String(data, StandardCharsets.UTF_8);
        assertThat(dataStr).isXmlEqualTo("<response><echo>hello</echo></response>");
    }
}
