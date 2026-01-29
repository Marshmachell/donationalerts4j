package net.marsh.donationalerts4j;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import net.marsh.donationalerts4j.event.AlertEvent;
import net.marsh.donationalerts4j.listener.AlertListener;
import net.marsh.donationalerts4j.listener.ListenerAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class DonationAlertsClient {
    private static final String WSS_URL = "https://socket.donationalerts.ru";
    private final Socket socket;
    private final String token;
    private final Logger logger;
    private final List<AlertListener> listeners = new ArrayList<>();
    private final ClientConfiguration configuration;

    public DonationAlertsClient(String token, ClientConfiguration configuration) throws URISyntaxException {
        this.logger = Logger.getLogger(getClass().getName());
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null!");
        }

        IO.Options options = new IO.Options();
        options.port = 433;
        socket = IO.socket(WSS_URL, options);

        this.token = token.trim();
        this.configuration = configuration;
        socket.on(Socket.EVENT_CONNECT, handleConnect())
                .on(Socket.EVENT_DISCONNECT, handleDisconnect())
                .on(Socket.EVENT_CONNECT_ERROR, handleError())
                .on("donation", handleDonation());
    }

    public DonationAlertsClient(String token) throws URISyntaxException {
        this(token, new ClientConfiguration());
    }

    public String getToken() {
        return token;
    }

    private Emitter.Listener handleConnect() {
        return arg -> {
            try {
                JSONObject connectPayload = new JSONObject().put("token", this.token).put("type", "minor");
                socket.emit("add-user", connectPayload);
            } catch (JSONException e) {
                throw new RuntimeException("Error establishing connection", e);
            }

            if (this.configuration.isLogging()) logger.info("Successfully connected!");
        };
    }
    private Emitter.Listener handleDisconnect() {
        return arg -> {
            if (this.configuration.isLogging()) logger.info("Disconnected");
        };
    }
    private Emitter.Listener handleError() {
        return arg -> {
            if (this.configuration.isLogging()) logger.severe(String.format("Error %s", arg[0].toString()));
        };
    }
    private Emitter.Listener handleDonation() {
        return arg -> {
            if (arg.length < 1) return;
            String content = arg[0].toString();
            if (this.configuration.isDebugMode()) System.out.println(content);

            try {
                this.fire(new AlertEvent.Builder(content));
            } catch (Exception e) {
                throw new RuntimeException("Error parsing JSON: ", e);
            }
        };
    }

    public void disconnect() {
        socket.close();
    }

    public boolean isConnected() {
        return socket != null && socket.connected();
    }

    public void connect() {
        if (isConnected()) {
            throw new RuntimeException("Client is already connected!");
        }
        socket.connect();
    }

    public DonationAlertsClient addEventListeners(ListenerAdapter... listeners) {
        Collections.addAll(this.listeners, listeners);
        return this;
    }

    private synchronized void fire(AlertEvent.Builder alertBuilder) {
        this.listeners.forEach(listener -> alertBuilder.build().handle(listener, alertBuilder.getJson()));
    }

    public void emit(AlertEvent.Builder alertBuilder) {
        if (this.configuration.isEmitDebugMode()) System.out.println(alertBuilder.getJson());
        this.fire(alertBuilder);
    }

    @Override
    public String toString() {
        return String.format("DonationAlertsClient[token=%s]", this.token);
    }
}