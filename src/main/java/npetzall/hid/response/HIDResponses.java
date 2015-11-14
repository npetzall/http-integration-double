package npetzall.hid.response;

import java.io.InputStream;

/**
 * Created by nosse on 2015-11-11.
 */
public class HIDResponses {

    private HIDResponses() {
        //Should only be used thru static methods
    }

    public static HIDTokenReplacer tokenReplacer(InputStream inputStream) {
        return new HIDTokenReplacer(inputStream);
    }
}
