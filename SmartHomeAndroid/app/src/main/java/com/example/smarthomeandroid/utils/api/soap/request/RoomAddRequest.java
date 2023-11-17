package com.example.smarthomeandroid.utils.api.soap.request;

public class RoomAddRequest {
    private Long homeId;
    private String roomName;
    private String background;

    public RoomAddRequest(Long homeId, String roomName, String background) {
        this.homeId = homeId;
        this.roomName = roomName;
        this.background = background;
    }

    public Long getHomeId() {
        return homeId;
    }

    public void setHomeId(Long homeId) {
        this.homeId = homeId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
