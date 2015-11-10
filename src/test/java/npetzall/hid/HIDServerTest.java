package npetzall.hid;

import npetzall.hid.request.HIDMatchers;
import npetzall.hid.response.HIDStaticResource;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class HIDServerTest {

    @Test
    public void doubleHIDs() {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration.setFirstPort(1234).setLastPort(1243);
        HIDServer hidServerOne = new HIDServer(hidConfiguration);
        hidServerOne.start();

        HIDServer hidServerTwo = new HIDServer(hidConfiguration);
        hidServerTwo.start();

        assertThat(hidServerOne.isStarted()).isTrue();
        assertThat(hidServerTwo.isStarted()).isTrue();

        URL url = hidServerOne.createURL("/hello");
        assertThat(url).isNotNull();
        assertThat(url.getHost()).isIn("localhost", "127.0.0.1","127.0.0.2");

        url = hidServerTwo.createURL("/hello");
        assertThat(url).isNotNull();
        assertThat(url.getHost()).isIn("localhost", "127.0.0.1","127.0.0.2");

        hidServerOne.stop();
        hidServerTwo.stop();
    }

    @Test
    public void simpleExchange() throws IOException {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration
                .setFirstPort(1234)
                .setLastPort(1243).addContext(
                HIDContext.newContext()
                        .setPath("/simple")
                        .addExchange(HIDExchange.newExchange()
                            .setMatcher(HIDMatchers.alwaysTrue())
                            .setResponse(HIDStaticResource.fromString("hello"))
                                    .setStatusCode(200))

        );
        HIDServer hidServer = new HIDServer(hidConfiguration);
        hidServer.start();

        HttpURLConnection httpClient = (HttpURLConnection) hidServer.createURL("/simple").openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.getResponseCode();
        byte[] response = TestUtil.readInputStreamToByteArray(httpClient.getInputStream());

        assertThat(new String(response, StandardCharsets.UTF_8)).isEqualTo("hello");

        hidServer.stop();
    }

    @Test
    public void checkDelay() throws IOException {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration
                .setFirstPort(1234)
                .setLastPort(1243).addContext(
                HIDContext.newContext()
                        .setPath("/simple")
                        .addExchange(HIDExchange.newExchange()
                                .setMatcher(HIDMatchers.alwaysTrue())
                                .setResponse(HIDStaticResource.fromString("hello"))
                                .setStatusCode(200))

        ).addContext(
                HIDContext.newContext()
                        .setPath("/simpleDelay")
                        .addExchange(HIDExchange.newExchange()
                                .setMatcher(HIDMatchers.alwaysTrue())
                                .setResponse(HIDStaticResource.fromString("hello"))
                                .setDelayBeforeStatusResponse(100)
                                .setStatusCode(200)
                                .setDelayBeforeBody(100)
                                .setTimeToWriteResponseBody(500)
                                .setShouldClose(true)
                                )

        );
        HIDServer hidServer = new HIDServer(hidConfiguration);
        hidServer.start();

        HttpURLConnection httpClient = (HttpURLConnection) hidServer.createURL("/simple").openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.getResponseCode();
        byte[] responseOne = TestUtil.readInputStreamToByteArray(httpClient.getInputStream());

        assertThat(new String(responseOne, StandardCharsets.UTF_8)).isEqualTo("hello");

        long timestamp = System.currentTimeMillis();
        httpClient = (HttpURLConnection) hidServer.createURL("/simpleDelay").openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.getResponseCode();
        byte[] responseTwo = TestUtil.readInputStreamToByteArray(httpClient.getInputStream());
        long timestampRequestTwo = System.currentTimeMillis() - timestamp;

        assertThat(new String(responseTwo, StandardCharsets.UTF_8)).isEqualTo("hello");

        assertThat(timestampRequestTwo).isBetween(700L, 800L);

        hidServer.stop();
    }

    @Test(expected = RuntimeException.class)
    public void incorrectPath() {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration.setFirstPort(1234).setLastPort(1243);
        HIDServer hidServer = new HIDServer(hidConfiguration);
        hidServer.start();
        hidServer.createURL("hello");
        hidServer.stop();
    }

    @Test(expected = RuntimeException.class)
    public void correctPathHIDnotStarted() {
        HIDServer hidServer = new HIDServer(HIDConfiguration.newConfiguration().setFirstPort(1234).setLastPort(1243));
        hidServer.createURL("/hello");
    }

}
