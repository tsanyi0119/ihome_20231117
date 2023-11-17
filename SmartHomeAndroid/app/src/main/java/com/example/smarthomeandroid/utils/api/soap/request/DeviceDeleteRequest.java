package com.example.smarthomeandroid.utils.api.soap.request;

import java.util.List;

public class DeviceDeleteRequest {
    private Long roomId;
    private List<Long> deviceIds;

    public DeviceDeleteRequest(Long roomId, List<Long> deviceIds) {
        this.roomId = roomId;
        this.deviceIds = deviceIds;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<Long> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<Long> deviceIds) {
        this.deviceIds = deviceIds;
    }
}
