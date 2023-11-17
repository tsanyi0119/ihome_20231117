package com.example.smarthomeandroid.ui.device.newdevice.connectbluefragment;

import java.util.List;

public interface ConnectBlueFragmentContract {
    interface View{
        void connectBlueDevice(String deviceName);
    }

    interface Adapter{
        void setData(List<String> dataList);
    }
}
