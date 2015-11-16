package npetzall.hid.response;

import npetzall.hid.TestUtil;
import npetzall.hid.api.response.HIDResponse;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class HIDStaticResourceTest {

    private final String TEST_STRING = "Hello";

    @Test
    public void withStringDefaultEncoding() throws IOException {
        InputStream in = HIDResponses.string(TEST_STRING).getInputStream(null);
        byte[] data = TestUtil.readInputStreamToByteArray(in);
        assertThat(new String(data, HIDResponse.DEFAULT_CHARSET)).isEqualTo(TEST_STRING);
    }

    @Test
    public void withStringASCII() throws IOException {
        InputStream in = HIDResponses.string(TEST_STRING, StandardCharsets.US_ASCII).getInputStream(null);
        byte[] data = TestUtil.readInputStreamToByteArray(in);
        assertThat(new String(data, StandardCharsets.US_ASCII)).isEqualTo(TEST_STRING);
    }

    @Test
    public void withURL() throws IOException {
        InputStream in = HIDResponses.url(getClass().getResource("/responses/EchoResponse.xml"))
                .getInputStream(null);
        byte[] dataFromHIDStaticResource = TestUtil.readInputStreamToByteArray(in);
        byte[] expected = TestUtil.readURLToByteArray(TestUtil.getResourceURL("/responses/EchoResponse.xml"));
        assertThat(dataFromHIDStaticResource).containsExactly(expected);
    }

    @Test
    public void withFile() throws IOException {
        InputStream in = HIDResponses.file("src/test/resources/responses/EchoResponse.xml").getInputStream(null);
        byte[] dataFromHIDStaticResource = TestUtil.readInputStreamToByteArray(in);
        byte[] expected = TestUtil.readInputStreamToByteArray(new FileInputStream("src/test/resources/responses/EchoResponse.xml"));
        assertThat(dataFromHIDStaticResource).containsExactly(expected);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void nonExistingFile() {
        HIDResponses.file("sdfas/sdf");
    }
}
