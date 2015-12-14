package com.example.drock.n_corder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.*;
import android.util.Log;
import android.view.View;

public class AndroidSensorListActivity extends AppCompatActivity implements AndroidSensorListFragment.OnFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_sensor_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if(null ==  fragment) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    protected Fragment createFragment() {
        return new AndroidSensorListFragment();
    }




    @Override
    public void onFragmentInteraction(int type) {
        Intent intent = new Intent(this, AndroidSensorConfigurationActivity.class);
        intent.putExtra(AndroidSensorConfigurationActivity.SENSOR_TYPE, type);
        startActivity(intent);
    }
}
