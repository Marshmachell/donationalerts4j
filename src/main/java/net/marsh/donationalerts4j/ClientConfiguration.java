package net.marsh.donationalerts4j;

public class ClientConfiguration {
    private final boolean logging;
    private final boolean debugMode;
    private final boolean emitDebugMode;

    public ClientConfiguration() {
        this.logging = true;
        this.debugMode = false;
        this.emitDebugMode = false;
    }

    public ClientConfiguration(boolean logging, boolean debugMode, boolean deepDebugMode) {
        this.logging = logging;
        this.debugMode = debugMode;
        this.emitDebugMode = deepDebugMode;
    }

    public boolean isLogging() {
        return logging;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public boolean isEmitDebugMode() {
        return emitDebugMode;
    }

    public ClientConfiguration setLogging(boolean bool) {
        return new ClientConfiguration(bool, debugMode, emitDebugMode);
    }

    public ClientConfiguration setDebug(boolean bool) {
        return new ClientConfiguration(logging, bool, emitDebugMode);
    }

    public ClientConfiguration setEmitDebug(boolean bool) {
        return new ClientConfiguration(logging, debugMode, bool);
    }
}
