package com.example.smarthomeandroid.utils.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final ApiClient smartHomeInstance = new ApiClient("http://192.168.1.248:8080/api/smarthome/");
    private static final ApiClient weatherInstance = new ApiClient("https://opendata.cwa.gov.tw/api/v1/rest/datastore/");

    private final ApiService mApiService;
    private final TokenInterceptor tokenInterceptor;

    public ApiClient(String baseUrl){
        Gson gson = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        tokenInterceptor = new TokenInterceptor();
        httpClient.addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build();
        mApiService = retrofit.create(ApiService.class);

    }

    public static ApiClient getSmartHomeInstance() {
        return smartHomeInstance;
    }
    public static ApiClient getWeatherInstance() {
        return weatherInstance;
    }

    public ApiService getApiService(){
        return mApiService;
    }

    public void setAuthToken(String token, String refreshToken) {
        tokenInterceptor.setAuthToken(token, refreshToken);
    }
}
