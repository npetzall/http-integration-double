package npetzall.hid;

import npetzall.hid.request.HIDMatchers;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nosse on 2015-11-04.
 */
public class HIDContextTest {

    @Test
    public void createHIDContext() {
        HIDContext hidContext = HIDContext.newContext();
    }

    @Test
    public void addPath() {
        HIDContext hidContext = HIDContext.newContext()
                .path("/test");
        assertThat(hidContext.getPath()).isEqualTo("/test");
    }

    @Test
    public void addExchange() {
        HIDContext hidContext = HIDContext.newContext().path("/test")
                .addExchange(HIDExchange.newExchange().matcher(HIDMatchers.elementQNameMatcher("http://npetzall/hid", "reverse")));
    }
}
