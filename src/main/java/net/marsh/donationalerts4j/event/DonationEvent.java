package net.marsh.donationalerts4j.event;

import com.google.gson.annotations.SerializedName;

public class DonationEvent extends BillingEvent {
    private String message;
    @SerializedName("tts_url")
    private String ttsUrl;

    public String getMessage() {
        return message;
    }

    public String getTtsUrl() {
        return ttsUrl;
    }
}
