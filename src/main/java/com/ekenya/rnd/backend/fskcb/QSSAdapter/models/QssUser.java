package com.ekenya.rnd.backend.fskcb.QSSAdapter.models;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Bourne Koloh on 28 May,2020.
 * Eclectics International, Products and R&D
 * PROJECT: Messenger
 */
public class QssUser implements Comparable<QssUser>{
    @SerializedName("uid")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("photo")
    private String photo;

    private String clientId;
    private String clientSecret;

    public QssUser(){}

    public QssUser(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.photo = avatar;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public int compareTo(QssUser o) {
        int last = this.id.compareTo(o.id);
        return last == 0 ? this.id.compareTo(o.id) : last;
    }
}
