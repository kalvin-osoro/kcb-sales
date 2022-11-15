package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

public class ChangePINRequest {
    private String currentPin;
    private String newPin;

    public String getCurrentPin() {
        return currentPin;
    }

    public void setCurrentPin(String currentPin) {
        this.currentPin = currentPin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }
}
