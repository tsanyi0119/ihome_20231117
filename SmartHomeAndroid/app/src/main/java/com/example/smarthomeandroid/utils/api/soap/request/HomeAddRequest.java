package com.example.smarthomeandroid.utils.api.soap.request;

public class HomeAddRequest {
    private String homeName;
    private String address;
    private String background;
    private Rooms rooms;
    public static class Rooms{
        private String roomName;
        private String background;

        public Rooms(String roomName, String background) {
            this.roomName = roomName;
            this.background = background;
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

    public HomeAddRequest(String homeName, String address, String background, Rooms rooms) {
        this.homeName = homeName;
        this.address = address;
        this.background = background;
        this.rooms = rooms;
    }

    public HomeAddRequest(){
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

    public Rooms getRooms() {
        return rooms;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }
}
