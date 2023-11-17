package com.example.smarthomeandroid.ui.settings.setting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;

import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.api.ApiService;
import com.example.smarthomeandroid.utils.api.soap.request.PhotoRequest;
import com.example.smarthomeandroid.utils.api.soap.response.LoginResponse;
import com.example.smarthomeandroid.utils.api.soap.response.PhotoResponse;
import com.example.smarthomeandroid.utils.api.soap.response.StatusResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class SettingPresenter implements SettingContract.Presenter {
    private SettingContract.View view;
    private ApiService apiService;

    public SettingPresenter(SettingContract.View view) {
        this.view = view;
        apiService = ApiClient.getSmartHomeInstance().getApiService();
    }

    @Override
    public void logout() {
        apiService.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<LoginResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<LoginResponse> loginResponseResponse) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.logoutFailed();
                    }

                    @Override
                    public void onComplete() {
                        view.logoutSuccess();
                    }
                });
    }

    @Override
    public void deleteHome(Long homeId) {

        apiService.deleteHome(homeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StatusResponse>>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<StatusResponse> statusResponseResponse) {
                        if (statusResponseResponse.isSuccessful()) {
                            StatusResponse statusResponse = statusResponseResponse.body();
                            if (statusResponse != null) {
                                view.deleteHomeSuccess();
                            } else {
                                view.deleteHomeFailed();
                            }
                        } else {
                            view.deleteHomeFailed();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void uploadPhoto(String image) {
        PhotoRequest photoRequest = new PhotoRequest();
        photoRequest.photoBase64 = image;
        apiService.uploadPhoto(photoRequest)
                .subscribeOn(Schedulers.io()) // 使用非主線程進行網絡請求
                .observeOn(AndroidSchedulers.mainThread()) // 在主線程處理回應
                .subscribe(new Observer<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // 訂閱
                    }

                    @Override
                    public void onNext(@NonNull Response<StatusResponse> response) {
                        if (response.isSuccessful()) {
                            StatusResponse statusResponse = response.body();
                            if (statusResponse != null) {
                                // 在這裡處理成功回應
                                view.uploadPhotoSuccess();
                            } else {
                                // 伺服器回應不包含有效的 StatusResponse
                                view.uploadPhotoFailed();
                            }
                        } else {
                            // 伺服器回應碼不是成功的範圍（通常是4xx或5xx）
                            view.uploadPhotoFailed();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // 處理錯誤
                        view.uploadPhotoFailed();
                    }

                    @Override
                    public void onComplete() {
                        // 完成
                        getPhoto();
                    }
                });
    }

    @Override
    public void getPhoto() {
        apiService.photoBase64()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Response<PhotoResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Response<PhotoResponse> photoResponseResponse) {

                        if (photoResponseResponse.isSuccessful()) {
                            PhotoResponse photoResponse = photoResponseResponse.body();
                            if (photoResponse != null) {
                                String photo = photoResponse.photoBase64;
                                String base64String = photo;
                                byte[] imageBytes = Base64.decode(base64String, Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.getPhotoSuccess(bitmap);
                                    }
                                });
                            } else {

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
