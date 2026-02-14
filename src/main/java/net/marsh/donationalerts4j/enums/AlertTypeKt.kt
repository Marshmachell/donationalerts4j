package net.marsh.donationalerts4j.enums

import com.google.gson.annotations.SerializedName
import net.marsh.donationalerts4j.event.*

enum class AlertTypeKt(val value: Int, val clazz: Class<out AlertEvent>?) {
    @SerializedName("-1")
    Undefined(-1, null),
    @SerializedName("1")
    Donation(1, DonationEvent::class.java),
    @SerializedName("4")
    TwitchSubscription(4, null),
    @SerializedName("6")
    TwitchFollow(6, TwitchFollowEvent::class.java),
    @SerializedName("7")
    YoutubeSubscription(7, null),
    @SerializedName("11")
    TwitchBits(11, null),
    @SerializedName("13")
    TwitchGiftSubscription(13, null),
    @SerializedName("15")
    TwitchPrimeSubscription(15, null),
    @SerializedName("19")
    TwitchPoints(19, TwitchPointsEvent::class.java),
    @SerializedName("30")
    BoostyBuyGiftSubscription(30, BoostyBuyGiftSubscriptionEvent::class.java),
    @SerializedName("29")
    BoostyAcceptGiftSubscription(29, BoostyAcceptGiftSubscriptionEvent::class.java);

    fun getEventClass(): Class<out AlertEvent>? = clazz
    fun valueOf(id: Int): AlertTypeKt = entries.firstOrNull{it.value == id}?: Undefined
}