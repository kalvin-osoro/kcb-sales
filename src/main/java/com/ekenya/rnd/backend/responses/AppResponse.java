package com.ekenya.rnd.backend.responses;


public class AppResponse {
    private int status = 0;
    private String message;
    private Object data;

    public AppResponse(){}

    public AppResponse(int status, Object data, String message){
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

