package com.example.drock.n_corder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AndroidSensorDetailFragment extends Fragment implements AbsListView.OnItemClickListener {

    private static final String ARG_SENSOR_TYPE = "sensor-type";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private int mSensorType;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    protected class ListItem {
        int mSensorType;
        String mValueDescription;
        int mValueSelector;


        public ListItem(int sensorType, String valueDescription, int valueSelector) {
            mSensorType = sensorType;
            mValueDescription = valueDescription;
            mValueSelector = valueSelector;
        }

        @Override
        public String toString() {
            return mValueDescription;
        }

        public int getSensorType() { return mSensorType; }
        public int getValueSelector() { return mValueSelector; }
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AndroidSensorDetailFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AndroidSensorDetailFragment newInstance(int sensorType) {
        Log.d("AndroidSensorDetailFragment", "newInstance");
        AndroidSensorDetailFragment fragment = new AndroidSensorDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SENSOR_TYPE, sensorType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("AndroidSensorDetailFragment", "onCreate");
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSensorType = getArguments().getInt(ARG_SENSOR_TYPE);
        }

        CreateListContent();
        mAdapter = new ArrayAdapter<ListItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, mListItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("AndroidSensorDetailFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }


    List<ListItem> mListItems;
    protected void CreateListContent() {
        Log.d("AndroidSensorDetailFragment", "getListContent");
        AndroidSensorHelperTable helpers = new AndroidSensorHelperTable();
        AndroidSensorHelper helper = helpers.getEntry(mSensorType);
        mListItems = new LinkedList<ListItem>();
        for(int i = 0; i < helper.getValueCount(); i++) {
            String description = helper.getValueDescription(i);
            ListItem item = new ListItem(mSensorType, description, i);
            mListItems.add(item);
        }
    }

    @Override
    public void onAttach(Activity context) {
        Log.d("AndroidSensorDetailFragment", "onAttach");
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.d("AndroidSensorDetailFragment", "onDetach");
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onListFragmentInteraction(mListItems.get(position));
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ListItem item);
    }


}
