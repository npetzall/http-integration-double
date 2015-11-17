package npetzall.hid.exception;

public class URLException extends  RuntimeException {
    public URLException(String message, Exception e) {
        super(message, e);
    }

    public URLException(String message) {
        super(message);
    }
}
