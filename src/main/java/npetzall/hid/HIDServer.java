package npetzall.hid;

import com.sun.net.httpserver.HttpServer;
import npetzall.hid.exception.URLException;
import npetzall.hid.exception.io.RuntimeIOException;

import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class HIDServer {

    private final HIDConfiguration hidConfiguration;
    private HttpServer httpServer;
    private volatile boolean started;

    private final Set<String> addedContexts = new HashSet<>();

    public HIDServer(final HIDConfiguration hidConfiguration) {
        this.hidConfiguration = hidConfiguration;
    }

    public void start() {
        try {
            httpServer = createServer();
        } catch (IOException ioException) {
            throw new RuntimeIOException("Unable to create HttpServer", ioException);
        }
        setupContexts();
        httpServer.start();
        started = true;
    }

    private HttpServer createServer() throws IOException {
        final HttpServer newHttpServer = HttpServer.create();
        IOException lastException = null;
        for(int i = hidConfiguration.getFirstPort(); i <= hidConfiguration.getLastPort(); i++) {
            try {
                newHttpServer.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), i), 0);
                return newHttpServer;
            } catch (BindException bindException) {
                lastException = bindException;
            } catch (IOException ioException) {
                lastException = ioException;
                break;
            }
        }
        throw new RuntimeIOException("Unable to create HttpServer", lastException);
    }

    private void setupContexts() {
        for(final HIDContext context : hidConfiguration.getContexts()) {
            httpServer.createContext(context.getPath(), context);
            addedContexts.add(context.getPath());
        }
    }

    public boolean isStarted() {
        return started;
    }

    public void stop() {
        if (httpServer != null) {
            httpServer.stop(0);
            httpServer = null;
        }
    }

    public URL createURL(final String path) {
        checkPath(path);
        checkServer();
        try {
            return new URL("http://" + httpServer.getAddress().getHostString() + ":" + httpServer.getAddress().getPort() + path);
        } catch (MalformedURLException e) {
            throw new URLException("Unable to create URL for context: + contextPath",e);
        }
    }

    private static void checkPath(String path) {
        if (path.charAt(0) != '/') {
            throw new URLException("Path must begin with /");
        }
    }

    private void checkServer() {
        if (httpServer == null) {
            throw new URLException("Server doesnt exist");
        }
    }
}
