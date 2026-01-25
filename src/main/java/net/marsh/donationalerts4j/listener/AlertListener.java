package net.marsh.donationalerts4j.listener;

import net.marsh.donationalerts4j.event.*;

public interface AlertListener {
    void onAlert(AlertEvent event);
    default void onBilling(BillingEvent event) {}
    default void onDonation(DonationEvent event) {}
    default void onTwitchFollow(TwitchFollowEvent event) {}
    default void onTwitchPoints(TwitchPointsEvent event) {}

    default void onBoostyBuyGiftSubscription(BoostyBuyGiftSubscription event) {}
    default void onBoostyAcceptGiftSubscription(BoostyAcceptGiftSubscription event) {}
}
