package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

public class CreatePINRequest {
    private String phoneNo;
    private String staffNo;
    private String newPIN;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getNewPIN() {
        return newPIN;
    }

    public void setNewPIN(String newPIN) {
        this.newPIN = newPIN;
    }
}
