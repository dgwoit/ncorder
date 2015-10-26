package com.example.drock.n_corder;

import android.app.ListFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import android.widget.ArrayAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class OnboardSensorListFragment extends ListFragment {

    public OnboardSensorListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboard_sensor_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> items = new ArrayList<String>();
        items.add("1");
        items.add("2");
        items.add("3");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,  items);
        setListAdapter(adapter);
    }
}
