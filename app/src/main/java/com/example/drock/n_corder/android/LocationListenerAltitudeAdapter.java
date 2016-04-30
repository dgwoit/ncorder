package com.example.drock.n_corder.android;

import android.location.Location;

import com.example.drock.n_corder.units.DistanceUnits;

public class LocationListenerAltitudeAdapter extends LocationListenerMeasurementSourceAdapter {
    public LocationListenerAltitudeAdapter() {
        super("Altitude", DistanceUnits.METERS);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location.hasAltitude()) {
            publishValue((float)location.getAltitude(), location.getTime());
        }
    }
}
