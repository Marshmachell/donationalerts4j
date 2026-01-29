package net.marsh.donationalerts4j.event;

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
}
