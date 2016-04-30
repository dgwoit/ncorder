package com.example.drock.n_corder.android;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.example.drock.n_corder.DisplayUnitManager;
import com.example.drock.n_corder.Measurement;
import com.example.drock.n_corder.MeasurementFactory;
import com.example.drock.n_corder.MeasurementSource;
import com.example.drock.n_corder.SystemFactory;
import com.example.drock.n_corder.SystemFactoryBroker;
import com.example.drock.n_corder.units.TimeUnits;
import com.example.drock.n_corder.units.UnitConverter;
import com.example.drock.n_corder.units.UnitSystemInfo;
import com.example.drock.n_corder.units.Units;

public abstract class LocationListenerMeasurementSourceAdapter extends MeasurementSource implements LocationListener {
    MeasurementFactory mMeasurementFactory;
    UnitConverter mUnitConverter;
    int mFromUnit;
    String mMoniker;

    public  LocationListenerMeasurementSourceAdapter(String moniker, int unit) {
        mMeasurementFactory = SystemFactoryBroker.getSystemFactory().getMeasurementFactory();
        mUnitConverter = SystemFactoryBroker.getSystemFactory().getUnitConverterFactory().createUnitConverter(unit);
        mFromUnit = unit;
        mMoniker = moniker;
    }

    protected void publishValue(float value, long locationTime) {
        value  = mUnitConverter.convert(mFromUnit, value); //convert to system units
        int systemUnit = mUnitConverter.getDefaultUnit();
        Measurement measurement = mMeasurementFactory.createMeasurement(value, systemUnit, locationTime * TimeUnits.NSEC_PER_MSEC);
        update(measurement);
    }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

    }

        @Override
        public void onProviderEnabled(String provider) {

    }

        @Override
        public void onProviderDisabled(String provider) {

    }

    public String getMoniker() {
        return mMoniker;
    }
}
