package net.marsh.donationalerts4j.event;

public class BoostyAcceptGiftSubscriptionEvent extends AlertEvent {
    public String getSender() {
        return additionalData != null && additionalData.eventData != null ? additionalData.eventData.sender : null;
    }

    public int getPeriod() {
        return additionalData != null && additionalData.eventData != null ? additionalData.eventData.period : 0;
    }

    public String getLevelName() {
        return additionalData != null && additionalData.eventData != null ? additionalData.eventData.levelName : null;
    }
}
