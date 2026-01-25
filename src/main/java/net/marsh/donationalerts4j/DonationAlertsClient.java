package net.marsh.donationalerts4j;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import net.marsh.donationalerts4j.event.*;
import net.marsh.donationalerts4j.listener.AlertListener;
import net.marsh.donationalerts4j.listener.ListenerAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class DonationAlertsClient {
    private final Logger LOGGER;
    private final List<AlertListener> DONATION_LISTENERS = new ArrayList<>();
    private static final String SOCKET_URL = "https://socket.donationalerts.ru:443";
    private final Socket SOCKET;
    private String TOKEN;
    private final ClientConfiguration configuration;

    public DonationAlertsClient(String token, ClientConfiguration configuration) throws URISyntaxException {
        this.LOGGER = Logger.getLogger(getClass().getName());
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null!");
        }

        URI url = new URI(SOCKET_URL);
        SOCKET = IO.socket(url);
        this.TOKEN = token.trim();
        this.configuration = configuration;
        SOCKET.on(Socket.EVENT_CONNECT, handleConnect())
                .on(Socket.EVENT_DISCONNECT, handleDisconnect())
                .on(Socket.EVENT_ERROR, handleError())
                .on("donation", handleDonation());
    }

    public DonationAlertsClient(String token) throws URISyntaxException {
        this(token, new ClientConfiguration());
    }

    public String getToken() {
        return TOKEN;
    }

    private Emitter.Listener handleConnect() {return arg -> {if (this.configuration.isLogging()) LOGGER.info("Successfully connected!");};}
    private Emitter.Listener handleDisconnect() {return arg -> {if (this.configuration.isLogging()) LOGGER.info("Disconnected");};}
    private Emitter.Listener handleError() {return arg -> {if (this.configuration.isLogging()) LOGGER.severe(String.format("Error %s", arg[0].toString()));};}

    private Emitter.Listener handleDonation() {
        return arg -> {
            if (arg.length < 1) return;
            String content = arg[0].toString();
            if (this.configuration.isDebugMode()) System.out.println(content);

            try {
                this.fire(new AlertEventBuilder(content));
            } catch (Exception e) {
                LOGGER.severe(String.format("Error parsing JSON: %s", e.getMessage()));
            }
        };
    }

    public void disconnect() {
        SOCKET.close();
        this.TOKEN = null;
    }

    public boolean isConnected() {
        return SOCKET != null && SOCKET.connected();
    }

    public void build() {
        if (isConnected()) {throw new RuntimeException("Client is already connected!");}
        SOCKET.connect();
        try {
            SOCKET.emit("add-user", new JSONObject()
                    .put("token", this.TOKEN)
                    .put("type", "minor"));
        } catch (JSONException e) {
            throw new RuntimeException("Failed to build connection", e);
        }
    }

    public DonationAlertsClient addEventListeners(ListenerAdapter... listeners) {
        Collections.addAll(this.DONATION_LISTENERS, listeners);
        return this;
    }

    private synchronized void fire(AlertEventBuilder eventBuilder) {
        /* Deprecated code
        listener.onAlert(event);
        if (event instanceof TwitchFollowEvent) listener.onTwitchFollow((TwitchFollowEvent) event);
        else if (event instanceof TwitchPointsEvent) listener.onTwitchPoints((TwitchPointsEvent) event);
        else if (event instanceof DonationEvent) listener.onDonation((DonationEvent) event);
        */
        this.DONATION_LISTENERS.forEach(listener -> eventBuilder.build().handle(listener, eventBuilder.getJson()));
    }

    public void emit(AlertEventBuilder eventBuilder) {
        if (this.configuration.isEmitDebugMode()) System.out.println(eventBuilder.getJson());
        this.fire(eventBuilder);
    }

    @Override
    public String toString() {
        return String.format("DonationAlertsClient[token=%s]", this.TOKEN);
    }
}