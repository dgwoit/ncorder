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
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drock.n_corder.dummy.DummyContent;
import com.example.drock.n_corder.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class IOIOSensorListFragment extends ListFragmentBase {

    private static final String CONNECTION_ID = "connection-id";

    // TODO: Customize parameters
    private String mConnectionId;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IOIOSensorListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static IOIOSensorListFragment newInstance(String connectionId) {
        IOIOSensorListFragment fragment = new IOIOSensorListFragment();
        Bundle args = new Bundle();
        args.putString(CONNECTION_ID, connectionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void createListItems() {
        if (getArguments() != null) {
            mConnectionId = getArguments().getString(CONNECTION_ID);
        }

        IOIODeviceDriverManager drvMan = IOIODeviceDriverManager.getInstance();
        Collection<IOIODeviceDriverInfo> driverInfos = drvMan.getDriversForConnection(mConnectionId);
        IOIOConnectionTable connections = new IOIOConnectionTable();
        ArrayList<ListItem> listItems = new ArrayList<ListItem>();
        for( IOIODeviceDriverInfo info : driverInfos) {
            listItems.add(new ListItem(info.getName(), info));
        }
        setListItems(listItems);
    }
}
