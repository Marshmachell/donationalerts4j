package net.marsh.donationalerts4j.event;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import net.marsh.donationalerts4j.listener.AlertListener;

public class DonationEvent extends BillingEvent {
    private String message;
    @SerializedName("tts_url")
    private String ttsUrl;

    public String getTtsUrl() {
        return ttsUrl;
    }

    @Override
    public void handle(AlertListener listener, JsonObject json) {
        if (json.get("alert_type").getAsJsonPrimitive().isString()) return;
        super.handle(listener);
        listener.onDonation(this);
    }
}
