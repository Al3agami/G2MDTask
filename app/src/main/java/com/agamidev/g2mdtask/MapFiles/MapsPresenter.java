package com.agamidev.g2mdtask.MapFiles;

import com.agamidev.g2mdtask.Interfaces.MapsContract;
import com.google.android.gms.maps.model.LatLng;

public class MapsPresenter implements MapsContract.MapsPresneter {
    MapsContract.MapsInteractorInterface mapsInteractorInterface;

    public MapsPresenter(MapsContract.MapsInteractorInterface mapsInteractorInterface){
        this.mapsInteractorInterface = mapsInteractorInterface;
    }

    @Override
    public void getNearbyStores(LatLng latLng) {
        mapsInteractorInterface.pointNearby(latLng);
    }
}
