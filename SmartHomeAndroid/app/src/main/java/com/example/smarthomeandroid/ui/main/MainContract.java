package com.example.smarthomeandroid.ui.main;

import com.example.smarthomeandroid.ui.main.item.RoomItem;
import com.example.smarthomeandroid.utils.api.soap.response.DeviceQtyResponse;
import com.example.smarthomeandroid.utils.api.soap.response.HomeResponse;

import java.util.List;

public interface MainContract {
    interface Presenter{
        void getWeatherData(String locationId, String locationName);
        void getHomeData();
        void getDeviceQty(Long roomId);
        void addHome(String homeName);
    }
    interface View {
        void setWeatherData(String wx, String t, String rh, String ws, String pop6h);
        void homeData(List<HomeResponse.HomeData> homeDataList);
        void DeviceQty(DeviceQtyResponse response);
        void addHomeSuccess();
        void addHomeFail();
    }
    interface Adapter {
        void setRoomList(List<HomeResponse.HomeData.Room> roomList);
    }

}
