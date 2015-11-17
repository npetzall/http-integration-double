package npetzall.hid.io;

import java.io.IOException;
import java.io.OutputStream;

public class SlowOutputStreamWriter {

    private SlowOutputStreamWriter() {
        //Should only be used thru static methods
    }

    public static void slowWrite(final byte[] data, final OutputStream outputStream, final long timeToWriteResponse) throws IOException {
        if (data.length > timeToWriteResponse) {
            slowWriteBytePerUnit(data, outputStream, timeToWriteResponse);
        } else {
            slowWriteDelayPerByte(data, outputStream, timeToWriteResponse);
        }
    }

    private static void slowWriteBytePerUnit(final byte[] data, final OutputStream outputStream, final long timeToWriteResponse) throws IOException {
        int numberOfBytes = data.length / (int)timeToWriteResponse;
        int lengthWritten = 0;
        for(int i = 0; i < timeToWriteResponse; i++) {
            outputStream.write(data, lengthWritten, numberOfBytes);
            lengthWritten += numberOfBytes;
            sleep(1);
        }
        if (lengthWritten != data.length) {
            outputStream.write(data, lengthWritten, data.length-lengthWritten);
        }
    }

    private static void slowWriteDelayPerByte(final byte[] data, final OutputStream outputStream, final long timeToWriteResponse) throws IOException {
        int delayPerByte = (int)timeToWriteResponse / data.length;
        int lengthWritten = 0;
        for(int i = 0; i < data.length-1; i++) {
            outputStream.write(data, lengthWritten, 1);
            lengthWritten++;
            sleep(delayPerByte);
        }
        sleep(timeToWriteResponse%data.length+delayPerByte);
        outputStream.write(data, lengthWritten,1);
    }

    private static void sleep(final long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            //swallow
        }
    }

}
