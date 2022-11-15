package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

public class SendVerificationCodeRequest {
    private String phoneNo;
    private String staffNo;

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
}
