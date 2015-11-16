package npetzall.hid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by nosse on 2015-11-05.
 */
public class TestUtil {

    public static byte[] readInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int read;
        while((read = inputStream.read(buff)) != -1) {
            out.write(buff,0,read);
        }
        return out.toByteArray();
    }

    public static byte[] readURLToByteArray(URL url) throws IOException {
        return readInputStreamToByteArray(url.openStream());
    }

    public static URL getResourceURL(String resourcePath) {
        return TestUtil.class.getResource(resourcePath);
    }

    public static InputStream getResourceStream(String resourcePath) {
        return TestUtil.class.getResourceAsStream(resourcePath);
    }
}
