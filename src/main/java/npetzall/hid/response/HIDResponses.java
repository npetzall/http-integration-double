package npetzall.hid.response;

import npetzall.hid.api.response.HIDResponse;
import npetzall.hid.exception.io.RuntimeIOException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by nosse on 2015-11-11.
 */
public class HIDResponses {

    private HIDResponses() {
        //Should only be used thru static methods
    }

    public static HIDResponse tokenReplacer(URL templateURL) {
        return new HIDTokenReplacer(templateURL);
    }

    public static HIDResponse string(String string) {
        return new HIDStaticString(string);
    }

    public static HIDResponse string(String string, Charset charset) {
        return new HIDStaticString(string, charset);
    }

    public static HIDResponse url(URL resource) {
        return new HIDStaticURL(resource);
    }

    public static HIDResponse file(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeIOException("Failed to create URL from File from String: "+ filePath + " doesn't exist");
            }
            return new HIDStaticURL(file.toURI().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeIOException("Failed to create URL from File from String: "+ filePath,e);
        }
    }
}
