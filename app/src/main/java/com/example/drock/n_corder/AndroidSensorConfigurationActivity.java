package com.example.drock.n_corder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AndroidSensorConfigurationActivity extends AppCompatActivity implements AndroidSensorDetailFragment.OnListFragmentInteractionListener {
    public static final String SENSOR_TYPE = "SENSOR_TYPE";
    private int mSensorType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_sensor_configuration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(null != savedInstanceState) {
            mSensorType = savedInstanceState.getInt(SENSOR_TYPE);
        } else {
            Intent intent = getIntent();
            mSensorType = intent.getIntExtra(SENSOR_TYPE, 0);
        }

        //android sensor service
        startService(new Intent(this, AndroidSensorService.class));
        doBindService();

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if(null ==  fragment) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }

    protected Fragment createFragment() {
        return AndroidSensorDetailFragment.newInstance(mSensorType);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    //Android Sensor Service support
    private AndroidSensorService mBoundService;
    private boolean mIsBound = false;

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(AndroidSensorConfigurationActivity.this,
                AndroidSensorService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((AndroidSensorService.LocalBinder)service).getService();

        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;
        }
    };

    @Override
    public void onListFragmentInteraction(AndroidSensorDetailFragment.ListItem item) {
        mBoundService.BindSensor(item.getSensorType(), item.getValueSelector());

        Intent intent = new Intent(this, DataViewActivity.class);
        intent.putExtra(DataViewActivity.STREAM_NAME, "Android");
        startActivity(intent);
    }
}
