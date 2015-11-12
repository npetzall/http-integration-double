package npetzall.hid.request;

import com.sun.net.httpserver.HttpExchange;
import npetzall.hid.api.request.HIDRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Override
    public InetSocketAddress getLocalAddress() {
        return httpExchange.getLocalAddress();
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return httpExchange.getRemoteAddress();
    }

    @Override
    public String getProtocol() {
        return httpExchange.getProtocol();
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return httpExchange.getRequestHeaders();
    }

    @Override
    public String getMethod() {
        return httpExchange.getRequestMethod();
    }

    @Override
    public URI getURI() {
        return httpExchange.getRequestURI();
    }

    @Override
    public Principal getPrincipal() {
        return httpExchange.getPrincipal();
    }

    @Override
    public InputStream getBodyStream() {
        return new ByteArrayInputStream(request);
    }

    @Override
    public byte[] getBodyBytes() {
        return Arrays.copyOf(request, request.length);
    }

    @Override
    public String getBodyString() {
        return new String(request, charset);
    }
}
