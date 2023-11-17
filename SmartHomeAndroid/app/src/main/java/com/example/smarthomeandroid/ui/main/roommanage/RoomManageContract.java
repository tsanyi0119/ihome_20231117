package com.example.smarthomeandroid.ui.main.roommanage;

import com.example.smarthomeandroid.utils.api.soap.response.RoomResponse;

import java.util.List;

public interface RoomManageContract {
    interface View{
        void showRoom(List<RoomResponse.RoomData> roomDataList);
    }
    interface Presenter{
        void addRoom(Long homeId, String roomName, String roomColor);
        void getHomeData(Long homeId);
    }
}
