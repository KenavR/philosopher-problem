package pprg.philosopher.test;

public class TestSettings {
    private final long timeout;

    public TestSettings() {
        this.timeout = -1;
    }

    public TestSettings(long timeoutInMS) {
        this.timeout = timeoutInMS;
    }

    public long getTimeout() {
        return timeout;
    }
}
