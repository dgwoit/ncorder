package com.example.drock.n_corder;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class IOIOConnectionsListFragment extends ListFragmentBase {

    // TODO: Customize parameter argument names
    private static final String ARG_LIST_ITEMS = "list-items";
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IOIOConnectionsListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static IOIOConnectionsListFragment newInstance() {
        IOIOConnectionsListFragment fragment = new IOIOConnectionsListFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    protected void createListItems() {
        IOIOConnectionTable connections = new IOIOConnectionTable();
        List<IOIOConnectionInfo> connectionInfos = connections.getConnectionInfo();
        ArrayList<ListItem> listItems = new ArrayList<ListItem>();
        for( IOIOConnectionInfo info : connectionInfos) {
            listItems.add(new ListItem(info.getName(), info));
        }
        setListItems(listItems);
    }
}
