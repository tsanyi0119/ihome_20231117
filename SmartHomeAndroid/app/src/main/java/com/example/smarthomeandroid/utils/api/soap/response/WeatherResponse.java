package com.example.smarthomeandroid.utils.api.soap.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("records")
    public Records records;

    public static class Records {
        @SerializedName("locations")
        public List<Locations> locations;

        public static class Locations{
            @SerializedName("location")
            public List<Location> location;

            public static class Location {
                @SerializedName("weatherElement")
                public List<WeatherElement> weatherElement;

                public static class WeatherElement {
                    @SerializedName("time")
                    public List<Time> time;
                    @SerializedName("elementName")
                    public String elementName;

                    public static class Time {
                        @SerializedName("elementValue")
                        public List<ElementValue> elementValue;

                        public static class ElementValue {
                            @SerializedName("value")
                            public String value;
                        }
                    }
                }
            }
        }
    }
}
