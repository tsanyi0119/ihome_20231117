package com.example.smarthomeandroid.ui.authentication.register.registerfragment;

public interface RegisterFragmentContract {
    interface View{
        void onRegisterSuccess();
        void onRegisterFail();
    }
    interface Presenter{
        void register(String userName,String email, String password, String fcmId);
    }
}
