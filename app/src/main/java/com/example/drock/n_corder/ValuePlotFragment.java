package com.example.drock.n_corder;


import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ValuePlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ValuePlotFragment extends Fragment implements IMeasurementSink {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STREAM_NAME = "streamName";

    private String mStreamName;
    private List<Measurement> mMeasurements = new LinkedList<Measurement>();
    private DataPlotView mDataView;

    public ValuePlotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param streamName name of stream to bind to
     * @return A new instance of fragment ValuePlotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ValuePlotFragment newInstance(String streamName) {
        ValuePlotFragment fragment = new ValuePlotFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STREAM_NAME, streamName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStreamName = getArguments().getString(ARG_STREAM_NAME);
        }


        SensorStreamBroker.getInstance().AttachToStream(this, mStreamName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_value_plot, container, false);
        mDataView = (DataPlotView)view.findViewById(R.id.data_plot_view);
        if(mDataView != null) {
            mDataView.attachDataSource(mMeasurements);
        }
        return view;
    }

    // from MeasurementSink
    @Override
    public boolean update(Measurement m) {
        addMeasurement(m);

        return true;
    }

    private synchronized void addMeasurement(Measurement m) {
        if(mMeasurements != null) {
            mMeasurements.add(m);
            while(mMeasurements.size() > 10000) //use hard-coded value for now
                mMeasurements.remove(0);

            if(mDataView != null)
                mDataView.onDataChanged();
        }
    }
}
