package npetzall.hid.test.unit.io;

import npetzall.hid.io.SlowOutputStreamWriter;
import npetzall.hid.test.TestUtil;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SlowOutputStreamWriterTest {

    @Test
    public void slowWriteBytePerUnit() throws IOException {
        byte[] dataIn = TestUtil.readURLToByteArray(TestUtil.getResourceURL("/matchers/QNameMatcherReverse.xml"));
        assertThat(dataIn.length).isGreaterThan(200);
        ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
        long timeStamp = System.currentTimeMillis();
        SlowOutputStreamWriter.slowWrite(dataIn, dataOut, 100);
        long duration = System.currentTimeMillis() - timeStamp;
        assertThat(dataOut.toByteArray().length).isEqualTo(dataIn.length);
        assertThat(dataOut.toByteArray()).containsExactly(dataIn);
        assertThat(duration).isBetween(100L,150L);
    }

    @Test
    public void slowWriteDelayPerByte() throws IOException {
        byte[] dataIn = TestUtil.readURLToByteArray(TestUtil.getResourceURL("/matchers/QNameMatcherReverse.xml"));
        assertThat(dataIn.length).isLessThan(520);
        ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
        long timeStamp = System.currentTimeMillis();
        SlowOutputStreamWriter.slowWrite(dataIn, dataOut, 800);
        long duration = System.currentTimeMillis() - timeStamp;
        assertThat(dataOut.toByteArray().length).isEqualTo(dataIn.length);
        assertThat(dataOut.toByteArray()).containsExactly(dataIn);
        assertThat(duration).isBetween(800L,900L);
    }

}
