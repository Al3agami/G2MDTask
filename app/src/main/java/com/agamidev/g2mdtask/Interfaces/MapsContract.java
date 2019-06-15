package com.agamidev.g2mdtask.Interfaces;

import com.google.android.gms.maps.model.LatLng;

public interface MapsContract {
    interface MapsPresneter{
        void getNearbyStores(LatLng latLng);
    }

    interface MapsInteractorInterface{
        void pointNearby(LatLng latLng);

    }
}
