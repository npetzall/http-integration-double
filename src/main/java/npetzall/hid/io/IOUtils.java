package npetzall.hid.io;

import npetzall.hid.io.exceptions.RuntimeIOException;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by nosse on 2015-11-13.
 */
public class IOUtils {

    private static final Logger LOGGER = Logger.getLogger(IOUtils.class.getName());

    private IOUtils() {

    }

    public static byte[] intputStreamToBytes(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        try {
            int read = inputStream.read(buff);
            while (hasRead(read)) {
                byteArrayOutputStream.write(buff, 0, read);
                read = inputStream.read(buff);
            }
        } catch (IOException ioExcetion) {
            throw new RuntimeIOException("Unable to fully read inputStream", ioExcetion);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static boolean hasRead(int numberOfBytes) {
        return numberOfBytes != -1;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOGGER.log(Level.FINE, "Exception during InputStream.close()", e);
            }
        }
    }
}
