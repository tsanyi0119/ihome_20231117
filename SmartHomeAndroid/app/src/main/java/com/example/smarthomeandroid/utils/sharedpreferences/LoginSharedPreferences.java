package com.example.smarthomeandroid.utils.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginSharedPreferences {
    private SharedPreferences sharedPreferences;
    public LoginSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("login",Context.MODE_PRIVATE);
    }
    public void setToken(String token){
        sharedPreferences.edit().putString("token",token).apply();
    }
    public String getToken(){
        return sharedPreferences.getString("token","");
    }
    public void setRefreshToken(String token){
        sharedPreferences.edit().putString("RefreshToken",token).apply();
    }
    public String getRefreshToken(){
        return sharedPreferences.getString("RefreshToken","");
    }

    public void setEmail(String email){
        sharedPreferences.edit().putString("email",email).apply();
    }
    public String getEmail(){
        return sharedPreferences.getString("email","");
    }

    public void setUser(String user){
        sharedPreferences.edit().putString("user",user).apply();
    }
    public String getUser(){
        return sharedPreferences.getString("user","");
    }

    public void dataClear() {
        sharedPreferences.edit().clear().apply();
    }
}
