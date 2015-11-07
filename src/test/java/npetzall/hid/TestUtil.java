package npetzall.hid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public static InputStream getResourceStream(String resourcePath) {
        return TestUtil.class.getResourceAsStream(resourcePath);
    }
}
