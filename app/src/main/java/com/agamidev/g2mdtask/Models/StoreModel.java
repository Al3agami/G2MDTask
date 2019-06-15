package com.agamidev.g2mdtask.Models;

import com.google.android.gms.maps.model.LatLng;

public class StoreModel {
    private String placeName;
    private String vicinity;
    private LatLng latLng;

    public StoreModel(String placeName, String vicinity, LatLng latLng){
        this.placeName = placeName;
        this.vicinity = vicinity;
        this.latLng = latLng;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }
}
