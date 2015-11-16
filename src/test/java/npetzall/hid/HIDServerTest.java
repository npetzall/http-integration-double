package npetzall.hid;

import npetzall.hid.request.HIDMatchers;
import npetzall.hid.response.HIDResponses;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static npetzall.hid.exchange.extractor.HIDExtractors.xPathExtractor;
import static npetzall.hid.gwt.HIDGWT.givenContext;
import static npetzall.hid.gwt.HIDGWT.hid;
import static npetzall.hid.request.HIDMatchers.*;
import static npetzall.hid.response.HIDResponses.tokenReplacer;
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
                            .setResponse(HIDResponses.string("hello"))
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
                                .setResponse(HIDResponses.string("hello"))
                                .setStatusCode(200))

        ).addContext(
                HIDContext.newContext()
                        .setPath("/simpleDelay")
                        .addExchange(HIDExchange.newExchange()
                                .setMatcher(HIDMatchers.alwaysTrue())
                                .setResponse(HIDResponses.string("hello"))
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

    @Test
    public void xmlExtractAndTokenReplace() throws IOException {
        HIDServer hidServer = hid(
                givenContext("/echo")
                        .whenRequestMatches(and(httpMethodMatcher("POST"), elementQNameMatcher("","message")))
                        .thenExtract(xPathExtractor("/request/message","message"))
                        .thenRespondWith(tokenReplacer(TestUtil.getResourceURL("/responses/EchoResponse.xml")))
                        .delayStatusFor(0)
                        .respondWithStatusCode(200)
                        .delayResponseBodyFor(0)
                        .writeBodyFor(0)
                        .shouldClose(true)
        )
                .firstPort(1234)
                .lastPort(1255)
                .start();

        HttpURLConnection httpClient = (HttpURLConnection) hidServer.createURL("/echo").openConnection();
        httpClient.setRequestMethod("POST");
        httpClient.setDoOutput(true);
        httpClient.setDoInput(true);
        httpClient.getOutputStream().write("<request><message>hello</message></request>".getBytes(StandardCharsets.UTF_8));
        httpClient.getOutputStream().close();
        httpClient.getResponseCode();
        String response = new String(TestUtil.readInputStreamToByteArray(httpClient.getInputStream()), StandardCharsets.UTF_8);
        assertThat(response).isXmlEqualTo("<response><echo>hello</echo></response>");

        httpClient = (HttpURLConnection) hidServer.createURL("/echo").openConnection();
        httpClient.setRequestMethod("POST");
        httpClient.setDoOutput(true);
        httpClient.setDoInput(true);
        httpClient.getOutputStream().write("<request><message>yhello</message></request>".getBytes(StandardCharsets.UTF_8));
        httpClient.getOutputStream().close();
        httpClient.getResponseCode();
        response = new String(TestUtil.readInputStreamToByteArray(httpClient.getInputStream()), StandardCharsets.UTF_8);
        assertThat(response).isXmlEqualTo("<response><echo>yhello</echo></response>");
    }

    @Test
    public void defaultTokenAndTokenReplace() throws IOException {
        HIDServer hidServer = hid(
                givenContext("/echo")
                        .whenRequestMatches(and(httpMethodMatcher("POST"), elementQNameMatcher("","message")))
                        .addTokenReplacement("message","hello")
                        .thenRespondWith(tokenReplacer(TestUtil.getResourceURL("/responses/EchoResponse.xml")))
                        .delayStatusFor(0)
                        .respondWithStatusCode(200)
                        .delayResponseBodyFor(0)
                        .writeBodyFor(0)
                        .shouldClose(true)
        )
                .firstPort(1234)
                .lastPort(1255)
                .start();

        HttpURLConnection httpClient = (HttpURLConnection) hidServer.createURL("/echo").openConnection();
        httpClient.setRequestMethod("POST");
        httpClient.setDoOutput(true);
        httpClient.setDoInput(true);
        httpClient.getOutputStream().write("<request><message>hello</message></request>".getBytes(StandardCharsets.UTF_8));
        httpClient.getOutputStream().close();
        httpClient.getResponseCode();
        String response = new String(TestUtil.readInputStreamToByteArray(httpClient.getInputStream()), StandardCharsets.UTF_8);
        assertThat(response).isXmlEqualTo("<response><echo>hello</echo></response>");

    }


    @Test(expectedExceptions = RuntimeException.class)
    public void incorrectPath() {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration.setFirstPort(1234).setLastPort(1243);
        HIDServer hidServer = new HIDServer(hidConfiguration);
        hidServer.start();
        hidServer.createURL("hello");
        hidServer.stop();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void correctPathHIDnotStarted() {
        HIDServer hidServer = new HIDServer(HIDConfiguration.newConfiguration().setFirstPort(1234).setLastPort(1243));
        hidServer.createURL("/hello");
    }

}
