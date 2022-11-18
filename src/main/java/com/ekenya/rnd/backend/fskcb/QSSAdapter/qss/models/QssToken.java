package com.ekenya.rnd.backend.fskcb.QSSAdapter.qss.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class QssToken implements Serializable {
    @SerializedName("access_token")
    private String value;
    @SerializedName("expires")
    private Date expires;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
