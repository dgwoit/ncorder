package com.example.drock.n_corder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class IOIOConfigureConnectionActivity extends AppCompatActivity implements IOIOConnectionsListFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ioioconfigure_connection);
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

    protected android.support.v4.app.Fragment createFragment() {
        return new IOIOConnectionsListFragment();
    }

    @Override
    public void onListFragmentInteraction(Object object) {
        Intent intent = new Intent(this, IOIOSensorConfigurationActivity.class);
        intent.putExtra(IOIOSensorConfigurationActivity.CONNECTION_ID, ((IOIOConnectionInfo)object).getName());
        startActivity(intent);
    }
}
