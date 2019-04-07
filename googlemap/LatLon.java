package com.example.googlemap;

public class LatLon {
    private String _id;
    private Double lat;
    private Double lon;
    private int __v;

    public LatLon(String _id, Double lat, Double lon, int __v) {
        this._id = _id;
        this.lat = lat;
        this.lon = lon;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public int get__v() {
        return __v;
    }
}
