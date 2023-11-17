package com.example.smarthomeandroid.ui.authentication.register;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }
}

