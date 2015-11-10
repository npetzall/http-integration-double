package npetzall.hid;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import npetzall.hid.exchange.HIDExchangeContextImpl;
import npetzall.hid.request.HIDHttpExchangeWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nosse on 2015-11-04.
 */
public class HIDContext implements HttpHandler {
    public static HIDContext newContext() {
        return new HIDContext();
    }

    private String path;
    private final List<HIDExchange> hidExchanges = new ArrayList<>();

    public HIDContext setPath(final String path) {
        this.path = path;
        return this;
    }

    public HIDContext addExchange(final HIDExchange hidExchange) {
        hidExchanges.add(hidExchange);
        return this;
    }

    public String getPath() {
        return path;
    }

    @Override
    public void handle(final HttpExchange httpExchange) throws IOException {
        final HIDExchangeContextImpl exchangeContext = new HIDExchangeContextImpl();
        final HIDHttpExchangeWrapper hidRequest = new HIDHttpExchangeWrapper(httpExchange);
        for(final HIDExchange exchange: hidExchanges) {
            if (exchange.matches(hidRequest)) {
                exchange.extractData(hidRequest, exchangeContext);
                exchange.sendResponse(exchangeContext, httpExchange);
            }
        }
    }
}
