package npetzall.hid;

import npetzall.hid.request.HIDMatchers;
import npetzall.hid.response.HIDStaticResource;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class HIDTest {

    @Test
    public void doubleHIDs() {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration.firstPort(1234).lastPort(1243);
        HID hidOne = new HID(hidConfiguration);
        hidOne.start();

        HID hidTwo = new HID(hidConfiguration);
        hidTwo.start();

        assertThat(hidOne.isStarted()).isTrue();
        assertThat(hidTwo.isStarted()).isTrue();

        URL url = hidOne.createURL("/hello");
        assertThat(url).isNotNull();
        assertThat(url.getHost()).isIn("localhost", "127.0.0.1","127.0.0.2");

        url = hidTwo.createURL("/hello");
        assertThat(url).isNotNull();
        assertThat(url.getHost()).isIn("localhost", "127.0.0.1","127.0.0.2");

        hidOne.stop();
        hidTwo.stop();
    }

    @Test
    public void simpleExchange() throws IOException {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration
                .firstPort(1234)
                .lastPort(1243).addContext(
                HIDContext.newContext()
                        .path("/simple")
                        .addExchange(HIDExchange.newExchange()
                            .matcher(HIDMatchers.alwaysTrue())
                            .response(HIDStaticResource.fromString("hello")
                                    .statusCode(200))
                        )
        );
        HID hid = new HID(hidConfiguration);
        hid.start();

        HttpURLConnection httpClient = (HttpURLConnection) hid.createURL("/simple").openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.getResponseCode();
        byte[] response = TestUtil.readInputStreamToByteArray(httpClient.getInputStream());

        assertThat(new String(response, StandardCharsets.UTF_8)).isEqualTo("hello");

        hid.stop();
    }

    @Test
    public void checkDelay() throws IOException {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration
                .firstPort(1234)
                .lastPort(1243).addContext(
                HIDContext.newContext()
                        .path("/simple")
                        .addExchange(HIDExchange.newExchange()
                                .matcher(HIDMatchers.alwaysTrue())
                                .response(HIDStaticResource.fromString("hello")
                                        .statusCode(200))
                        )
        ).addContext(
                HIDContext.newContext()
                        .path("/simpleDelay")
                        .addExchange(HIDExchange.newExchange()
                                .matcher(HIDMatchers.alwaysTrue())
                                .response(HIDStaticResource.fromString("hello")
                                        .delayBeforeStatusResponse(100)
                                        .statusCode(200)
                                        .delayBeforeBody(100)
                                        .timeToWriteResponseBody(500)
                                        .shouldClose(true)
                                )
                        )
        );
        HID hid = new HID(hidConfiguration);
        hid.start();

        HttpURLConnection httpClient = (HttpURLConnection) hid.createURL("/simple").openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.getResponseCode();
        byte[] responseOne = TestUtil.readInputStreamToByteArray(httpClient.getInputStream());

        assertThat(new String(responseOne, StandardCharsets.UTF_8)).isEqualTo("hello");

        long timestamp = System.currentTimeMillis();
        httpClient = (HttpURLConnection) hid.createURL("/simpleDelay").openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.getResponseCode();
        byte[] responseTwo = TestUtil.readInputStreamToByteArray(httpClient.getInputStream());
        long timestampRequestTwo = System.currentTimeMillis() - timestamp;

        assertThat(new String(responseTwo, StandardCharsets.UTF_8)).isEqualTo("hello");

        assertThat(timestampRequestTwo).isBetween(700L, 800L);

        hid.stop();
    }

    @Test(expected = RuntimeException.class)
    public void incorrectPath() {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration.firstPort(1234).lastPort(1243);
        HID hid = new HID(hidConfiguration);
        hid.start();
        hid.createURL("hello");
        hid.stop();
    }

    @Test(expected = RuntimeException.class)
    public void correctPathHIDnotStarted() {
        HID hid = new HID(HIDConfiguration.newConfiguration().firstPort(1234).lastPort(1243));
        hid.createURL("/hello");
    }

}
