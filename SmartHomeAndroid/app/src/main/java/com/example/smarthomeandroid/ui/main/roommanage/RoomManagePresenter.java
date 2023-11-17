package com.example.smarthomeandroid.ui.main.roommanage;

import android.util.Log;

import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.api.ApiService;
import com.example.smarthomeandroid.utils.api.soap.request.RoomAddRequest;
import com.example.smarthomeandroid.utils.api.soap.response.RoomResponse;
import com.example.smarthomeandroid.utils.api.soap.response.StatusResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class RoomManagePresenter implements RoomManageContract.Presenter{

    private RoomManageContract.View activity;
    private ApiService apiService;

    public RoomManagePresenter(RoomManageContract.View activity) {
        this.activity = activity;
        apiService = ApiClient.getSmartHomeInstance().getApiService();
    }


    @Override
    public void addRoom(Long homeId,String roomName, String roomColor) {
        RoomAddRequest roomAddRequest = new RoomAddRequest(homeId,roomName,roomColor);
        apiService.addRoom(roomAddRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<StatusResponse> statusResponseResponse) {
                        Log.e("20231023", "onNext: " + "新增Room成功");
                        getHomeData(homeId);
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
    public void getHomeData(Long homeId) {
        apiService.roomData(homeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<RoomResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<RoomResponse> roomResponseResponse) {
                        activity.showRoom(roomResponseResponse.body().getRoom());
                        Log.e("20231024", "onNext: " + roomResponseResponse.body().getRoom().toString());
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
