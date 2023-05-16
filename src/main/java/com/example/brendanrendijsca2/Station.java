package com.example.brendanrendijsca2;

import java.util.List;

public class Station {
   // id,latitude,longitude,name
    int ID;
    float latitude;
    float longitude;
    String stationName;
    int X;
    int Y;
    private List<Station> stationsByLineNumber;

    public Station(int ID, float latitude, float longitude, String stationName, int X, int Y) {
        this.ID = ID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stationName = stationName;
        this.X = X;
        this.Y = Y;

    }

    public int getID() {
        return ID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getStationName() {
        return stationName;
    }

    public List<Station> getStationsByLineNumber() {
        return stationsByLineNumber;
    }

    public void setStationsByLineNumber(List<Station> stationsByLineNumber) {
        this.stationsByLineNumber = stationsByLineNumber;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public String toString() {
        return ID + ", " + latitude + ", " + longitude + ", " + stationName + "\n";
    }
}
