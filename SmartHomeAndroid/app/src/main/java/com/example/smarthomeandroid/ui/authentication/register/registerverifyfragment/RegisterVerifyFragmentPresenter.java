package com.example.smarthomeandroid.ui.authentication.register.registerverifyfragment;


import android.content.Context;
import android.util.Log;

import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.api.ApiService;
import com.example.smarthomeandroid.utils.api.soap.request.RegisterVerifyRequest;
import com.example.smarthomeandroid.utils.api.soap.response.RegisterVerifyResponse;
import com.example.smarthomeandroid.utils.sharedpreferences.LoginSharedPreferences;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class RegisterVerifyFragmentPresenter implements RegisterVerifyFragmentContract.Presenter {
    private RegisterVerifyFragmentContract.View view;
    private ApiService apiService;
    private LoginSharedPreferences loginSharedPreferences;

    public RegisterVerifyFragmentPresenter(RegisterVerifyFragmentContract.View view, Context context) {
        this.view = view;
        apiService = ApiClient.getSmartHomeInstance().getApiService();
        loginSharedPreferences = new LoginSharedPreferences(context);
    }

    @Override
    public void registerVerify(String code) {
        RegisterVerifyRequest request = new RegisterVerifyRequest();
        request.setCode(code);
        request.setEmail(loginSharedPreferences.getEmail());
        Log.e("lee", request.getEmail());
        apiService.registerVerify(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<RegisterVerifyResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<RegisterVerifyResponse> registerVerifyResponseResponse) {

                        if (registerVerifyResponseResponse.body() != null && registerVerifyResponseResponse.body().getStatus().equals("成功")){
                            view.registerVerifySuccess();
                        }else if (registerVerifyResponseResponse.body() != null && registerVerifyResponseResponse.body().getStatus().equals("失敗")){
                            view.registerVerifyFail("驗證碼失敗");
                        }else {
                            view.registerVerifyFail("驗證碼失敗");
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.registerVerifyFail("驗證碼失敗");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
