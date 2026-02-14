package net.marsh.donationalerts4j.event

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import net.marsh.donationalerts4j.adapter.AdditionalDataAdapter
import net.marsh.donationalerts4j.data.AdditionalData
import net.marsh.donationalerts4j.enums.AlertTypeKt

abstract class AlertEventKt {
    private var id: Int = 0
    @SerializedName("alert_type")
    private var alertType: AlertTypeKt? = null
    @SerializedName("is_shown")
    private var isShown: String? = null
    private var username: String? = null
    @SerializedName("date_created")
    private var dateCreated: String? = null
    private var referrer: String? = null
    @SerializedName("_is_test_alert")
    private var isTest: Boolean? = null
    @SerializedName("message_type")
    private var messageType: String? = null

    @SerializedName("additional_data")
    @JsonAdapter(AdditionalDataAdapter::class)
    var additionalData: AdditionalData? = null

    fun getId(): Int = id
    fun getAlertType(): AlertTypeKt? = alertType
    fun isShown(): Boolean = isShown != "0"
    fun getUsername(): String? = username
    fun getDateCreated(): String? = dateCreated
    fun getReferrer(): String? = referrer
    fun isTest(): Boolean? = isTest
    fun getMessageType(): String? = messageType

    class Builder(private val json: String) {
        private val gson = Gson()

        fun getJson(): JsonObject = gson.fromJson(json, JsonObject::class.java)
        fun build(): AlertEvent {
            val jsonObject = gson.fromJson(json, JsonObject::class.java)
            val alertType = AlertTypeKt.entries.first {it.value == jsonObject.get("alert_type").asInt}
            return gson.fromJson(json, alertType.getEventClass())
        }
    }
}