package com.example.smarthomeandroid.ui.settings.changepassword;

import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.api.ApiService;
import com.example.smarthomeandroid.utils.api.soap.request.ResetPasswordRequest;
import com.example.smarthomeandroid.utils.api.soap.response.StatusResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {
    private ChangePasswordContract.View view;
    private ApiService apiService;
    public ChangePasswordPresenter(ChangePasswordContract.View view){
        this.view = view;
        apiService = ApiClient.getSmartHomeInstance().getApiService();
    }
    @Override
    public void ChangePassword(String newPassword, String oldPassword) {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(oldPassword,newPassword);
        apiService.resetPassword(resetPasswordRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<StatusResponse> statusResponseResponse) {
                        if (statusResponseResponse.isSuccessful()) {
                            StatusResponse response = statusResponseResponse.body();
                            if (response != null) {
                                // 根据 status 属性的值执行不同的操作
                                if (response.getStatus().equals("成功")) {
                                    view.onChangeSuccess();
                                } else if (response.getError() != null) {
                                    view.onChangeError(response.getError());
                                }
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
