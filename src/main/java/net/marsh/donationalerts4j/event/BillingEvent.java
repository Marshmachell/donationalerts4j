package net.marsh.donationalerts4j.event;

import com.google.gson.annotations.SerializedName;
import net.marsh.donationalerts4j.enums.Currency;
import net.marsh.donationalerts4j.listener.AlertListener;

public abstract class BillingEvent extends AlertEvent {
    @SerializedName("billing_system")
    private String billingSystem;
    @SerializedName("billing_system_type")
    private String billingSystemType;
    private String amount;
    @SerializedName("amount_formatted")
    private String amountFormatted;
    @SerializedName("amount_main")
    private float amountMain;
    private Currency currency;

    public String getBillingSystem() {
        return billingSystem.toUpperCase();
    }

    public String getBillingSystemType() {
        return billingSystemType;
    }

    public String getAmount() {
        return amount;
    }

    public String getAmountFormatted() {
        return amountFormatted;
    }

    public float getAmountMain() {
        return amountMain;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BillingEvent() {}

    @Override
    public void handle(AlertListener listener) {
        super.handle(listener);
        listener.onBilling(this);
    }
}
