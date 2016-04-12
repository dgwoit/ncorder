package com.example.drock.n_corder;

import android.content.Context;
import android.location.Location;

public class MeasurementFactory {
    boolean mUseLocationInfo = false;
    LocationReceiver mLocationReceiver;

    public Measurement createMeasurement(float value, int unit) {
        return createMeasurement(value, unit, System.nanoTime());
    }

    public Measurement createMeasurement(float value, int unit, long timestamp) {
        Measurement m = new Measurement(value, timestamp, unit);
        if(mUseLocationInfo) {
            Location location = getLocation();
            m.setLocation(location);
        }
        return m;
    }

    protected Location getLocation() {
        if(mLocationReceiver != null) {
            mLocationReceiver.getLocation();
        }

        return null;
    }

    public void setUseLocationInfo(boolean useLocationInfo) {
        mUseLocationInfo=useLocationInfo;
        LocationManagerHelper helper = SystemFactoryBroker.getSystemFactory().getLocationManagerHelper();
        if(mUseLocationInfo) {
            mLocationReceiver = new LocationReceiver();
            helper.startLocationUpdates();
        } else {
            helper.stopLocationUpdates();
            mLocationReceiver = null;
        }
    }
}
