package npetzall.hid.io;

import npetzall.hid.TestUtil;
import npetzall.hid.exchange.HIDExchangeContextImpl;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nosse on 2015-11-13.
 */
public class TokenReplaceInputStreamTest {

    @Test(timeout = 100L)
    public void canReadWithOutReplace() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("hello".getBytes(StandardCharsets.UTF_8));
        TokenReplaceInputStream tokenReplaceInputStream = new TokenReplaceInputStream(byteArrayInputStream,StandardCharsets.UTF_8, new HIDExchangeContextImpl());
        String result = new String(TestUtil.readInputStreamToByteArray(tokenReplaceInputStream),StandardCharsets.UTF_8);
        assertThat(result).isEqualTo("hello");
    }

    @Test(timeout = 100L)
    public void canReplaceSingleToken() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("he${middle}o".getBytes(StandardCharsets.UTF_8));
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        context.addAttribute("middle","ll");
        TokenReplaceInputStream tokenReplaceInputStream = new TokenReplaceInputStream(byteArrayInputStream, StandardCharsets.UTF_8, context);
        String result = new String(TestUtil.readInputStreamToByteArray(tokenReplaceInputStream),StandardCharsets.UTF_8);
        assertThat(result).isEqualTo("hello");
    }

    @Test(timeout = 100L)
    public void doNothingIfReplacementsAreMissing() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("he${middle}o".getBytes(StandardCharsets.UTF_8));
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        TokenReplaceInputStream tokenReplaceInputStream = new TokenReplaceInputStream(byteArrayInputStream, StandardCharsets.UTF_8, context);
        String result = new String(TestUtil.readInputStreamToByteArray(tokenReplaceInputStream),StandardCharsets.UTF_8);
        assertThat(result).isEqualTo("he${middle}o");
    }

    @Test(timeout = 100L)
    public void doNothingIfReplacementIsMissing() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("he${middle}o".getBytes(StandardCharsets.UTF_8));
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        context.addAttribute("mid-dle","ll");
        TokenReplaceInputStream tokenReplaceInputStream = new TokenReplaceInputStream(byteArrayInputStream, StandardCharsets.UTF_8, context);
        String result = new String(TestUtil.readInputStreamToByteArray(tokenReplaceInputStream),StandardCharsets.UTF_8);
        assertThat(result).isEqualTo("he${middle}o");
    }

    @Test(timeout = 100L)
    public void tokenLongerThanMessage() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("he${middle}o".getBytes(StandardCharsets.UTF_8));
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        context.addAttribute("miasdfasdgafgdfgsdfgsdfgd-dle","ll");
        TokenReplaceInputStream tokenReplaceInputStream = new TokenReplaceInputStream(byteArrayInputStream, StandardCharsets.UTF_8, context);
        String result = new String(TestUtil.readInputStreamToByteArray(tokenReplaceInputStream),StandardCharsets.UTF_8);
        assertThat(result).isEqualTo("he${middle}o");
    }

    @Test(timeout = 100L)
    public void canReplaceTwoTokens() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("${beginning}${middle}${end}".getBytes(StandardCharsets.UTF_8));
        HIDExchangeContextImpl context = new HIDExchangeContextImpl();
        context.addAttribute("beginning","he");
        context.addAttribute("middle","ll");
        context.addAttribute("end","o");
        TokenReplaceInputStream tokenReplaceInputStream = new TokenReplaceInputStream(byteArrayInputStream, StandardCharsets.UTF_8, context);
        String result = new String(TestUtil.readInputStreamToByteArray(tokenReplaceInputStream),StandardCharsets.UTF_8);
        assertThat(result).isEqualTo("hello");
    }
}
