package net.marsh.donationalerts4j.listener;

import net.marsh.donationalerts4j.event.*;

public abstract class ListenerAdapter implements AlertListener {
    public void onAlert(AlertEvent event) {}
    public void onBilling(BillingEvent event) {}
    public void onDonation(DonationEvent event) {}
    public void onTwitchFollow(TwitchFollowEvent event) {}
    public void onTwitchPoints(TwitchPointsEvent event) {}

    public void onBoostyBuyGiftSubscription(BoostyBuyGiftSubscription event) {}
    public void onBoostyAcceptGiftSubscription(BoostyAcceptGiftSubscription event) {}
}
