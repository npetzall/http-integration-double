package npetzall.hid.io.exceptions;

import java.io.IOException;

/**
 * Created by nosse on 2015-11-12.
 */
public class RuntimeIOException extends RuntimeException {
    public RuntimeIOException(IOException ioException) {
        super(ioException);
    }

    public RuntimeIOException(String message, IOException ioExcetion) {
        super(message,ioExcetion);
    }
}
