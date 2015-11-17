package npetzall.hid.io;

import npetzall.hid.api.exchange.HIDExchangeContext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class TokenReplaceInputStream extends InputStream {

    private static final String PRE_FIX = "${";
    private static final String POST_FIX = "}";

    private final byte[] preFixBytes;
    private final byte postFixByte;
    private final InputStream sourceInputStream;
    private final Charset charset;
    private final HIDExchangeContext exchangeContext;
    private final byte[] tokenBuffer;

    private boolean reachedEndOfStream;

    private CircularIntBuffer outBuffer = new CircularIntBuffer(80);

    public TokenReplaceInputStream(InputStream sourceInputStream, Charset charset, HIDExchangeContext hidExchangeContext) {
        this.sourceInputStream = sourceInputStream;
        this.charset = charset;
        this.exchangeContext = hidExchangeContext;
        preFixBytes = PRE_FIX.getBytes(charset);
        postFixByte = POST_FIX.getBytes(charset)[0];
        int longest = 0;
        for(String str : exchangeContext.getAttributeKeys()) {
            longest = longest < str.length() ? str.length(): longest;
        }
        tokenBuffer = new byte[longest+1];
    }

    @Override
    public int read() throws IOException {
        if (reachedEndOfStream) {
            return -1;
        }
        return doRead();
    }

    private int doRead() throws IOException {
        if (outBuffer.available() > 0) {
            return outBuffer.read();
        }
        int read = readSource();
        if (read == -1) {
            reachedEndOfStream = true;
        }
        return read;
    }

    private int readSource() throws IOException {
        int read0 = sourceInputStream.read();
        if (read0 == -1) {
           return -1;
        }
        if (read0 == preFixBytes[0]) {
            checkPrefix();
        } else {
            outBuffer.write(read0);
        }
        return outBuffer.read();
    }

    private void checkPrefix() throws IOException {
        int read1 = sourceInputStream.read();
        if (read1 == preFixBytes[1]) {
            tryToken();
        } else {
            outBuffer.write(preFixBytes[0]);
            outBuffer.write(read1);
        }
    }

    private void tryToken() throws IOException {
        int index = fillBuffer();
        int lastRead = tokenBuffer[index];
        if (lastRead == -1) {
            writePreFix();
            for(int i = 0; i <= index; i++) {
                outBuffer.write(tokenBuffer[i]);
            }
        } else if (lastRead == postFixByte) {
            checkForTokenReplacement(index);
        } else {
            writeTokenbuffer(index);
        }
    }

    private int fillBuffer() throws IOException {
        int read;
        int index = 0;
        for(; index < tokenBuffer.length; index++) {
            read = sourceInputStream.read();
            tokenBuffer[index] = (byte)read;
            if (read == -1 || read == postFixByte) {
                index++;
                break;
            }
        }
        return index-1;
    }

    private void writePreFix() {
        for(byte b: preFixBytes) {
            outBuffer.write(b);
        }
    }

    private void checkForTokenReplacement(int index) {
        String value = exchangeContext.getAttribute(new String(tokenBuffer,0,index, charset));
        if (value != null) {
            writeReplacement(value);
        } else {
            writeTokenbuffer(index);
        }
    }

    private void writeReplacement(String replacement) {
        for(byte b: replacement.getBytes(charset)) {
            outBuffer.write(b);
        }
    }

    private void writeTokenbuffer(int index) {
        writePreFix();
        for(int i = 0; i <= index; i++) {
            outBuffer.write(tokenBuffer[i]);
        }
    }

}
