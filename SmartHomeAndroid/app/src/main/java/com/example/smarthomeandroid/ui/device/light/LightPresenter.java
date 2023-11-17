package com.example.smarthomeandroid.ui.device.light;

import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.api.ApiService;

public class LightPresenter implements LightContract.Presenter{
    private LightContract.View view;
    private ApiService apiService;
    public LightPresenter(LightContract.View view) {
        this.view = view;
        apiService = ApiClient.getSmartHomeInstance().getApiService();
    }

    @Override
    public void getLightSensor() {

    }
}
