package npetzall.hid.response;

/**
 * Created by nosse on 2015-11-05.
 */
public abstract class HIDBaseResponse implements HIDResponse {

    protected long delayBeforeStatusResponse = 0;
    protected int statusCode = 200;
    protected long delayBeforeBody = 0;
    protected long timeToWriteResponseBody = 0;
    protected boolean shouldClose = true;

    public abstract <T extends HIDBaseResponse> T getThis();

    public <T extends HIDBaseResponse> T delayBeforeStatusResponse(long delayBeforeStatusResponse) {
        this.delayBeforeStatusResponse = delayBeforeStatusResponse;
        return getThis();
    }

    public <T extends HIDBaseResponse> T statusCode(int statusCode) {
        this.statusCode = statusCode;
        return getThis();
    }

    public <T extends HIDBaseResponse> T delayBeforeBody(long delayBeforeBody) {
        this.delayBeforeBody = delayBeforeBody;
        return getThis();
    }

    public <T extends HIDBaseResponse> T timeToWriteResponseBody(long timeToWriteResponseBody) {
        this.timeToWriteResponseBody = timeToWriteResponseBody;
        return getThis();
    }

    public <T extends HIDBaseResponse> T shouldClose(boolean shouldClose) {
        this.shouldClose = shouldClose;
        return getThis();
    }

    @Override
    public long getDelayBeforeStatusResponse() {
        return delayBeforeStatusResponse;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public long getDelayBeforeBody() {
        return delayBeforeBody;
    }

    public long getTimeToWriteResponseBody() {
        return timeToWriteResponseBody;
    }

    public boolean getShouldClose() {
        return shouldClose;
    }
}
