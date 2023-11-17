package com.example.smarthomeandroid.utils.api.soap.request;

import java.util.List;

public class RoomDeleteRequest {
    private Long homeId;
    private List<Long> roomIds;

    public RoomDeleteRequest(Long homeId, List<Long> roomIds) {
        this.homeId = homeId;
        this.roomIds = roomIds;
    }

    public Long getHomeId() {
        return homeId;
    }

    public void setHomeId(Long homeId) {
        this.homeId = homeId;
    }

    public List<Long> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<Long> roomIds) {
        this.roomIds = roomIds;
    }
}
