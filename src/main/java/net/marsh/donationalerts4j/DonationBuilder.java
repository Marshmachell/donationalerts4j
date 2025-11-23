package net.marsh.donationalerts4j;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", ID);
        jsonObject.addProperty("alert_type", type);
        jsonObject.addProperty("is_shown", IsShown);

        JsonObject additionalData = new JsonObject();
        additionalData.addProperty("randomness", (int) (Math.random() * 1000));

        jsonObject.add("additional_data", additionalData);
        jsonObject.addProperty("billing_system", "fake");
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("amount", String.valueOf(amount));
        jsonObject.addProperty("amount_formatted", String.format("%.2f", amount).replace(".", ","));
        jsonObject.addProperty("amount_main", amount);
        jsonObject.addProperty("currency", currency);
        jsonObject.addProperty("message", message);
        jsonObject.addProperty("date_created", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateCreated));
        jsonObject.addProperty("_is_test_alert", IsTest);
        return DonationEvent.Builder.fromJson(jsonObject);
    }
}