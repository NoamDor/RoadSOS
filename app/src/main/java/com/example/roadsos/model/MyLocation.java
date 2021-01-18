package com.example.roadsos.model;

import java.io.Serializable;

public class MyLocation implements Serializable {
    public double latitude;
    public double longitude;
    public String address;

    public MyLocation(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public MyLocation() {

    }
}
