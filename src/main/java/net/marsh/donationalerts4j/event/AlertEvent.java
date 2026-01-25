package net.marsh.donationalerts4j.event;

import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import net.marsh.donationalerts4j.adapter.AdditionalDataAdapter;
import net.marsh.donationalerts4j.data.AdditionalData;
import net.marsh.donationalerts4j.data.RewardData;
import net.marsh.donationalerts4j.data.UserData;
import net.marsh.donationalerts4j.enums.AlertType;
import net.marsh.donationalerts4j.listener.AlertListener;

public abstract class AlertEvent {
    private int id;
    @SerializedName("alert_type")
    private AlertType alertType;
    @SerializedName("is_shown")
    private String isShown;
    private String username;
    @SerializedName("date_created")
    private String dateCreated;
    private String referrer;
    @SerializedName("_is_test_alert")
    private boolean isTest;
    @SerializedName("message_type")
    private String messageType;

    @SerializedName("additional_data")
    @JsonAdapter(AdditionalDataAdapter.class)
    public AdditionalData additionalData;

    public int getId() {
        return id;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public boolean isShown() {
        return !"0".equals(isShown);
    }

    public String getUsername() {
        return username;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getReferrer() {
        return referrer;
    }

    public boolean isTest() {
        return isTest;
    }

    public String getMessageType() {
        return messageType;
    }

    public void handle(AlertListener listener) {
        listener.onAlert(this);
    }

    public void handle(AlertListener listener, JsonObject json) {
        listener.onAlert(this);
    }
}
