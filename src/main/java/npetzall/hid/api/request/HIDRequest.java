package npetzall.hid.api.request;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpPrincipal;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public interface HIDRequest {

    InetSocketAddress getLocalAddress();
    InetSocketAddress getRemoteAddress();

    String getProtocol();

    Headers getHeaders();

    String getMethod();
    URI getURI();

    HttpPrincipal getPrincipal();

    byte[] getBodyBytes();
    String getBodyString();
    InputStream getBodyStream();

}
