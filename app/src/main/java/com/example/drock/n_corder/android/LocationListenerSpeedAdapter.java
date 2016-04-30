package com.example.drock.n_corder.android;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.example.drock.n_corder.IMeasurementSink;
import com.example.drock.n_corder.IMeasurementSource;
import com.example.drock.n_corder.Measurement;
import com.example.drock.n_corder.MeasurementFactory;
import com.example.drock.n_corder.MeasurementSource;
import com.example.drock.n_corder.SystemFactoryBroker;
import com.example.drock.n_corder.units.TimeUnits;
import com.example.drock.n_corder.units.Units;

public class LocationListenerSpeedAdapter extends LocationListenerMeasurementSourceAdapter {

    LocationListenerSpeedAdapter() {
        super("Ground Speed", Units.SPEED);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location.hasSpeed()) {
            float speed = location.getSpeed(); //meters per second
            publishValue(speed, location.getTime());
        }
    }
}
