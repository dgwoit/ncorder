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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

import com.example.drock.n_corder.IOIO.IOIOConfigureConnectionActivity;
import com.example.drock.n_corder.IOIO.SamplingIntervalActivity;
import com.example.drock.n_corder.android.AndroidSensorListActivity;
import com.example.drock.n_corder.android.OnboardLocationDetailActivity;
import com.example.drock.n_corder.fileselector.FileOperation;
import com.example.drock.n_corder.fileselector.FileSelector;
import com.example.drock.n_corder.fileselector.OnHandleFileListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemFactoryBroker.initSystemFactory(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showOnboardSensorsMessage(View view) {
        startActivityByClass(AndroidSensorListActivity.class);
    }

    public void showOnboardLocationActivity(View view) {
        startActivityByClass(OnboardLocationDetailActivity.class);
    }

    public void showIOIOConnectActivity(View view) {
        startActivityByClass(SamplingIntervalActivity.class);
    }

    public void showAudioSpectrumActivity(View view) {
        startActivityByClass(AudioSpectrumActivity.class);
    }

    public void startActivityByClass(Class activityClass) {
        //apply preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useLocationInfo = preferences.getBoolean("pref_record_location", false);
        SystemFactoryBroker.getSystemFactory().getMeasurementFactory().setUseLocationInfo(useLocationInfo);

        //start activity
        startActivity(new Intent(this, activityClass));
    }

    public void loadData(View view) {
        OnHandleFileListener loadFileListener = new OnHandleFileListener() {
            @Override
            public void handleFile(final String filePath) {
                try {
                    MeasurementDataStore dataStore = MeasurementDataStore.getInstance();
                    dataStore.load(filePath);
                    Intent intent = new Intent(MainActivity.this, DataViewActivity.class);
                    intent.putExtra(ParamNames.STREAM_NAME, ""); //no stream to attach to, pre-recorded data
                    intent.putExtra(ParamNames.DISPLAY_PARAMS, DataViewFactory.ViewType.DataPlot);
                    startActivity(intent);
                } catch(IOException ex) {
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
                }
            }
        };
        String[] fileFilter = {".csv"};
        new FileSelector(this, FileOperation.LOAD, loadFileListener, fileFilter).show();
    }
}
