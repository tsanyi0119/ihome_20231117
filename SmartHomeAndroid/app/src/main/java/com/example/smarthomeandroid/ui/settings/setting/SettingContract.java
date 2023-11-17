package com.example.smarthomeandroid.ui.settings.setting;

import android.graphics.Bitmap;

import okhttp3.MultipartBody;

public interface SettingContract {
    interface View{
        void logoutSuccess();
        void logoutFailed();

        void uploadPhotoFailed();

        void uploadPhotoSuccess();

        void getPhotoSuccess(Bitmap bitmap);

        void deleteHomeSuccess();

        void deleteHomeFailed();
    }

    interface Presenter{
        void logout();
        void uploadPhoto(String image);
        void getPhoto();
        void deleteHome(Long homeId);
    }
}
