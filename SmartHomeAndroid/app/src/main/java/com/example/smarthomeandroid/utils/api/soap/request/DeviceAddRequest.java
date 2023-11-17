package com.example.smarthomeandroid.utils.api.soap.request;

public class DeviceAddRequest {
    private Long roomId;
    private String deviceName;
    private String deviceType;
    private String serviceSetID;
    private String password;
    private Boolean state;

    public DeviceAddRequest(Long roomId, String deviceName, String deviceType, String serviceSetID, String password, Boolean state) {
        this.roomId = roomId;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.serviceSetID = serviceSetID;
        this.password = password;
        this.state = state;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getServiceSetID() {
        return serviceSetID;
    }

    public void setServiceSetID(String serviceSetID) {
        this.serviceSetID = serviceSetID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
