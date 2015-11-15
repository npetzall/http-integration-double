package npetzall.hid.gwt;

import npetzall.hid.HIDServer;
import npetzall.hid.TestUtil;
import npetzall.hid.request.HIDMatchers;
import npetzall.hid.request.matchers.HttpMethodMatcher;
import npetzall.hid.response.HIDStaticResource;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import static npetzall.hid.gwt.HIDGWT.givenContext;
import static npetzall.hid.gwt.HIDGWT.hid;
import static org.assertj.core.api.Assertions.assertThat;

public class HIDGWTTest {

    @Test
    public void simple() throws IOException {
        HIDServer hidServer = hid(
                givenContext("/simpleOne")
                        .whenRequestMatches(HIDMatchers.alwaysTrue())
                        .thenRespondWith(HIDStaticResource.fromString("hello"))
                        .addHeader("custom", "first")
                        .addHeader("custom", "second")
                        .delayStatusFor(0)
                        .respondWithStatusCode(200)
                        .delayResponseBodyFor(0)
                        .writeBodyFor(0)
                        .shouldClose(true),
                givenContext("/simpleTwo")
                        .whenRequestMatches(new HttpMethodMatcher("GET"))
                        .thenRespondWith(HIDStaticResource.fromString("bye"))
                        .delayStatusFor(100)
                        .respondWithStatusCode(200)
                        .delayResponseBodyFor(100)
                        .writeBodyFor(500),
                givenContext("/simpleTwo")
                        .whenRequestMatches(new HttpMethodMatcher("POST"))
                        .thenRespondWith(HIDStaticResource.fromString("eyb"))
                        .delayStatusFor(0)
                        .respondWithStatusCode(200)
                        .delayResponseBodyFor(0)
                        .writeBodyFor(0)
                        .shouldClose(true)
        )
                .firstPort(1233)
                .lastPort(1244)
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


}
