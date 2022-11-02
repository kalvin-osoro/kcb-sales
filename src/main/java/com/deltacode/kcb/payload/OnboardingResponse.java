package com.deltacode.kcb.payload;

public class OnboardingResponse {
    private String message;
    private String status;
    private long accountNo;
    public OnboardingResponse(String message, String status, long accountNo) {
        super();
        this.message = message;
        this.status = status;
        this.accountNo = accountNo;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public long getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }
}
