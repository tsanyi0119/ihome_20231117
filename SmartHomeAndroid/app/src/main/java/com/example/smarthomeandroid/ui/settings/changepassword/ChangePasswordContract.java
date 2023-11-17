package com.example.smarthomeandroid.ui.settings.changepassword;

public interface ChangePasswordContract {
    interface View {
        void onChangeSuccess();
        void onChangeError(String message);
    }

    interface Presenter {
        void ChangePassword(String newPassword, String oldPassword);
    }
}
