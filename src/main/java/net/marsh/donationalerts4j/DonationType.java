package net.marsh.donationalerts4j;

import com.google.gson.annotations.SerializedName;

public enum DonationType {
    @SerializedName("-1")
    Undefined(-1),
    @SerializedName("1")
    Donation(1),
    @SerializedName("4")
    TwitchSubscription(4),
    @SerializedName("6")
    TwitchFollow(6),
    @SerializedName("7")
    YouTubeSubscription(7),
    @SerializedName("11")
    TwitchBits(11),
    @SerializedName("13")
    TwitchGiftSubscription(13),
    @SerializedName("15")
    TwitchPrimeSubscription(15),
    @SerializedName("19")
    TwitchPoints(19);

    public final int value;

    DonationType(int value) {
        this.value = value;
    }

    public static DonationType fromValue(int value) {
        switch (value) {
            case 1: return DonationType.Donation;
            case 4: return DonationType.TwitchSubscription;
            case 6: return DonationType.TwitchFollow;
            case 7: return DonationType.YouTubeSubscription;
            case 11: return DonationType.TwitchBits;
            case 13: return DonationType.TwitchGiftSubscription;
            case 15: return DonationType.TwitchPrimeSubscription;
            case 19: return DonationType.TwitchPoints;
            default: return DonationType.Undefined;
        }
    }
}
