import net.marsh.donationalerts4j.DonationAlertsClient;

import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        new DonationAlertsClient("SWpMcPps9N7ECfzYYVf8").addEventListeners(new DonationReceiveListener()).build();
    }
}
