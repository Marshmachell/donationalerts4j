package net.marsh.donationalerts4j;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import net.marsh.donationalerts4j.annotation.AlertHandler;
import net.marsh.donationalerts4j.event.AlertEvent;
import net.marsh.donationalerts4j.handler.RegisteredHandler;
import net.marsh.donationalerts4j.listener.AlertListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Logger;

public class DonationAlertsClient {
    private static final String WSS_URL = "https://socket.donationalerts.ru";
    private final Socket socket;
    private final String token;
    private final Logger logger;
    private final List<AlertListener> listeners = new ArrayList<>();
    private final Map<Class<? extends AlertEvent>, List<RegisteredHandler>> handlers = new HashMap<>();
    private final ClientConfiguration configuration;

    public DonationAlertsClient(String token, ClientConfiguration configuration) throws URISyntaxException {
        this.logger = Logger.getLogger(getClass().getName());
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null!");
        }

        IO.Options options = new IO.Options();
        options.port = 443;
        socket = IO.socket(WSS_URL, options);

        this.token = token.trim();
        this.configuration = configuration;
        socket.on(Socket.EVENT_CONNECT, handleConnect())
                .on(Socket.EVENT_DISCONNECT, handleDisconnect())
                .on(Socket.EVENT_CONNECT_ERROR, handleError())
                .on("donation", handleDonation());
    }

    public void registerListeners() {
        for (AlertListener listener : listeners) {
            for (Method method : listener.getClass().getDeclaredMethods()) {
                if (!method.isAnnotationPresent(AlertHandler.class)) continue;
                if (method.getParameterCount() != 1) continue;
                if (!AlertEvent.class.isAssignableFrom(method.getParameterTypes()[0])) continue;

                Class<? extends AlertEvent> type = method.getParameterTypes()[0].asSubclass(AlertEvent.class);
                method.setAccessible(true);

                AlertHandler annotation = method.getAnnotation(AlertHandler.class);
                handlers.computeIfAbsent(type, k -> new ArrayList<>())
                        .add(new RegisteredHandler(listener, method, annotation.priority()));
            }
        }
        handlers.values().forEach(list -> list.sort(Comparator.comparing(RegisteredHandler::priority)));
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

            if (this.configuration.logging()) logger.info("Successfully connected!");
        };
    }

    private Emitter.Listener handleDisconnect() {
        return arg -> {
            if (this.configuration.logging()) logger.info("Disconnected");
        };
    }

    private Emitter.Listener handleError() {
        return arg -> {
            if (this.configuration.logging()) logger.severe(String.format("Error %s", arg[0].toString()));
        };
    }

    private Emitter.Listener handleDonation() {
        return arg -> {
            if (arg.length < 1) return;
            String content = arg[0].toString();
            if (this.configuration.debugMode()) System.out.println(content);

            try {
                this.fire(new AlertEvent.Builder(content).build());
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

    public DonationAlertsClient addEventListeners(AlertListener... listeners) {
        Collections.addAll(this.listeners, listeners);
        registerListeners();
        return this;
    }

//    private synchronized void fire(AlertEvent.Builder alertBuilder) {
//        this.listeners.forEach(listener -> alertBuilder.build().handle(listener, alertBuilder.getJson()));
//    }

    private synchronized void fire(AlertEvent event) {
        for (Map.Entry<Class<? extends AlertEvent>, List<RegisteredHandler>> entry : handlers.entrySet()) {
            if (entry.getKey().isAssignableFrom(event.getClass())) {
                for (RegisteredHandler handler : entry.getValue()) {
                    try {
                        handler.method().invoke(handler.listener(), event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void emit(AlertEvent.Builder alertBuilder) {
        if (this.configuration.deepDebugMode()) System.out.println(alertBuilder.getJson());
        this.fire(alertBuilder.build());
    }

    @Override
    public String toString() {
        return String.format("DonationAlertsClient[token=%s]", this.token);
    }
}