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
package com.example.drock.n_corder.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.drock.n_corder.ParamNames;
import com.example.drock.n_corder.R;
import com.example.drock.n_corder.SingleFragmentActivity;
import com.example.drock.n_corder.ViewTypeActivity;

public class AndroidSensorConfigurationActivity extends SingleFragmentActivity implements AndroidSensorDetailFragment.OnListFragmentInteractionListener {
    private int mSensorType;
    private String mDeviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(null != savedInstanceState) {
            mSensorType = savedInstanceState.getInt(ParamNames.SENSOR_TYPE);
            mDeviceName = savedInstanceState.getString(ParamNames.DEVICE_NAME);
        } else {
            Intent intent = getIntent();
            mSensorType = intent.getIntExtra(ParamNames.SENSOR_TYPE, 0);
            mDeviceName = intent.getStringExtra(ParamNames.DEVICE_NAME);
        }

        super.onCreate(savedInstanceState);
        startService(new Intent(this, AndroidSensorService.class));
        doBindService();
    }

    protected Fragment createFragment() {
        return AndroidSensorDetailFragment.newInstance(mSensorType, mDeviceName);
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
    public void onListFragmentInteraction(Object object) {
        AndroidSensorDetailFragment.BindingInfo bindingInfo = (AndroidSensorDetailFragment.BindingInfo)object;
        mBoundService.BindSensor(bindingInfo.sensorType, bindingInfo.adapter);

        Intent intent = new Intent(this, ViewTypeActivity.class);
        intent.putExtra(ParamNames.STREAM_NAME, bindingInfo.adapter.getMoniker());
        startActivity(intent);
    }
}
