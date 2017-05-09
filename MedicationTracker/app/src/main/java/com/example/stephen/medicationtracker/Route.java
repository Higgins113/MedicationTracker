package com.example.stephen.medicationtracker;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.xml.datatype.Duration;

/**
 * Created by Stephen on 20-04-2017.
 */
public class Route {
    public String distance1;
    public int distance2;
    public String duration1;
    public int duration2;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;

    public String getDistance1() {
        return distance1;
    }

    public void setDistance1(String distance1) {
        this.distance1 = distance1;
    }

    public int getDistance2() {
        return distance2;
    }

    public void setDistance2(int distance2) {
        this.distance2 = distance2;
    }

    public String getDuration1() {
        return duration1;
    }

    public void setDuration1(String duration1) {
        this.duration1 = duration1;
    }

    public int getDuration2() {
        return duration2;
    }

    public void setDuration2(int duration2) {
        this.duration2 = duration2;
    }
}