package com.ekenya.rnd.backend.fskcb.AuthModule.models.resp;

import java.util.Date;
import java.util.List;

public class LoginResponse {

    private String token;
    private String type;
    private Date expires;
    private List<String> profiles;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public List<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }
}
