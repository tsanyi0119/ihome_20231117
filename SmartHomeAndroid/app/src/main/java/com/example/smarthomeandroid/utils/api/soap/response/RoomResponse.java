package com.example.smarthomeandroid.utils.api.soap.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomResponse {
    @SerializedName("Room")
    private List<RoomData> room;
    public static class RoomData{
        private Long id;
        private String roomName;
        private String background;
        private List<DevicesData> devices;
        public static class DevicesData{
            private Long id;
            private String deviceName;
            private String deviceType;
            private Boolean state;
            private String serviceSetID;

            public DevicesData(Long id, String deviceName, String deviceType, Boolean state, String serviceSetID) {
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

        public RoomData(Long id, String roomName, String background, List<DevicesData> devices) {
            this.id = id;
            this.roomName = roomName;
            this.background = background;
            this.devices = devices;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public List<DevicesData> getDevices() {
            return devices;
        }

        public void setDevices(List<DevicesData> devices) {
            this.devices = devices;
        }
    }

    public List<RoomData> getRoom() {
        return room;
    }
}
