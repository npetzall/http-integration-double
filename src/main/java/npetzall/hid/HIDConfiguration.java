package npetzall.hid;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by nosse on 2015-11-04.
 */
public class HIDConfiguration {

    public static HIDConfiguration newConfiguration() {
        return new HIDConfiguration();
    }

    private int firstPort;
    private int lastPort;

    private final Set<HIDContext> hidContexts = new LinkedHashSet<>();

    public HIDConfiguration firstPort(final int firstPort) {
        this.firstPort = firstPort;
        return this;
    }

    public int getFirstPort() {
        return firstPort;
    }

    public HIDConfiguration lastPort(final int lastPort) {
        this.lastPort = lastPort;
        return this;
    }

    public int getLastPort() {
        return lastPort;
    }

    public HIDConfiguration addContext(final HIDContext hidContext) {
        hidContexts.add(hidContext);
        return this;
    }

    public Set<HIDContext> getContexts() {
        return hidContexts;
    }
}
