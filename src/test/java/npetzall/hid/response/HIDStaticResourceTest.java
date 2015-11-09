package npetzall.hid.response;

import npetzall.hid.TestUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nosse on 2015-11-04.
 */
public class HIDStaticResourceTest {

    private final String TEST_STRING = "Hello";

    @Test
    public void withStringDefaultEncoding() throws IOException {
        InputStream in = HIDStaticResource.fromString(TEST_STRING).getInputStream(null);
        byte[] data = TestUtil.readInputStreamToByteArray(in);
        assertThat(new String(data, HIDStaticResource.DEFAULT_CHARSET)).isEqualTo(TEST_STRING);
    }

    @Test
    public void withStringASCII() throws IOException {
        InputStream in = HIDStaticResource.fromString(TEST_STRING, StandardCharsets.US_ASCII).getInputStream(null);
        byte[] data = TestUtil.readInputStreamToByteArray(in);
        assertThat(new String(data, StandardCharsets.US_ASCII)).isEqualTo(TEST_STRING);
    }

    @Test
    public void withInputStream() throws IOException {
        InputStream in = HIDStaticResource.fromInputStream(getClass().getResourceAsStream("/responses/SimpleResponse.xml"))
                .getInputStream(null);
        byte[] dataFromHIDStaticResource = TestUtil.readInputStreamToByteArray(in);
        byte[] expected = TestUtil.readInputStreamToByteArray(TestUtil.getResourceStream("/responses/SimpleResponse.xml"));
        assertThat(dataFromHIDStaticResource).containsExactly(expected);
    }

    @Test
    public void withFile() throws IOException {
        InputStream in = HIDStaticResource.fromFile("src/test/resources/responses/SimpleResponse.xml").getInputStream(null);
        byte[] dataFromHIDStaticResource = TestUtil.readInputStreamToByteArray(in);
        byte[] expected = TestUtil.readInputStreamToByteArray(new FileInputStream("src/test/resources/responses/SimpleResponse.xml"));
        assertThat(dataFromHIDStaticResource).containsExactly(expected);
    }

    @Test(expected = RuntimeException.class)
    public void nonExistingFile() {
        HIDStaticResource.fromFile("sdfas/sdf");
    }
}
