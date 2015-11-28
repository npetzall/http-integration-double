package npetzall.hid.test.acceptance;

import npetzall.hid.*;
import npetzall.hid.request.matchers.HIDMatchers;
import npetzall.hid.request.matchers.HttpMethodMatcher;
import npetzall.hid.response.HIDResponses;
import npetzall.hid.test.TestUtil;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static npetzall.hid.request.extractor.HIDExtractors.xPathExtractor;
import static npetzall.hid.gwt.HIDGWT.givenContext;
import static npetzall.hid.gwt.HIDGWT.hid;
import static npetzall.hid.request.matchers.HIDMatchers.*;
import static npetzall.hid.response.HIDResponses.tokenReplacer;
import static org.assertj.core.api.Assertions.assertThat;

public class HIDServerTest {

    private static int FIRST_PORT = 1234;
    private static int LAST_PORT = 1334;

    @Test
    public void doubleHIDs() {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration.setFirstPort(FIRST_PORT).setLastPort(LAST_PORT);
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
    public void checkDelay() throws IOException {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration
                .setFirstPort(FIRST_PORT)
                .setLastPort(LAST_PORT).addContext(
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
                        .thenExtract(xPathExtractor().xpath("/request/message","message"))
                        .thenRespondWith(tokenReplacer(TestUtil.getResourceURL("/responses/EchoResponse.xml")))
                        .delayStatusFor(0)
                        .respondWithStatusCode(200)
                        .delayResponseBodyFor(0)
                        .writeBodyFor(0)
                        .shouldClose(true)
        )
                .firstPort(FIRST_PORT)
                .lastPort(LAST_PORT)
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
                .firstPort(FIRST_PORT)
                .lastPort(LAST_PORT)
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

    @Test
    public void simple() throws IOException {
        HIDServer hidServer = hid(
                givenContext("/simpleOne")
                        .whenRequestMatches(HIDMatchers.alwaysTrue())
                        .thenRespondWith(HIDResponses.string("hello"))
                        .addHeader("custom", "first")
                        .addHeader("custom", "second")
                        .delayStatusFor(0)
                        .respondWithStatusCode(200)
                        .delayResponseBodyFor(0)
                        .writeBodyFor(0)
                        .shouldClose(true),
                givenContext("/simpleTwo")
                        .whenRequestMatches(new HttpMethodMatcher("GET"))
                        .thenRespondWith(HIDResponses.string("bye"))
                        .delayStatusFor(100)
                        .respondWithStatusCode(200)
                        .delayResponseBodyFor(100)
                        .writeBodyFor(500),
                givenContext("/simpleTwo")
                        .whenRequestMatches(new HttpMethodMatcher("POST"))
                        .thenRespondWith(HIDResponses.string("eyb"))
                        .delayStatusFor(0)
                        .respondWithStatusCode(200)
                        .delayResponseBodyFor(0)
                        .writeBodyFor(0)
                        .shouldClose(true)
        )
                .firstPort(FIRST_PORT)
                .lastPort(LAST_PORT)
                .start();

        HttpURLConnection httpClient = (HttpURLConnection) hidServer.createURL("/simpleOne").openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.getResponseCode();
        byte[] responseOne = TestUtil.readInputStreamToByteArray(httpClient.getInputStream());

        assertThat(new String(responseOne, StandardCharsets.UTF_8)).isEqualTo("hello");

        long timestamp = System.currentTimeMillis();
        httpClient = (HttpURLConnection) hidServer.createURL("/simpleTwo").openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.getResponseCode();
        byte[] responseTwo = TestUtil.readInputStreamToByteArray(httpClient.getInputStream());
        long timestampRequestTwo = System.currentTimeMillis() - timestamp;

        assertThat(new String(responseTwo, StandardCharsets.UTF_8)).isEqualTo("bye");

        assertThat(timestampRequestTwo).isBetween(700L, 800L);

        hidServer.stop();
    }


    @Test(expectedExceptions = RuntimeException.class)
    public void incorrectPath() {
        HIDConfiguration hidConfiguration = new HIDConfiguration();
        hidConfiguration.setFirstPort(FIRST_PORT).setLastPort(LAST_PORT);
        HIDServer hidServer = new HIDServer(hidConfiguration);
        hidServer.start();
        hidServer.createURL("hello");
        hidServer.stop();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void correctPathHIDnotStarted() {
        HIDServer hidServer = new HIDServer(HIDConfiguration.newConfiguration().setFirstPort(FIRST_PORT).setLastPort(LAST_PORT));
        hidServer.createURL("/hello");
    }

}
