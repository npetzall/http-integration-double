package npetzall.hid.response;

import npetzall.hid.api.exchange.HIDExchangeContext;
import npetzall.hid.api.response.HIDResponse;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by nosse on 2015-11-04.
 */
public class HIDStaticResource implements HIDResponse {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static HIDStaticResource fromString(String text) {
        return new HIDStaticResource(text, DEFAULT_CHARSET);
    }

    public static HIDStaticResource fromString(String text, Charset charset) {
        return new HIDStaticResource(text, charset);
    }

    public static HIDStaticResource fromInputStream(InputStream inputStream) {
        return new HIDStaticResource(inputStream);
    }

    public static HIDStaticResource fromFile(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to create inputstream for file: "+ filePath, e);
        }
        return new HIDStaticResource(inputStream);
    }

    private InputStream inputStream;

    public HIDStaticResource(String text, Charset charset) {
        inputStream = new ByteArrayInputStream(text.getBytes(charset));
    }

    public HIDStaticResource(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getInputStream(HIDExchangeContext hidExchangeContext) {
        return inputStream;
    }

}
