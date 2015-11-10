package npetzall.hid.request;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import npetzall.hid.api.request.HIDRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HIDHttpExchangeWrapper implements HIDRequest{

    private final HttpExchange httpExchange;
    private final byte[] request;
    private final Charset charset = StandardCharsets.UTF_8;

    public HIDHttpExchangeWrapper(HttpExchange httpExchange) {
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

    public InetSocketAddress getLocalAddress() {
        return httpExchange.getLocalAddress();
    }

    public InetSocketAddress getRemoteAddress() {
        return httpExchange.getRemoteAddress();
    }

    public String getProtocol() {
        return httpExchange.getProtocol();
    }

    public Headers getHeaders() {
        return httpExchange.getRequestHeaders();
    }

    public String getMethod() {
        return httpExchange.getRequestMethod();
    }

    public URI getURI() {
        return httpExchange.getRequestURI();
    }

    public HttpPrincipal getPrincipal() {
        return httpExchange.getPrincipal();
    }

    public InputStream getBodyStream() {
        return new ByteArrayInputStream(request);
    }

    public byte[] getBodyBytes() {
        return Arrays.copyOf(request, request.length);
    }

    public String getBodyString() {
        return new String(request, charset);
    }
}
