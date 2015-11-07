package npetzall.hid;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class HID {

    private final HIDConfiguration hidConfiguration;
    private HttpServer httpServer;
    private volatile boolean started;

    private final Set<String> addedContexts = new HashSet<>();

    public HID (final HIDConfiguration hidConfiguration) {
        this.hidConfiguration = hidConfiguration;
    }

    public void start() {
        httpServer = createServer();
        setupContexts();
        httpServer.start();
        started = true;
    }

    private HttpServer createServer() {
        HttpServer newHttpServer;
        Exception lastException = null;
        for(int i = hidConfiguration.getFirstPort(); i <= hidConfiguration.getLastPort(); i++) {
            try {
                newHttpServer = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), i), 0);
                return newHttpServer;
            } catch (BindException bindException) {
                lastException = bindException;
            } catch (IOException ioException) {
                lastException = ioException;
                break;
            }
        }
        throw new RuntimeException("Unable to create HttpServer", lastException);
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
        if (path.charAt(0) != '/') {
            throw new RuntimeException("Path must begin with /");
        }
        if (httpServer == null) {
            throw new RuntimeException("Server doesnt exist");
        }
        try {
            return new URL("http://" + httpServer.getAddress().getHostString() + ":" + httpServer.getAddress().getPort() + path);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to create URL for context: + contextPath",e);
        }
    }
}
