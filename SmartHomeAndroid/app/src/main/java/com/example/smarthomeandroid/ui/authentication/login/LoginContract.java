package com.example.smarthomeandroid.ui.authentication.login;

public interface LoginContract {
    interface View {
        void loginError(String error);
        void loginSuccess();
        void incompleteInput();
    }

    interface Presenter {
        void login(String email, String password);
    }
}
