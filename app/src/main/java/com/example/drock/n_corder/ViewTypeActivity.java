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

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class ViewTypeActivity extends SingleFragmentActivity implements ViewTypeListFragment.OnListFragmentInteractionListener {
    String mStreamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        if(null != savedInstanceState) {
            mStreamName = savedInstanceState.getString(ParamNames.STREAM_NAME);
        } else {
            Intent intent = getIntent();
            mStreamName = intent.getStringExtra(ParamNames.STREAM_NAME);
            if(mStreamName != null && !mStreamName.isEmpty()) {
                MeasurementDataStore.recordStream(mStreamName);
            } else { //go back to beginning
                startActivity(new Intent(this, MainActivity.class));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_type, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            MeasurementDataStore.getInstance().save(this);
            return true;
        }

        if(id == R.id.action_new) {
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
        }

        if(id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if(mStreamName != null)
            savedInstanceState.putString(ParamNames.STREAM_NAME, mStreamName);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return ViewTypeListFragment.newInstance();
    }

    @Override
    public void onListFragmentInteraction(Object object) {
        String viewType = (String)object;
        Intent intent = new Intent(this, DataViewActivity.class);
        intent.putExtra(ParamNames.STREAM_NAME, mStreamName);
        intent.putExtra(ParamNames.DISPLAY_PARAMS, viewType);
        startActivity(intent);
    }
}
