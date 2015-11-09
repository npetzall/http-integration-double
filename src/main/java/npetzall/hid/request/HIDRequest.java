package npetzall.hid.request;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Arrays;

//TODO Abstract the HttpExchange even more and move to API and make it easier to use in tests
public class HIDRequest {

    private final HttpExchange httpExchange;

    private byte[] request = new byte[0];

    public HIDRequest(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int read;
        try {
            while ((read = httpExchange.getRequestBody().read(buff)) != -1) {
                byteArrayOutputStream.write(buff, 0, read);
            }
        } catch (IOException ioExcetion) {
            throw new RuntimeException("Unable to create request wrapper", ioExcetion);
        }
        request = byteArrayOutputStream.toByteArray();
    }

    public InputStream getRequestBodyInputStream() {
        return new ByteArrayInputStream(request);
    }

    public byte[] getRequestBodyBytes() {
        return Arrays.copyOf(request, request.length);
    }

    public HttpPrincipal getPrincipal() {
        return httpExchange.getPrincipal();
    }

    public Object getAttribute(String s) {
        return httpExchange.getAttribute(s);
    }

    public String getProtocol() {
        return httpExchange.getProtocol();
    }

    public InetSocketAddress getLocalAddress() {
        return httpExchange.getLocalAddress();
    }

    public InetSocketAddress getRemoteAddress() {
        return httpExchange.getRemoteAddress();
    }

    public HttpContext getHttpContext() {
        return httpExchange.getHttpContext();
    }

    public String getRequestMethod() {
        return httpExchange.getRequestMethod();
    }

    public URI getRequestURI() {
        return httpExchange.getRequestURI();
    }

    public Headers getResponseHeaders() {
        return httpExchange.getResponseHeaders();
    }

    public Headers getRequestHeaders() {
        return httpExchange.getRequestHeaders();
    }
}
