package net.marsh.donationalerts4j.event;

import com.google.gson.JsonObject;
import net.marsh.donationalerts4j.listener.AlertListener;

public class TwitchFollowEvent extends AlertEvent {
    @Override
    public String getUsername() {
        return additionalData != null && additionalData.eventData != null ? additionalData.eventData.user.displayName : null;
    }

    @Override
    public void handle(AlertListener listener, JsonObject jsonObject) {
        super.handle(listener);
        listener.onTwitchFollow(this);
    }
}
