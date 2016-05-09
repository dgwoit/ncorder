package com.example.drock.n_corder;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;

import com.example.drock.n_corder.android.LocationListenerLocationAdapter;

public class MeasurementFactory {
    boolean mUseLocationInfo = false;
    LocationReceiver mLocationReceiver;
    LocationListenerLocationAdapter mLocationListener;

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
        /*if(mLocationReceiver != null) {
            return mLocationReceiver.getLocation();
        }*/
        if(mLocationListener != null) {
            return mLocationListener.getLocation();
        }

        return null;
    }

    public void setUseLocationInfo(boolean useLocationInfo) {
        mUseLocationInfo=useLocationInfo;
        LocationManagerHelper helper = SystemFactoryBroker.getSystemFactory().getLocationManagerHelper();
        if(mUseLocationInfo) {
            helper.startLocationUpdates();
            //mLocationReceiver = helper.getLocationReceiver();
            mLocationListener = new LocationListenerLocationAdapter();
            helper.requestLocationUpdates(mLocationListener);
        } else {
            //mLocationReceiver = null;
            if(mLocationListener != null) {
                helper.removeUpdates(mLocationListener);
                mLocationListener = null;
                helper.stopLocationUpdates();
            }
        }
    }
}
