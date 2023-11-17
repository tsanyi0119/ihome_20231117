package com.example.smarthomeandroid.utils.api.soap.response;

public class StatusResponse {
    private String status;
    private String error;

    public StatusResponse(String status, String error) {
        this.status = status;
        this.error = error;
    }

    public StatusResponse(){

    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
