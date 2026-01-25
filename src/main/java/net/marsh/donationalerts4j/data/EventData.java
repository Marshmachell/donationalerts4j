package net.marsh.donationalerts4j.data;

import com.google.gson.annotations.SerializedName;

public class EventData {
    public UserData user;
    public RewardData reward;

    public int period;
    @SerializedName("level_name")
    public String levelName;

    public String sender;
}
