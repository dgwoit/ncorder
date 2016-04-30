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

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.example.drock.n_corder.ListFragmentBase;
import com.example.drock.n_corder.ParamNames;
import com.example.drock.n_corder.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AndroidSensorDetailFragment extends ListFragmentBase implements AbsListView.OnItemClickListener {
    // TODO: Customize parameters
    private int mSensorType;
    private String mDeviceName;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AndroidSensorDetailFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AndroidSensorDetailFragment newInstance(int sensorType, String deviceName) {
        AndroidSensorDetailFragment fragment = new AndroidSensorDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ParamNames.SENSOR_TYPE, sensorType);
        args.putString(ParamNames.DEVICE_NAME, deviceName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mSensorType = getArguments().getInt(ParamNames.SENSOR_TYPE);
            mDeviceName = getArguments().getString(ParamNames.DEVICE_NAME);
        }

        super.onCreate(savedInstanceState);
    }

    public class BindingInfo {
        public AndroidSensorEventAdapter adapter;
        public int sensorType;
    }

    @Override
    protected void createListItems() {
        ArrayList<ListItem> listItems;
        AndroidSensorHelperTable helpers = new AndroidSensorHelperTable();
        AndroidSensorHelper helper = helpers.getEntry(mSensorType);
        listItems = new ArrayList<ListItem>();
        for(int i = 0; i < helper.getValueCount(); i++) {
            String description = helper.getValueDescription(i);
            BindingInfo bindingInfo = new BindingInfo();
            bindingInfo.sensorType = mSensorType;
            bindingInfo.adapter = helper.newAdapter(i, mDeviceName);
            ListItem item = new ListItem(description, bindingInfo);
            listItems.add(item);
        }
        setListItems(listItems);
    }

}
