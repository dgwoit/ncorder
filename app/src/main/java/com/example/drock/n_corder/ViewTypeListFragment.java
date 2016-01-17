package com.example.drock.n_corder;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drock on 1/10/2016.
 */
public class ViewTypeListFragment extends ListFragmentBase {
    public ViewTypeListFragment() {}

    public static ViewTypeListFragment newInstance() {
        ViewTypeListFragment fragment = new ViewTypeListFragment();
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
        listItems.add(new ListItem("Numeric Display", DataViewFactory.ViewType.NumericDisplay));
        listItems.add(new ListItem("Data Plot", DataViewFactory.ViewType.DataPlot));
        setListItems(listItems);
    }
}
