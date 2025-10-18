import net.marsh.donationalerts4j.event.DonationEvent;
import net.marsh.donationalerts4j.listener.DonationListener;

public class DonationReceiveListener implements DonationListener {
    @Override
    public void onDonation(DonationEvent event) {
        System.out.printf("[%s] %s %s: %s\n",
                event.getUsername(),
                event.getAmountFormatted(),
                event.getCurrency(),
                event.getMessage());
    }
}
