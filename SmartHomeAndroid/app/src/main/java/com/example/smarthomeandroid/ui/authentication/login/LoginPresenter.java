package com.example.smarthomeandroid.ui.authentication.login;

import android.content.Context;
import android.util.Log;

import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.api.ApiService;
import com.example.smarthomeandroid.utils.api.soap.request.LoginRequest;
import com.example.smarthomeandroid.utils.api.soap.response.LoginResponse;
import com.example.smarthomeandroid.utils.sharedpreferences.LoginSharedPreferences;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View view;
    private ApiService apiService;
    private LoginSharedPreferences loginSharedPreferences;
    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        apiService = ApiClient.getSmartHomeInstance().getApiService();
        loginSharedPreferences = new LoginSharedPreferences(context);
    }

    @Override
    public void login(String email, String password) {

        if (email.equals("") || password.equals("")) {
            view.incompleteInput();
            return;
        }
        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        apiService.login(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<LoginResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<LoginResponse> loginResponseResponse) {
                        if (loginResponseResponse.isSuccessful() && loginResponseResponse.body() != null) {
                            Log.e("lee", "onNext: " + loginResponseResponse.body().getStatus());
                            Log.e("lee", "onNext: " + loginResponseResponse.body().getToken());
                            if (loginResponseResponse.body().getStatus().equals("成功")) {
                                loginSharedPreferences.setToken(loginResponseResponse.body().getToken());
                                loginSharedPreferences.setRefreshToken(loginResponseResponse.body().getRefreshToken());
                                loginSharedPreferences.setUser(loginResponseResponse.body().getUserAccount());
                                loginSharedPreferences.setEmail(email);
                                ApiClient.getSmartHomeInstance().setAuthToken(loginResponseResponse.body().getToken(), loginResponseResponse.body().getRefreshToken());
                                view.loginSuccess();
                            } else {
                                view.loginError(loginResponseResponse.body().getError());
                            }
                        }else {
                            Log.e("lee", "無法抓取資料");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("TAG", "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("lee", "onComplete!!!");
                    }
                });
    }
}
