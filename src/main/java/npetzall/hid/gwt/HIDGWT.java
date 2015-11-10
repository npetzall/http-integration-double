package npetzall.hid.gwt;

import npetzall.hid.HIDServer;
import npetzall.hid.HIDConfiguration;
import npetzall.hid.HIDContext;
import npetzall.hid.HIDExchange;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nosse on 2015-11-08.
 */
public class HIDGWT {

    public static HIDGWT hid(HIDGWTContext...contexts) {
        return new HIDGWT(contexts);
    }

    public static HIDGWTContext givenContext(String contextPath) {
        return new HIDGWTContext(contextPath);
    }

    private Map<String, HIDContext> contexts = new HashMap<>();
    private int firstPort = 1234;
    private int lastPort = 1342;

    public HIDGWT(HIDGWTContext...contexts) {
        HIDContext existingContext;
        for(HIDGWTContext context : contexts) {
            existingContext = this.contexts.get(context.contextPath);
            if (existingContext == null) {
                this.contexts.put(context.contextPath, toHIDContext(context));
            } else {
                existingContext.addExchange(toHIDExchange(context));
            }
        }
    }

    private HIDExchange toHIDExchange(HIDGWTContext context) {
        return HIDExchange
                .newExchange()
                .setMatcher(context.hidMatcher)
                .setResponse(context.hidResponse)
                .setDelayBeforeStatusResponse(context.delayStatusFor)
                .setStatusCode(context.respondWithStatusCode)
                .setDelayBeforeBody(context.delayResponseBodyFor)
                .setTimeToWriteResponseBody(context.writeBodyFor)
                .setShouldClose(context.shouldClose);

    }

    private HIDContext toHIDContext(HIDGWTContext context) {
        return HIDContext.newContext().setPath(context.contextPath).addExchange(toHIDExchange(context));
    }

    public HIDGWT firstPort(int firstPort) {
        this.firstPort = firstPort;
        return this;
    }

    public HIDGWT lastPort(int lastPort) {
        this.lastPort = lastPort;
        return this;
    }

    public HIDServer start() {
        HIDConfiguration hidConfiguration = HIDConfiguration.newConfiguration();
        hidConfiguration.setFirstPort(firstPort).setLastPort(lastPort);
        for(HIDContext context: contexts.values()) {
            hidConfiguration.addContext(context);
        }
        HIDServer hidServer = new HIDServer(hidConfiguration);
        hidServer.start();
        return hidServer;
    }

}
