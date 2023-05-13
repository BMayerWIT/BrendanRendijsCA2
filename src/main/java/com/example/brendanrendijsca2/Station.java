package com.example.brendanrendijsca2;

public class Station {
   // id,latitude,longitude,name
    int ID;
    float latitude;
    float longitude;
    String stationName;

    public Station(int ID, float latitude, float longitude, String stationName) {
        this.ID = ID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stationName = stationName;
    }

    public String toString() {
        return ID + ", " + latitude + ", " + longitude + ", " + stationName + "\n";
    }
}
