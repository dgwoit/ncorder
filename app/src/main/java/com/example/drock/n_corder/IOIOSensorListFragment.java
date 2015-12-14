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
