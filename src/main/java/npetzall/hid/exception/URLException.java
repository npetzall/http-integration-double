package npetzall.hid.exception;

/**
 * Created by nosse on 2015-11-14.
 */
public class URLException extends  RuntimeException {
    public URLException(String message, Exception e) {
        super(message, e);
    }

    public URLException(String message) {
        super(message);
    }
}
