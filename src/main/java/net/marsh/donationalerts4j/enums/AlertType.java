package net.marsh.donationalerts4j.enums;

import com.google.gson.annotations.SerializedName;
import net.marsh.donationalerts4j.event.*;

import java.util.Arrays;

public enum AlertType {
    @SerializedName("-1")
    Undefined(-1, null),
    @SerializedName("1")
    Donation(1, DonationEvent.class),
    @SerializedName("4")
    TwitchSubscription(4, null),
    @SerializedName("6")
    TwitchFollow(6, TwitchFollowEvent.class),
    @SerializedName("7")
    YouTubeSubscription(7, null),
    @SerializedName("11")
    TwitchBits(11, null),
    @SerializedName("13")
    TwitchGiftSubscription(13, null),
    @SerializedName("15")
    TwitchPrimeSubscription(15, null),
    @SerializedName("19")
    TwitchPoints(19, TwitchPointsEvent.class),
    @SerializedName("30")
    BoostyBuyGiftSubscription(30, BoostyBuyGiftSubscriptionEvent.class),
    @SerializedName("29")
    BoostyAcceptGiftSubscription(29, BoostyAcceptGiftSubscriptionEvent.class);

    private final int id;
    private final Class<? extends AlertEvent> clazz;

    AlertType(int value, Class<? extends AlertEvent> clazz) {
        this.id = value;
        this.clazz = clazz;
    }

    public int getId() {return id;}
    public Class<? extends AlertEvent> getEventClass() {return clazz;}
    public static AlertType valueOf(int id) {
        return Arrays.stream(values()).filter(t -> t.id == id).findFirst().orElse(Undefined);
    }
}
