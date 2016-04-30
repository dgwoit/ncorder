package com.example.drock.n_corder.android;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationListenerLocationAdapter implements LocationListener {
    Location mLocation;

    public Location getLocation() {
        synchronized (this) {
            return mLocation;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        synchronized (this) {
            mLocation = location;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        synchronized (this) {
            mLocation = null;
        }
    }
}
