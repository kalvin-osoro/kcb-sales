package com.ekenya.rnd.backend.fskcb.QSSAdapter.qss.models;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class QssGroup implements Comparable<QssGroup> {
    @SerializedName("gid")
    private String id;
    @SerializedName("photo")
    private String photo;
    @SerializedName("name")
    private String name;
    @SerializedName("admin")
    private int owner;
    @SerializedName("members")
    private List<? extends QssUser> users;
    private Date dateAdded = Calendar.getInstance().getTime();

    public QssGroup(String id, String name, String photo, List<? extends QssUser> users) {

        this.id = id;
        this.name = name;
        this.photo = photo;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPhoto(){
        return this.photo;
    }
    public void setPhoto(String dialogPhoto) {
        this.photo = dialogPhoto;
    }

    public String getName(){
        return name;
    }
    public void setName(String dialogName) {
        this.name = dialogName;
    }

    public boolean isOwner() {
        return owner == 1;
    }

    public void setOwner(boolean owner) {
        this.owner = owner ? 1 : 0;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public List<? extends QssUser> getMembers() {
        return users;
    }

    public void setMembers(List<QssUser> users) {
        this.users = users;
    }

    @Override
    public int compareTo(QssGroup o) {
        int last = this.id.compareTo(o.id);
        return last == 0 ? this.id.compareTo(o.id) : last;
    }
}
