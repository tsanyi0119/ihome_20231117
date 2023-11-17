package com.example.smarthomeandroid.utils.api.soap.request;

public class RegisterVerifyRequest {
    private String email;
    private String code;

    public RegisterVerifyRequest() {
    }

    public RegisterVerifyRequest(String code, String email) {
        this.code = code;
        this.email = email;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
