package com.example.smarthomeandroid.ui.device.lock;

import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.api.ApiService;

public class LockPresenter implements LockContract.Presenter{
    private LockContract.View view;
    private ApiService apiService;
    public LockPresenter(LockContract.View view) {
        this.view = view;
        apiService = ApiClient.getSmartHomeInstance().getApiService();
    }

    @Override
    public void getLockSensor() {

    }
}
