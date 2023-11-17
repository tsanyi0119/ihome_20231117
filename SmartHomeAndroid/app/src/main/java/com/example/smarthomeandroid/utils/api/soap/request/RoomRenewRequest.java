package com.example.smarthomeandroid.utils.api.soap.request;

public class RoomRenewRequest {
    private Long roomId;
    private String roomName;
    private String background;

    public RoomRenewRequest(Long roomId, String roomName, String background) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.background = background;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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
