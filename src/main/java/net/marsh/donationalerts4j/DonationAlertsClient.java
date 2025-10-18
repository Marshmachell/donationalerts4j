package net.marsh.donationalerts4j;

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
    protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    protected final List<DonationListener> DONATION_LISTENERS = new ArrayList<>();
    protected static final String SOCKET_URI = "https://socket.donationalerts.ru:443";
    protected final Socket SOCKET;
    protected String TOKEN;

    public DonationAlertsClient(String token) throws URISyntaxException {
        URI url = new URI(SOCKET_URI); SOCKET = IO.socket(url); this.TOKEN = token;
        SOCKET.on(Socket.EVENT_CONNECT, handleConnect()).on(Socket.EVENT_DISCONNECT, handleDisconnect()).on(Socket.EVENT_ERROR, handleError()).on("donation", handleDonation());
    }

    private Emitter.Listener handleConnect() {return arg -> LOGGER.info("Successfully connected!");}
    private Emitter.Listener handleDisconnect() {return arg -> LOGGER.info("Disconnected");}
    private Emitter.Listener handleError() {return arg -> LOGGER.warning(String.format("Error %s", arg[0].toString()));}

    private Emitter.Listener handleDonation() {
        return arg -> {
            if (arg.length < 1 || !((String)arg[0]).contains("referrer")) return;
            String json = arg[0].toString();

            this.fire(DonationEvent.Builder.fromJson(json));
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
            throw new RuntimeException(e);
        }
    }

    public DonationAlertsClient addEventListeners(DonationListener... listeners) {
        Collections.addAll(this.DONATION_LISTENERS, listeners);
        return this;
    }

    public void fire(DonationEvent event) {
        this.DONATION_LISTENERS.forEach(listener -> listener.onDonation(event));
    }
}