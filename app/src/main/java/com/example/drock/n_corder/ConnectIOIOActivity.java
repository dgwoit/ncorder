package com.example.drock.n_corder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.WindowManager;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;


public class ConnectIOIOActivity extends Activity {
    private IOIOAccessService mBoundService;
    private boolean mIsBound = false;
    private static final String IOIO = "IOIO";
    public static final String CONNECTION_ID = "connection-id";
    public static final String SENSOR_NAME = "sensor-name";
    private final String CONNECT_IOIO_ACTIVITY = "ConnectIOIOActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_ioio);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        String connectionId;
        String sensorName;
        if(null != savedInstanceState) {
            connectionId = savedInstanceState.getString(CONNECTION_ID);
            sensorName = savedInstanceState.getString(SENSOR_NAME);
        } else {
            Intent intent = getIntent();
            connectionId = intent.getStringExtra(CONNECTION_ID);
            sensorName = intent.getStringExtra(SENSOR_NAME);
        }

        IOIODeviceDriverManager drvMan = IOIODeviceDriverManager.getInstance();
        drvMan.BeginConnectToDevice(sensorName, connectionId);
//        IOIOConnectionTable connectionInfoTable = new IOIOConnectionTable();
//        IOIOConnectionInfo connectionInfo = connectionInfoTable.getConnectionInfo(connectionId);
//        int basePin = connectionInfo.getPin();
//        AnalogPinReader driver = new AnalogPinReader(basePin);
 //       SensorStreamBroker streamBroker = SensorStreamBroker.getInstance();
 //       streamBroker.RegisterStream(IOIO, driver);
        IOIODeviceDriver senseConnectionDriver = new IOIODeviceDriver() {
            @Override
            public void Realize(ioio.lib.api.IOIO ioio) throws ConnectionLostException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ConnectIOIOActivity.this, ViewTypeActivity.class);
                        intent.putExtra(ParamNames.STREAM_NAME, IOIO);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void Update() {
                //does nothing
            }
        };
        drvMan.AssignDriver(CONNECT_IOIO_ACTIVITY, senseConnectionDriver);
//        drvMan.AssignDriver(connectionId, driver);
        startService(new Intent(this, IOIOAccessService.class));

        doBindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((IOIOAccessService.LocalBinder)service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;
        }
    };

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(ConnectIOIOActivity.this,
                IOIOAccessService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //doBindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //doUnbindService();
    }
}
