/*
* THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.example.drock.n_corder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.WindowManager;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;


public class ConnectIOIOActivity extends Activity {
    private IOIOAccessService mBoundService;
    private boolean mIsBound = false;
    private static final String IOIO = "IOIO";
    public static final String CONNECTION_ID = "connection-id";
    public static final String SENSOR_NAME = "sensor-name";
    private final String CONNECT_IOIO_ACTIVITY = "ConnectIOIOActivity";
    String mConnectionId;
    String mSensorName;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_ioio);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (null != savedInstanceState) {
            mConnectionId = savedInstanceState.getString(CONNECTION_ID);
            mSensorName = savedInstanceState.getString(SENSOR_NAME);
        } else {
            Intent intent = getIntent();
            mConnectionId = intent.getStringExtra(CONNECTION_ID);
            mSensorName = intent.getStringExtra(SENSOR_NAME);
            assert mConnectionId != null;
            assert mSensorName != null;
        }

        IOIODeviceDriverManager drvMan = IOIODeviceDriverManager.getInstance();
        drvMan.BeginConnectToDevice(mSensorName, mConnectionId);
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString(CONNECTION_ID, mConnectionId);
        bundle.putString(SENSOR_NAME, mSensorName);
        super.onSaveInstanceState(bundle);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((IOIOAccessService.LocalBinder) service).getService();
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ConnectIOIO Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.drock.n_corder/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ConnectIOIO Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.drock.n_corder/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
