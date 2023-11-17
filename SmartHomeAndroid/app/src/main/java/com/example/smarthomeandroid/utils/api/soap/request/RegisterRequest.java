package com.example.smarthomeandroid.utils.api.soap.request;

public class RegisterRequest {
    private String email;
    private String password;
    private String userAccount;
    private String fcmId;
    public RegisterRequest() {
    }
    public RegisterRequest(String email, String password, String userAccount, String fcmId) {
        this.email = email;
        this.password = password;
        this.userAccount = userAccount;
        this.fcmId = fcmId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }
}
