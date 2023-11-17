package com.example.smarthomeandroid.utils.api.soap.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HomeResponse {
    @SerializedName("Home")
    private List<HomeData> home;
    public static class HomeData{
        private Long id;
        private String homeName;
        private String address;
        private String background;
        private List<Room> rooms;
        public static class Room{
            private Long id;
            private String roomName;
            private String background;

            public Room(Long id, String roomName, String background) {
                this.id = id;
                this.roomName = roomName;
                this.background = background;
            }

            public Long getId() {
                return id;
            }

            public String getRoomName() {
                return roomName;
            }

            public String getBackground() {
                return background;
            }
        }

        public HomeData(Long id, String homeName, String address, String background, List<Room> rooms) {
            this.id = id;
            this.homeName = homeName;
            this.address = address;
            this.background = background;
            this.rooms = rooms;
        }

        public Long getId() {
            return id;
        }

        public String getHomeName() {
            return homeName;
        }

        public String getAddress() {
            return address;
        }

        public String getBackground() {
            return background;
        }

        public List<Room> getRooms() {
            return rooms;
        }
    }

    public HomeResponse(List<HomeData> home) {
        this.home = home;
    }

    public List<HomeData> getHome() {
        return home;
    }
}
