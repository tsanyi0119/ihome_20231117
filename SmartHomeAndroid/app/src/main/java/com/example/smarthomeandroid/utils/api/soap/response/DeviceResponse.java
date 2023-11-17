package com.example.smarthomeandroid.utils.api.soap.response;

public class DeviceResponse {
    private Long id;
    public String deviceName;
    public String deviceType;
    public Boolean state;
    public String serviceSetID;

    public DeviceResponse(Long id, String deviceName, String deviceType, Boolean state, String serviceSetID) {
        this.id = id;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.state = state;
        this.serviceSetID = serviceSetID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getServiceSetID() {
        return serviceSetID;
    }

    public void setServiceSetID(String serviceSetID) {
        this.serviceSetID = serviceSetID;
    }
}
