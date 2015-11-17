package npetzall.hid;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class HIDConfiguration {

    private int firstPort;
    private int lastPort;

    private final Set<HIDContext> hidContexts = new LinkedHashSet<>();

    public static HIDConfiguration newConfiguration() {
        return new HIDConfiguration();
    }

    public HIDConfiguration setFirstPort(final int firstPort) {
        this.firstPort = firstPort;
        return this;
    }

    public int getFirstPort() {
        return firstPort;
    }

    public HIDConfiguration setLastPort(final int lastPort) {
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
    public HIDConfiguration addContexts(Collection<HIDContext> contexts) {
        hidContexts.addAll(contexts);
        return this;
    }

    public Set<HIDContext> getContexts() {
        return hidContexts;
    }


}
