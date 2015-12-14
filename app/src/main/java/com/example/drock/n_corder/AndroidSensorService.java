package com.example.drock.n_corder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

import com.example.drock.n_corder.IOIOAccessService;

public class AndroidSensorService extends Service{
    public   AndroidSensorService(){
    }

    public class LocalBinder extends Binder {
        AndroidSensorService getService() {
            return AndroidSensorService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    public void BindSensor(int type, int valueSelector) {
        AndroidSensorEventAdapter adapter = new AndroidSensorEventAdapter(valueSelector);
        SensorStreamBroker.getInstance().RegisterStream("Android", adapter);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(type);
        sensorManager.registerListener((SensorEventListener)adapter, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
