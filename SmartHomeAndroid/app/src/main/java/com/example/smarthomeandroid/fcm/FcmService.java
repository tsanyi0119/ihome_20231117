package com.example.smarthomeandroid.fcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.authentication.login.LoginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FcmService extends FirebaseMessagingService{
    private static final String TAG = "FirebaseMessagingService";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Log.e(TAG, "onMessageReceived: 有接收到訊息" );
        //訊息接收及處理
        String notificationTitle = message.getNotification().getTitle();
        String notificationBody = message.getNotification().getBody();
        String imageUrl = String.valueOf(message.getNotification().getImageUrl());
        Bitmap bitmap = getBitmapFromUrl(imageUrl);
        Log.e(TAG, imageUrl);
        showNotification(notificationTitle, notificationBody, bitmap);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //將token列印出來
        Log.e(TAG, "此裝置的token : " + token);
        saveTokenToSharedPreferences(token);
        //這裡要寫當token出現時請求"api/smarthome/fcm/save"這支API將token存在資料庫
    }

    private void saveTokenToSharedPreferences(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("fcm", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fcmId", token);
        editor.apply();
    }

    //發送通知
    private void showNotification(String title, String body, Bitmap imageUrl) {
        long currentTimeMillis = System.currentTimeMillis();
        int notificationId = (int) currentTimeMillis;
        //點擊跳頁設定
        Intent openIntent = new Intent(this, LoginActivity.class);
        PendingIntent openPendingIntent = PendingIntent.getActivity(this, 1, openIntent, PendingIntent.FLAG_MUTABLE);
        // 創建通知
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel")
                .setSmallIcon(R.drawable.notification_small_icon)
                .setLargeIcon(imageUrl)
                .setContentTitle(title)
                .setContentText(body)
                //通知被使用者點擊後是否清除
                .setAutoCancel(true)
                //設置通知等級
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //設置通知類型
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(openPendingIntent);
        // 發送通知
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            // 讀取圖片數據
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            // 關閉連接和輸入流
            input.close();
            connection.disconnect();

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            // 錯誤處理，返回默認的圖片或者 null
            return null;
        }
    }
}
