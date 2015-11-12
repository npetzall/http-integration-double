package npetzall.hid.io;

import npetzall.hid.io.exceptions.BufferClosedException;

import java.util.concurrent.atomic.AtomicInteger;

public class CircularIntBuffer {
    private int readPointer = 0;
    private int writerPointer = 0;
    private AtomicInteger available = new AtomicInteger(0);
    private Object monitor = new Object();
    private volatile boolean closed;

    private final int bufferSize;
    private final int[] buffer;
    private final boolean[] allocated;

    public CircularIntBuffer(int bufferSize) {
        this.bufferSize = bufferSize;
        buffer = new int[bufferSize];
        allocated = new boolean[bufferSize];
    }

    public boolean write(int i) {
        if (closed) {
            throw new BufferClosedException("Can't write to closed buffer");
        }
        if(allocated[writerPointer]) {
           return false;
        }

        buffer[writerPointer] = i;
        allocated[writerPointer] = true;
        synchronized (monitor) {
            monitor.notifyAll();
        }
        available.incrementAndGet();
        writerPointer = (writerPointer + 1) % bufferSize;
        return true;
    }

    public int read() {
        synchronized (monitor) {
            while(continueToWaitForNewData()) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    //Swallow
                }
            }
        }
        if(closed) {
            return -1;
        } else {
            int value = buffer[readPointer];
            allocated[readPointer] = false;
            available.decrementAndGet();
            readPointer = (readPointer + 1) % bufferSize;
            return value;
        }

    }

    private boolean continueToWaitForNewData() {
        return !allocated[readPointer] && !closed;
    }

    public int available() {
        return available.get();
    }

    public void close() {
        closed = true;
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }
}
