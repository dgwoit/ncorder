package com.example.drock.n_corder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.drock.n_corder.dummy.DummyContent;

public class IOIOSensorConfigurationActivity extends AppCompatActivity implements IOIOSensorListFragment.OnListFragmentInteractionListener {
    public static final String CONNECTION_ID = "connection-id";

    protected String mConnectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ioiosensor_configuration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(null != savedInstanceState) {
            mConnectionId = savedInstanceState.getString(CONNECTION_ID);
        } else {
            Intent intent = getIntent();
            mConnectionId = intent.getStringExtra(CONNECTION_ID);
        }

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if(null ==  fragment) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }

    protected android.support.v4.app.Fragment createFragment() {
        return IOIOSensorListFragment.newInstance(mConnectionId);
    }

    @Override
    public void onListFragmentInteraction(Object object) {
        Intent intent = new Intent(this, ConnectIOIOActivity.class);
        IOIODeviceDriverInfo driverInfo = (IOIODeviceDriverInfo)object;
        intent.putExtra(ConnectIOIOActivity.CONNECTION_ID, mConnectionId);
        intent.putExtra(ConnectIOIOActivity.SENSOR_NAME, driverInfo.mName);
        startActivity(intent);
    }
}
