package com.example.chatbot;

import com.google.android.gms.maps.model.LatLng;

public class DistrictData {

    private String addr;
    private int tnc ;
    private LatLng latLng;

    public DistrictData(String addr, int tnc, LatLng latLng) {
        this.addr = addr;
        this.tnc = tnc;
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getAddr() {
        return addr;
    }

    public int getTnc() {
        return tnc;
    }

}
