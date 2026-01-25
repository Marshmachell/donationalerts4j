package net.marsh.donationalerts4j.event;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.marsh.donationalerts4j.enums.AlertType;

public class AlertEventBuilder {
    private final String json;

    public AlertEventBuilder(String json) {
        this.json = json;
    }

    public JsonObject getJson() {
        return new Gson().fromJson(json, JsonObject.class);
    }

    public AlertEvent build() {
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        AlertType alertType = AlertType.valueOf(jsonObject.get("alert_type").getAsInt());
        return new Gson().fromJson(json, alertType.getEventClass());
    }
}
