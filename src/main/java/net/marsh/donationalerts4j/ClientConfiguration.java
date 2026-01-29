package net.marsh.donationalerts4j;

public record ClientConfiguration(boolean logging, boolean debugMode, boolean deepDebugMode) {
    public ClientConfiguration() {
        this(true, false, false);
    }

    public ClientConfiguration setLogging(boolean bool) {
        return new ClientConfiguration(bool, debugMode, deepDebugMode);
    }

    public ClientConfiguration setDebug(boolean bool) {
        return new ClientConfiguration(logging, bool, deepDebugMode);
    }

    public ClientConfiguration setEmitDebug(boolean bool) {
        return new ClientConfiguration(logging, debugMode, bool);
    }
}
