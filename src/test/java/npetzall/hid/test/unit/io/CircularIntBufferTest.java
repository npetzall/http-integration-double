package npetzall.hid.test.unit.io;

import npetzall.hid.io.CircularIntBuffer;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CircularIntBufferTest {

    @Test
    public void createCircularIntBuffer() {
        CircularIntBuffer cib = new CircularIntBuffer(1024);
        assertThat(cib).isNotNull();
    }

    @Test
    public void canWriteFullBuffer() {
        CircularIntBuffer cib = new CircularIntBuffer(10);
        for(int i = 0; i < 10; i++) {
            cib.write(i);
        }
    }

    @Test
    public void canNotWriteLongerThanBufferIfNoRead() {
        CircularIntBuffer cib = new CircularIntBuffer(10);
        for(int i = 0; i < 10; i++) {
            if (!cib.write(i)) {
                fail("All writes should be true");
            }
        }
        assertThat(cib.write(10)).isFalse();
    }

    @Test
    public void canWriteLongerIfBufferIsRead() {
        CircularIntBuffer cib = new CircularIntBuffer(10);
        for(int i = 0; i < 10; i++) {
            if (!cib.write(i)) {
                fail("All writes should be true");
            }
        }
        assertThat(cib.available()).isEqualTo(10);
        for(int i = 0; i < 10; i++) {
            assertThat(cib.read()).isEqualTo(i);
        }
        assertThat(cib.available()).isEqualTo(0);
        assertThat(cib.write(10)).isTrue();
        assertThat(cib.available()).isEqualTo(1);
        assertThat(cib.read()).isEqualTo(10);
        assertThat(cib.available()).isEqualTo(0);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void cantWriteToClosedBuffer() {
        CircularIntBuffer cib = new CircularIntBuffer(10);
        cib.close();
        assertThat(cib.read()).isEqualTo(-1);
        cib.write(0);
    }

    @Test
    public void quickReaderSlowWriter() throws InterruptedException {
        final CircularIntBuffer cib = new CircularIntBuffer(10);
        Thread reader = new Thread(new Runnable() {
            @Override
            public void run() {
                assertThat(cib.read()).isEqualTo(123);
            }
        });
        reader.start();
        while(reader.getState() != Thread.State.WAITING) {
            Thread.yield();
        }
        reader.interrupt();
        while(reader.getState() == Thread.State.WAITING) {
            Thread.yield();
        }
        while(reader.getState() != Thread.State.WAITING) {
            Thread.yield();
        }
        cib.write(123);
    }
}
