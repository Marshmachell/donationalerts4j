package net.marsh.donationalerts4j.event;

import com.google.gson.JsonObject;
import net.marsh.donationalerts4j.listener.AlertListener;

public class TwitchPointsEvent extends AlertEvent {
    @Override
    public String getUsername() {
        return additionalData != null && additionalData.eventData != null ? additionalData.eventData.user.displayName : null;
    }

    public int getCost() {
        return additionalData != null && additionalData.eventData != null ? additionalData.eventData.reward.cost : 0;
    }

    public String getTitle() {
        return additionalData != null && additionalData.eventData != null ? additionalData.eventData.reward.title : null;
    }

    @Override
    public void handle(AlertListener listener, JsonObject jsonObject) {
        super.handle(listener);
        listener.onTwitchPoints(this);
    }
}
