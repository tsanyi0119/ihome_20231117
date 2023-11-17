package com.example.smarthomeandroid.utils.bluetooth;

public class BluetoothData {
    private String deviceName;

    private String address;

    public BluetoothData(String deviceName, String address) {
        this.deviceName = deviceName;

        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getDeviceName() {
        return deviceName;
    }
}
