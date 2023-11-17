package com.example.smarthomeandroid.ui.authentication.register.registerverifyfragment;

public interface RegisterVerifyFragmentContract {
    interface View{
        void registerVerifySuccess();
        void registerVerifyFail(String message);
    }
    interface Presenter{
        void registerVerify(String code);
    }
}
