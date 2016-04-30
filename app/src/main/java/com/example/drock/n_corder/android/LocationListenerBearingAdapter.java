package com.example.drock.n_corder.android;

import android.location.Location;

import com.example.drock.n_corder.units.AngleUnits;

public class LocationListenerBearingAdapter extends LocationListenerMeasurementSourceAdapter {
    public LocationListenerBearingAdapter() {
        super("Bearing", AngleUnits.DEGREES);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location.hasBearing()) {
            publishValue(location.getBearing(), location.getTime());
        }
    }
}
