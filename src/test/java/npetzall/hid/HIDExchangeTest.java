package npetzall.hid;

import npetzall.hid.request.QNameMatcher;
import npetzall.hid.response.HIDStaticResource;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nosse on 2015-11-04.
 */
public class HIDExchangeTest {

    @Test
    public void createExchange() {
        HIDExchange hidExchange = HIDExchange.newExchange();
    }

    @Test
    public void addMatcher() {
        HIDExchange hidExchange = HIDExchange.newExchange()
                .matcher(new QNameMatcher("http://npetzall/hid","reverse", QNameMatcher.ForType.ELEMENT));
    }

    @Test
    public void addResponse() throws IOException {
        HIDExchange hidExchange = HIDExchange.newExchange()
                .response(HIDStaticResource.fromString("Text"));
        String text = new String(TestUtil.readInputStreamToByteArray(hidExchange.getResponse().getInputStream()), StandardCharsets.UTF_8);
        assertThat(text).isEqualTo("Text");
    }

}
