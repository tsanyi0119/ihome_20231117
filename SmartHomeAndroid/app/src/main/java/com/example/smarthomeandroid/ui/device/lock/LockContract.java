package com.example.smarthomeandroid.ui.device.lock;

public interface LockContract {
    interface View{
        void showLockSensor();
    }
    interface Presenter{
        void getLockSensor();
    }
}
