package com.example.smarthomeandroid.utils.api;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;


import com.example.smarthomeandroid.utils.api.soap.response.LoginResponse;
import com.example.smarthomeandroid.utils.initialization.MyApplication;
import com.example.smarthomeandroid.utils.sharedpreferences.LoginSharedPreferences;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TokenInterceptor implements Interceptor {
    private LoginSharedPreferences loginSharedPreferences;
    private ApiService mApiService;
    private static final String TAG = "hank";
    private String authToken;   //存放認證Token
    private String refreshToken;    //存放刷新令牌
    private boolean refreshingToken = false;    // 防止請求刷新令牌API出現無限輪迴
    private boolean shouldContinueWithRequests = true; // 刷新令牌過期時防止其他API請求


    public TokenInterceptor() {
        Context context = MyApplication.getAppContext();
        loginSharedPreferences = new LoginSharedPreferences(context);
    }

    public void setAuthToken(String token, String refresh) {
        authToken = token;
        refreshToken = refresh;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (mApiService == null) {
            // 初始化 ApiService，只執行第一次
            mApiService = ApiClient.getSmartHomeInstance().getApiService();
        }

        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();

        if (authToken != null) {
            requestBuilder.header("Authorization", "Bearer " + authToken);
        }

        if (isTokenExpired()) { // 才看token有沒有過期，ture表示過期
            // 令牌已過期時換上刷新令牌
            requestBuilder.header("Authorization", "Bearer " + refreshToken);

            //請求刷新令牌API
            CompletableFuture<String> newTokenFuture = refreshTokenAsync();
            try {
                String newToken = newTokenFuture.get();
                if (newToken != null) {
                    if (newToken.equals("Token失效，請重新申請")) {
                        // 刷新令牌過期時跳轉到登入畫面
                        MyApplication.getInstance().handleUnauthenticated();
                        shouldContinueWithRequests = false; // 設置為false，表示不再繼續其他API請求
                    } else if (newToken.equals("重新登入")) {
                        requestBuilder.removeHeader("Authorization");
                    } else {
                        // 換上由刷新令牌所換來的認證Token
                        requestBuilder.header("Authorization", "Bearer " + newToken);
                        //將新的token存起
                        loginSharedPreferences.setToken(newToken);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                // 異常處理
                Log.e(TAG, "刷新令牌失败: " + e.getMessage());
            }
        }
        if (shouldContinueWithRequests) {
            Request request = requestBuilder.build();
            return chain.proceed(request);
        } else {
            //回復使用API請求
            shouldContinueWithRequests = true;
            // 返回一個空的API Response
            return new Response.Builder()
                    .code(401)
                    .message("Unauthorized")
                    .request(original)
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(MediaType.parse("application/json"), "Unauthorized"))
                    .build();
        }
    }

    private CompletableFuture<String> refreshTokenAsync() {
        // 防止無限輪迴
        if (refreshingToken) {
            return CompletableFuture.completedFuture(null);
        }
        refreshingToken = true;

        CompletableFuture<String> future = new CompletableFuture<>();

        mApiService.refreshToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    refreshingToken = false;

                    if (response.isSuccessful()) {
                        LoginResponse body = response.body();
                        if (body != null) {
                            if (body.getStatus().equals("成功")) {
                                future.complete(body.getToken());
                            } else if (body.getStatus().equals("FAILED")) {
                                Log.e(TAG, "刷新令牌错误: " + body.getError());
                                future.complete("Token失效，請重新申請");
                            }
                        }
                    }
                }, throwable -> {
                    refreshingToken = false;
                    future.completeExceptionally(throwable);
                });

        return future;
    }

    private boolean isTokenExpired() {
        if (authToken == null){
            // 沒代入token視為無過期，讓登入時能通過
            return false;
        }
        // 解析JWT令牌，查看token是否過期
        try {
            String[] tokenParts = authToken.split("\\."); // JWT通常由三部分组成
            String payload = tokenParts[1];
            byte[] decodedPayload = Base64.decode(payload, Base64.DEFAULT);
            String payloadJson = new String(decodedPayload, StandardCharsets.UTF_8);

            JSONObject payloadObj = new JSONObject(payloadJson);
            long expirationTime = payloadObj.getLong("exp");
            long currentTime = System.currentTimeMillis() / 1000;
            // 如果有效期小于当前时间，令牌过期
            return expirationTime < currentTime;
        } catch (Exception e) {
            Log.e(TAG, "isTokenExpired: " + e.getMessage());
            return true;
        }
    }
}
