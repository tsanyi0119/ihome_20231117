package com.example.smarthomeandroid.ui.main;

import android.util.Log;
import android.widget.Toast;

import com.example.smarthomeandroid.ui.main.item.RoomItem;
import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.api.ApiService;
import com.example.smarthomeandroid.utils.api.soap.request.HomeAddRequest;
import com.example.smarthomeandroid.utils.api.soap.response.DeviceQtyResponse;
import com.example.smarthomeandroid.utils.api.soap.response.HomeResponse;
import com.example.smarthomeandroid.utils.api.soap.response.StatusResponse;
import com.example.smarthomeandroid.utils.api.soap.response.WeatherResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter{
    private final MainContract.View view;
    private ApiService apiServiceWeather;
    private ApiService apiServiceSmartHome;
    private final Map<String, String> countyMap = new HashMap<>();
    private final Map<String, String> weatherElementMap = new HashMap<>();
    public MainPresenter(MainContract.View view) {
        this.view = view;
        setCountyMap();
        apiServiceWeather = ApiClient.getWeatherInstance().getApiService();
        apiServiceSmartHome = ApiClient.getSmartHomeInstance().getApiService();
    }
    @Override
    public void getWeatherData(String locationId, String locationName) {
        if(locationId == null || locationName == null)
            view.setWeatherData("Nan","Nan","Nan","Nan","Nan");
        apiServiceWeather.weather("CWB-29505DAD-7965-40EE-83B4-962CC736EDAD", countyMap.get(locationId), locationName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<WeatherResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<WeatherResponse> weatherResponse) {
                        if(weatherResponse.body() == null) {
                            view.setWeatherData("Nan","Nan","Nan","Nan","Nan");
                        } else {
                            for(int i = 0 ; i < weatherResponse.body().records.locations.get(0).location.get(0).weatherElement.size(); i++) {
                                weatherElementMap.put(weatherResponse.body().records.locations.get(0).location.get(0).weatherElement.get(i).elementName,
                                                      weatherResponse.body().records.locations.get(0).location.get(0).weatherElement.get(i).time.get(0).elementValue.get(0).value);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.setWeatherData("Nan","Nan","Nan","Nan","Nan");
                        Log.e("lee", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.setWeatherData(weatherElementMap.get("Wx"),
                                weatherElementMap.get("T"),
                                weatherElementMap.get("RH"),
                                weatherElementMap.get("WS"),
                                weatherElementMap.get("PoP6h"));
                    }
                });

    }

    @Override
    public void getHomeData() {
        apiServiceSmartHome.homeData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<HomeResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<HomeResponse> homeResponse) {
                        if (homeResponse.isSuccessful()) {
                            Log.e("TAG", "有資料存在");
                            if (homeResponse.body() != null) {
                                List<HomeResponse.HomeData> homeDataList = new ArrayList<>();
                                homeDataList.addAll(homeResponse.body().getHome());
                                view.homeData(homeDataList);
                            }
                        }
                        Log.e("TAG", "沒資料存在");

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
    public void getDeviceQty(Long roomId) {
        apiServiceSmartHome.qtyDevice(roomId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<DeviceQtyResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<DeviceQtyResponse> deviceQtyResponse) {
                        if (deviceQtyResponse.isSuccessful()) {
                            DeviceQtyResponse response = deviceQtyResponse.body();
                            view.DeviceQty(response);
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
    public void addHome(String homeName) {
        HomeAddRequest homeAddRequest = new HomeAddRequest();
        homeAddRequest.setHomeName(homeName);
        apiServiceSmartHome.addHome(homeAddRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<StatusResponse> statusResponseResponse) {
                        if (statusResponseResponse.isSuccessful()) {
                            view.addHomeSuccess();
                        } else {
                            view.addHomeFail();
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

    private void setCountyMap() {
        countyMap.put("宜蘭縣", "F-D0047-001");
        countyMap.put("桃園市", "F-D0047-005");
        countyMap.put("新竹縣", "F-D0047-009");
        countyMap.put("苗栗縣", "F-D0047-013");
        countyMap.put("彰化縣", "F-D0047-017");
        countyMap.put("南投縣", "F-D0047-021");
        countyMap.put("雲林縣", "F-D0047-025");
        countyMap.put("嘉義縣", "F-D0047-029");
        countyMap.put("屏東縣", "F-D0047-033");
        countyMap.put("臺東縣", "F-D0047-037");
        countyMap.put("花蓮縣", "F-D0047-041");
        countyMap.put("澎湖縣", "F-D0047-045");
        countyMap.put("基隆市", "F-D0047-049");
        countyMap.put("新竹市", "F-D0047-053");
        countyMap.put("嘉義市", "F-D0047-057");
        countyMap.put("臺北市", "F-D0047-061");
        countyMap.put("高雄市", "F-D0047-065");
        countyMap.put("新北市", "F-D0047-069");
        countyMap.put("臺中市", "F-D0047-073");
        countyMap.put("臺南市", "F-D0047-077");
        countyMap.put("連江縣", "F-D0047-081");
        countyMap.put("金門縣", "F-D0047-085");
    }
}
