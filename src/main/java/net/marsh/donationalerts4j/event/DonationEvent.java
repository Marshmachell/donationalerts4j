package net.marsh.donationalerts4j.event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import net.marsh.donationalerts4j.DonationType;

import java.util.Date;

public class DonationEvent {
    private transient JsonObject originalJson;
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private String token;
    private int id;
    @SerializedName("alert_type")
    private DonationType type;
    @SerializedName("is_shown")
    private String isShown;
    @SerializedName("additional_data")
    private String additional;
    @SerializedName("billing_system")
    private String billingSystem;
    @SerializedName("billing_system_type")
    private String billingSystemType;
    private String username;
    private float amount;
    @SerializedName("amount_formatted")
    private String amountFormatted;
    @SerializedName("amount_main")
    private float amountMain;
    private String currency;
    private String message;
    @SerializedName("date_created")
    private Date dateCreated;
    private String emotes;
    @SerializedName("ap_id")
    private String apId;
    @SerializedName("_is_test_alert")
    private boolean isTest;

    public String getToken() {return token;}
    public int id() {return id;}
    public DonationType getType() {return type;}
    public String getIsShown() {return isShown;}
    public String getAdditional() {return additional;}
    public String getBillingSystem() {return billingSystem;}
    public String getBillingSystemType() {return billingSystemType;}
    public String getUsername() {return username;}
    public float getAmount() {return amount;}
    public String getAmountFormatted() {return amountFormatted;}
    public float getAmountMain() {return amountMain;}
    public String getCurrency() {return currency;}
    public String getMessage() {return message;}
    public Date getDateCreated() {return dateCreated;}
    public String getEmotes() {return emotes;}
    public String getApId() {return apId;}
    public boolean isTest() {return isTest;}

    public JsonObject toJson() {
        return originalJson;
    }

    public static class Builder {
        public static DonationEvent fromJson(JsonObject jsonObject) {
            DonationEvent event = gson.fromJson(jsonObject, DonationEvent.class);
            event.originalJson = jsonObject;
            return event;
        }
    }
}