package npetzall.hid.test.unit.response;

import npetzall.hid.api.response.HIDResponse;
import npetzall.hid.response.HIDResponses;
import npetzall.hid.test.TestUtil;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class HIDHalfResponseTest {

    @Test
    public void halfResponse() throws IOException {
        HIDResponse halfResponse = HIDResponses.halfResponse(HIDResponses.string("1234", StandardCharsets.UTF_8));
        String result = new String(TestUtil.readInputStreamToByteArray(halfResponse.getInputStream(null)), StandardCharsets.UTF_8);
        assertThat(result).isEqualTo("12");
    }

}
