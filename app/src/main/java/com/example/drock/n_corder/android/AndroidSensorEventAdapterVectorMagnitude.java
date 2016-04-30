package com.example.drock.n_corder.android;

import android.hardware.SensorEvent;

import com.example.drock.n_corder.Measurement;

/**
 * Created by drock on 4/16/2016.
 */
public class AndroidSensorEventAdapterVectorMagnitude extends AndroidSensorEventAdapter {
    public AndroidSensorEventAdapterVectorMagnitude(int valueSelector, int unit, String moniker) {
        super(valueSelector, unit, moniker);
    }

    @Override
    protected Measurement createMeasurement(SensorEvent event) {
        if(mValueSelector < event.values.length)
            return super.createMeasurement(event);

        float sumSquare = 0;
        for(float componentValue: event.values) {
            sumSquare += componentValue * componentValue;
        }
        float value = (float)Math.sqrt(sumSquare);
        value = mUnitConverter.convert(mFromUnit, value);
        return mMeasurementFactory.createMeasurement(value, mUnitConverter.getDefaultUnit(), event.timestamp);
    }
}
