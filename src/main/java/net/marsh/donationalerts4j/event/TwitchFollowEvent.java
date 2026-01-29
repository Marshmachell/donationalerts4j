package net.marsh.donationalerts4j.event;

public class TwitchFollowEvent extends AlertEvent {
    @Override
    public String getUsername() {
        return additionalData != null && additionalData.eventData != null ? additionalData.eventData.user.displayName : null;
    }
}
