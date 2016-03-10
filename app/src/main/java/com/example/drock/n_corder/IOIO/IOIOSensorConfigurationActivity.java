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
package com.example.drock.n_corder.IOIO;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.drock.n_corder.ListFragmentBase;
import com.example.drock.n_corder.R;

public class IOIOSensorConfigurationActivity extends AppCompatActivity implements ListFragmentBase.OnListFragmentInteractionListener {
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
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString(CONNECTION_ID, mConnectionId);
        super.onSaveInstanceState(bundle);
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
