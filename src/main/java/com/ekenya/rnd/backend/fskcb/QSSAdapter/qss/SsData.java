package com.ekenya.rnd.backend.fskcb.QSSAdapter.qss;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bourne Koloh on 05 December,2019.
 * Eclectics International, Products and R&D
 * PROJECT: Messenger
 */
public class SsData implements Serializable {
    @SerializedName("action")
    public String action;
    @SerializedName("senderId")
    public String senderId;
    @SerializedName("time")
    public String time;

    @SerializedName("data")
    public JsonElement data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
