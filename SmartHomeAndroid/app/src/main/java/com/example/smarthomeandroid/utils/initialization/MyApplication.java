package com.example.smarthomeandroid.utils.initialization;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.example.smarthomeandroid.ui.authentication.login.LoginActivity;
import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.sharedpreferences.LoginSharedPreferences;

public class MyApplication extends Application {
    private static MyApplication instance;
    private LoginSharedPreferences loginSharedPreferences;
    private static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        loginSharedPreferences = new LoginSharedPreferences(this);
        createNotificationChannel();
        instance = this;
        appContext = getApplicationContext();
    }
    public static MyApplication getInstance() {
        return instance;
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                "channel",
                "channel",
                NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public void handleUnauthenticated() {
        // 清除資料跳轉至登入介面
        loginSharedPreferences.dataClear();
        ApiClient.getSmartHomeInstance().setAuthToken(null,null);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public static Context getAppContext() {
        return appContext;
    }
}
