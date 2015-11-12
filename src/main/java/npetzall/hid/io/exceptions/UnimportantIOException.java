package npetzall.hid.io.exceptions;

import java.io.IOException;

/**
 * Created by nosse on 2015-11-12.
 */
public class UnimportantIOException extends RuntimeException {
    public UnimportantIOException(IOException ioException) {
        super(ioException);
    }
}
