package com.example.smarthomeandroid.utils.api.soap.response;

public class DeviceQtyResponse {
    private Long light;
    private Long environment;
    private Long monitor;
    private Long lock;

    public DeviceQtyResponse(Long light, Long environment, Long monitor, Long lock) {
        this.light = light;
        this.environment = environment;
        this.monitor = monitor;
        this.lock = lock;
    }

    public Long getLight() {
        return light;
    }

    public Long getEnvironment() {
        return environment;
    }

    public Long getMonitor() {
        return monitor;
    }

    public Long getLock() {
        return lock;
    }
}
