package com.example.drock.n_corder;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by drock on 11/28/2015.
 */
public class AndroidSensorEventAdapter extends MeasurementSource implements SensorEventListener {
    int mValueSelector;

    public AndroidSensorEventAdapter(int valueSelector) {
        mValueSelector = valueSelector;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Measurement m = CreateMeasurement(event);
        update(m);
    }

    protected Measurement CreateMeasurement(SensorEvent event) {
        return new Measurement(event.values[mValueSelector]);
    }
}
