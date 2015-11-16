package com.example.drock.n_corder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class OnboardSensorActivity extends AppCompatActivity {
    public final static String ANDROID_SENSOR_TYPE = "AndroidSensorType";

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorListener mSensorListener;

    class SensorListener implements SensorEventListener {
        @Override
        public final void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Do something here if sensor accuracy changes.
        }

        @Override
        public final void onSensorChanged(SensorEvent event) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_sensor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        int type = -1;
        if(null != savedInstanceState) {
            type = savedInstanceState.getInt(ANDROID_SENSOR_TYPE);
        } else {
            Intent intent = getIntent();
            type = intent.getIntExtra(ANDROID_SENSOR_TYPE, type);
        }


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(type);
    }

    @Override
    protected void onResume() {
        super.onResume();
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        mSensorManager.registerListener((SensorEventListener)fragment, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FragmentManager fm = getFragmentManager();
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        mSensorManager.unregisterListener((SensorEventListener)fragment);
    }
}
