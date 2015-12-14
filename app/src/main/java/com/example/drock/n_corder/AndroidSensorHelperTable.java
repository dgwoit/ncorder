package com.example.drock.n_corder;

import android.hardware.Sensor;

/**
 * Created by drock on 12/5/2015.
 */
public class AndroidSensorHelperTable {
    private AndroidSensorHelper[] mHelpers;

    AndroidSensorHelperTable() {
        Init();
    }

    void Init() {
        String[] xyz = {"x", "y", "z"};
        mHelpers = new AndroidSensorHelper[] {
                new AndroidSensorHelper(Sensor.TYPE_ACCELEROMETER, new String[]{"x acceleration", "y acceleration", "z acceleration"}),
                new AndroidSensorHelper(Sensor.TYPE_AMBIENT_TEMPERATURE, new String[]{"ambient temperature"}),
                new AndroidSensorHelper(Sensor.TYPE_GAME_ROTATION_VECTOR, new String[]{"rotation vector x", "rotation vector y", "rotation vector z"}),
                new AndroidSensorHelper(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, new String[]{"x", "y", "z"}),
                new AndroidSensorHelper(Sensor.TYPE_GRAVITY, new String[]{"x", "y", "z"}),
                new AndroidSensorHelper(Sensor.TYPE_GYROSCOPE, new String[]{"x", "y", "z"}),
                new AndroidSensorHelper(Sensor.TYPE_GYROSCOPE_UNCALIBRATED, xyz),
                new AndroidSensorHelper(Sensor.TYPE_HEART_RATE, xyz),
                new AndroidSensorHelper(Sensor.TYPE_LIGHT, new String[]{"ambient light level"}),
                new AndroidSensorHelper(Sensor.TYPE_LINEAR_ACCELERATION, xyz),
                new AndroidSensorHelper(Sensor.TYPE_MAGNETIC_FIELD, xyz),
                new AndroidSensorHelper(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, new String[]{"x uncalibrated", "y uncalibrated", "z uncalibrated", "x bias", "y bias", "z bias"}),
                new AndroidSensorHelper(Sensor.TYPE_ORIENTATION, xyz),
                new AndroidSensorHelper(Sensor.TYPE_PRESSURE, new String[]{"pressure"}),
                new AndroidSensorHelper(Sensor.TYPE_PROXIMITY, new String[]{"proximity"}),
                new AndroidSensorHelper(Sensor.TYPE_RELATIVE_HUMIDITY, new String[]{"humidity"}),
                new AndroidSensorHelper(Sensor.TYPE_ROTATION_VECTOR, xyz),
                new AndroidSensorHelper(Sensor.TYPE_SIGNIFICANT_MOTION, new String[]{"significant motion"}),
                new AndroidSensorHelper(Sensor.TYPE_STEP_COUNTER, new String[]{"steps"}),
        };
    }

    AndroidSensorHelper getEntry(int sensorType) {
        for(AndroidSensorHelper helper : mHelpers) {
            if(helper.getType() == sensorType)
                return helper;
        }

        return null;
    }
}
