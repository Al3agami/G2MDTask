package com.agamidev.g2mdtask.MapFiles;

import android.util.Log;

import com.agamidev.g2mdtask.Interfaces.MapsContract;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class MapsInteractorImp implements MapsContract.MapsInteractorInterface {

    private int PROXIMITY_RADIUS = 10000;
    GoogleMap mMap;

    public MapsInteractorImp(GoogleMap mMap){
        this.mMap = mMap;
    }

    @Override
    public void pointNearby(LatLng latLng) {
        mMap.clear();
        String url = getUrl(latLng.latitude, latLng.longitude, "restaurant");
        Object[] DataTransfer = new Object[2];
        DataTransfer[0] = mMap;
        DataTransfer[1] = url;
        Log.d("onClick", url);
        GetNearbyStoresData getNearbyPlacesData = new GetNearbyStoresData();
        getNearbyPlacesData.execute(DataTransfer);
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

}
