package com.example.smarthomeandroid.utils.api.soap.response;

public class LoginResponse {
    private String status;
    private String token;
    private String userAccount;
    private String error;
    private String refreshToken;

    public LoginResponse(String status, String token, String error, String userAccount, String refreshToken) {
        this.status = status;
        this.token = token;
        this.error = error;
        this.userAccount = userAccount;
        this.refreshToken = refreshToken;
    }

    public LoginResponse() {
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }

    public String getUserAccount() {
        return userAccount;
    }
    public String getRefreshToken() {
        return refreshToken;
    }

}
