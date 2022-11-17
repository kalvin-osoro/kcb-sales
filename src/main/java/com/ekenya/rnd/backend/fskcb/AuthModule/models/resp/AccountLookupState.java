package com.ekenya.rnd.backend.fskcb.AuthModule.models.resp;

public enum AccountLookupState
{
    NOT_ACTIVATED(2),
    ACTIVE(1),
    NOT_FOUND(0);

    private int state;

    AccountLookupState(int s) {
        this.state = s;
    }

    public int getState() {
        return state;
    }
}


