package net.marsh.donationalerts4j.listener;

import net.marsh.donationalerts4j.event.DonationEvent;

public interface DonationListener {
    void onDonation(DonationEvent event);
}
