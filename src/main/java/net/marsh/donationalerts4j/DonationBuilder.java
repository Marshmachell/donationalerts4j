package net.marsh.donationalerts4j;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.marsh.donationalerts4j.event.DonationEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DonationBuilder {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private int ID;
    private int type;
    private int IsShown;
    private String username;
    private float amount;
    private String currency;
    private String message;
    private final Date dateCreated;
    private boolean IsTest;

    public DonationBuilder() {
        this.ID = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        this.type = -1;
        this.IsShown = 0;
        this.username = "username";
        this.amount = 0;
        this.currency = "USD";
        this.message = "message";
        this.dateCreated = new Date();
        this.IsTest = true;
    }

    public DonationBuilder id(int id) {
        this.ID = id;
        return this;
    }

    public DonationBuilder type(DonationType type) {
        this.type = type.value;
        return this;
    }

    public DonationBuilder isShown(boolean isShown) {
        this.IsShown = isShown ? 1 : 0;
        return this;
    }

    public DonationBuilder username(String username) {
        this.username = username;
        return this;
    }

    public DonationBuilder amount(float amount) {
        this.amount = amount;
        return this;
    }

    public DonationBuilder currency(String currency) {
        this.currency = currency;
        return this;
    }

    public DonationBuilder message(String message) {
        this.message = message;
        return this;
    }

    public DonationBuilder isTest(boolean isTest) {
        this.IsTest = isTest;
        return this;
    }

    public DonationEvent build() {
        String js = String.format(
                "{\"id\":%s,\"alert_type\":\"%s\",\"is_shown\":\"%s\",\"additional_data\":\"{\\\"randomness\\\":%s\"},\"billing_system\":\"fake\",\"username\":\"%s\",\"amount\":%s,\"amount_formatted\":\"%s\",\"amount_main\":%s,\"currency\":\"%s\",\"message\":\"%s\",\"date_created\":\"%s\",\"_is_test_alert\":%s}",
                ID, type, IsShown, (int) (Math.random() * 1000), escapeJson(username), amount, String.format("%.2f", amount).replace(".", ","), amount, currency, escapeJson(message), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateCreated), IsTest
        );
        return DonationEvent.Builder.fromJson(js);
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}