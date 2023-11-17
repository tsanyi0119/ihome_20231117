package com.example.smarthomeandroid.ui.authentication.register.registerfragment;

import android.content.Context;
import android.util.Log;

import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.api.ApiService;
import com.example.smarthomeandroid.utils.api.soap.request.RegisterRequest;
import com.example.smarthomeandroid.utils.api.soap.response.RegisterResponse;
import com.example.smarthomeandroid.utils.sharedpreferences.LoginSharedPreferences;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class RegisterFragmentPresenter implements RegisterFragmentContract.Presenter{
    private RegisterFragmentContract.View view;
    private ApiService apiService;
    private LoginSharedPreferences loginSharedPreferences;
    public RegisterFragmentPresenter(RegisterFragmentContract.View view, Context context) {
        this.view = view;
        apiService = ApiClient.getSmartHomeInstance().getApiService();
        loginSharedPreferences = new LoginSharedPreferences(context);
    }

    @Override
    public void register(String userName, String email, String password, String fcmId) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserAccount(userName);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setFcmId(fcmId);

        apiService.register(registerRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<RegisterResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<RegisterResponse> registerResponseResponse) {
                        if (registerResponseResponse.isSuccessful()) {
                            assert registerResponseResponse.body() != null;
                            if (registerResponseResponse.body().getError() != null) {
                                view.onRegisterFail();
                            }else {
                                view.onRegisterSuccess();
                                loginSharedPreferences.setEmail(email);
                            }
                        }else {
                            view.onRegisterFail();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.onRegisterFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
