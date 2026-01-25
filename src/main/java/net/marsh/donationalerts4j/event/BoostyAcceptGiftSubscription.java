package net.marsh.donationalerts4j.event;

import com.google.gson.JsonObject;
import net.marsh.donationalerts4j.listener.AlertListener;

public class BoostyAcceptGiftSubscription extends AlertEvent {
    public String getSender() {
        return additionalData != null && additionalData.eventData != null ? additionalData.eventData.sender : null;
    }

    public int getPeriod() {
        return additionalData != null && additionalData.eventData != null ? additionalData.eventData.period : 0;
    }

    public String getLevelName() {
        return additionalData != null && additionalData.eventData != null ? additionalData.eventData.levelName : null;
    }

    @Override
    public void handle(AlertListener listener, JsonObject json) {
        super.handle(listener, json);
        listener.onBoostyAcceptGiftSubscription(this);
    }
}
