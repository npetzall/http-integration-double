package npetzall.hid.test;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpPrincipal;
import npetzall.hid.api.request.HIDRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DummyRequest implements HIDRequest {

    private String protocol;
    private Headers headers = new Headers();
    private String method;
    private URI uri;
    private byte[] request = new byte[0];
    private Charset charset = StandardCharsets.UTF_8;
    private HttpPrincipal httpPrincipal;

    public DummyRequest protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public DummyRequest addHeader(String key, String value) {
        this.headers.add(key, value);
        return this;
    }

    public DummyRequest httpPrincipal(HttpPrincipal httpPrincipal) {
        this.httpPrincipal = httpPrincipal;
        return this;
    }

    public DummyRequest method(String method) {
        this.method = method;
        return this;
    }

    public DummyRequest uri(URI uri) {
        this.uri = uri;
        return this;
    }

    public DummyRequest request(byte[] request) {
        this.request = request;
        return this;
    }

    public DummyRequest charset(Charset charset) {
        this.charset = charset;
        return this;
    }

    /** Interface **/

    @Override
    public InetSocketAddress getLocalAddress() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public Headers getHeaders() {
        return headers;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public URI getURI() {
        return uri;
    }

    @Override
    public HttpPrincipal getPrincipal() {
        return httpPrincipal;
    }

    @Override
    public byte[] getBodyBytes() {
        return Arrays.copyOf(request, request.length);
    }

    @Override
    public String getBodyString() {
        return new String(request, charset);
    }

    @Override
    public InputStream getBodyStream() {
        return new ByteArrayInputStream(request);
    }
}
