package npetzall.hid.response;

import java.io.InputStream;

/**
 * Created by nosse on 2015-11-04.
 */
public interface HIDResponse {

    long getDelayBeforeStatusResponse();
    int getStatusCode();
    long getDelayBeforeBody();
    InputStream getInputStream();
    long getTimeToWriteResponseBody();
    boolean getShouldClose();

}
