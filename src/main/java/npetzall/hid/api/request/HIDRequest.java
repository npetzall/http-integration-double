package npetzall.hid.api.request;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface HIDRequest {

    InetSocketAddress getLocalAddress();
    InetSocketAddress getRemoteAddress();

    String getProtocol();

    Map<String, List<String>> getHeaders();

    String getMethod();
    URI getURI();

    Principal getPrincipal();

    byte[] getBodyBytes();
    String getBodyString();
    InputStream getBodyStream();

}
