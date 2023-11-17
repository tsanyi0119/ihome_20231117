package com.example.smarthomeandroid.utils.api.soap.request;

public class HomeReNewRequest {
    private Long homeId;
    private String homeName;
    private String address;
    private String background;

    public HomeReNewRequest(Long homeId, String homeName, String address, String background) {
        this.homeId = homeId;
        this.homeName = homeName;
        this.address = address;
        this.background = background;
    }

    public Long getHomeId() {
        return homeId;
    }

    public void setHomeId(Long homeId) {
        this.homeId = homeId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
