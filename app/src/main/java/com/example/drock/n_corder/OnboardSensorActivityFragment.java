package com.example.drock.n_corder;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A placeholder fragment containing a simple view.
 */
public class OnboardSensorActivityFragment extends Fragment implements SensorEventListener {
    public OnboardSensorActivityFragment() {
    }

    public static OnboardSensorActivityFragment newInstance() {
        return new OnboardSensorActivityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboard_sensor, container, false);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float value = event.values[0];
        View v = getView();
        TextView tv = (TextView)v.findViewById(R.id.fragmentSensorValue);
        tv.setText(String.format("%f", value));
    }
}
