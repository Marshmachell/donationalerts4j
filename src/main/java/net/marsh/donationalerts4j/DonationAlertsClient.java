package net.marsh.donationalerts4j;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import net.marsh.donationalerts4j.event.DonationEvent;
import net.marsh.donationalerts4j.listener.DonationListener;
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
    private final List<DonationListener> DONATION_LISTENERS = new ArrayList<>();
    private static final String SOCKET_URI = "https://socket.donationalerts.ru:443";
    private final Socket SOCKET;
    private String TOKEN;

    public DonationAlertsClient(String token) throws URISyntaxException {
        this.LOGGER = Logger.getLogger(getClass().getName());
        if (token == null || token.trim().isEmpty()) {throw new IllegalArgumentException("Token cannot be null!");}

        URI url = new URI(SOCKET_URI); SOCKET = IO.socket(url); this.TOKEN = token.trim();
        SOCKET.on(Socket.EVENT_CONNECT, handleConnect()).on(Socket.EVENT_DISCONNECT, handleDisconnect()).on(Socket.EVENT_ERROR, handleError()).on("donation", handleDonation());
    }

    private Emitter.Listener handleConnect() {return arg -> LOGGER.info("Successfully connected!");}
    private Emitter.Listener handleDisconnect() {return arg -> LOGGER.info("Disconnected");}
    private Emitter.Listener handleError() {return arg -> LOGGER.severe(String.format("Error %s", arg[0].toString()));}

    private Emitter.Listener handleDonation() {
        return arg -> {
            if (arg.length < 1/* || ((String)arg[0]).contains("referrer")*/) return;
            String message = arg[0].toString();

            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("token", this.TOKEN);

                new Gson().fromJson(message, JsonObject.class).entrySet().forEach(e -> jsonObject.add(e.getKey(), e.getValue()));
                DonationEvent event = DonationEvent.Builder.fromJson(jsonObject);

                if (event.getType().equals(DonationType.Donation) && jsonObject.has("referrer")) return;
                else if (event.getType().equals(DonationType.TwitchPoints) && !jsonObject.get("referrer").isJsonNull()) return;
                this.fire(event);
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

    public DonationAlertsClient addEventListeners(DonationListener... listeners) {
        Collections.addAll(this.DONATION_LISTENERS, listeners);
        return this;
    }

    private synchronized void fire(DonationEvent event) {
        this.DONATION_LISTENERS.forEach(listener -> listener.onDonation(event));
    }

    public void emit(DonationEvent event) {
        this.fire(event);
    }
}