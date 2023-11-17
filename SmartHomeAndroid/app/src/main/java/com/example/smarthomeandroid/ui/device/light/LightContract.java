package com.example.smarthomeandroid.ui.device.light;

public interface LightContract {
    interface View{
        void showLightSensor();
    }
    interface Presenter{
        void getLightSensor();
    }
}
