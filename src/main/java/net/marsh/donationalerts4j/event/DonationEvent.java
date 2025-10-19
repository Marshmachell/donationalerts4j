package net.marsh.donationalerts4j.event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import net.marsh.donationalerts4j.DonationType;

import java.util.Date;

public class DonationEvent {
    private transient String originalJson;
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @SerializedName("id")
    private int ID;
    @SerializedName("alert_type")
    private DonationType type;
    @SerializedName("is_shown")
    private String IsShown;
    @SerializedName("additional_data")
    private String additional;
    @SerializedName("billing_system")
    private String billingSystem;
    @SerializedName("billing_system_type")
    private String billingSystemType;
    @SerializedName("username")
    private String username;
    @SerializedName("amount")
    private float amount;
    @SerializedName("amount_formatted")
    private String amountFormatted;
    @SerializedName("amount_main")
    private float amountMain;
    @SerializedName("currency")
    private String currency;
    @SerializedName("message")
    private String message;
    @SerializedName("date_created")
    private Date dateCreated;
    @SerializedName("emotes")
    private String emotes;
    @SerializedName("ap_id")
    private String apId;
    @SerializedName("_is_test_alert")
    private boolean IsTest;

    public int id() { return ID; }
    public DonationType getType() { return type; }
    public String getIsShown() { return IsShown; }
    public String getAdditional() { return additional; }
    public String getBillingSystem() { return billingSystem; }
    public String getBillingSystemType() { return billingSystemType; }
    public String getUsername() { return username; }
    public float getAmount() { return amount; }
    public String getAmountFormatted() { return amountFormatted; }
    public float getAmountMain() { return amountMain; }
    public String getCurrency() { return currency; }
    public String getMessage() { return message; }
    public Date getDateCreated() { return dateCreated; }
    public String getEmotes() { return emotes; }
    public String getApId() { return apId; }
    public boolean isTest() { return IsTest; }

    public String toJson() {
        return originalJson;
    }

    public static class Builder {
        public static DonationEvent fromJson(Object json) {
            String jsonString = (String) json;
            DonationEvent event = gson.fromJson(jsonString, DonationEvent.class);
            event.originalJson = jsonString;
            return event;
        }
    }
}