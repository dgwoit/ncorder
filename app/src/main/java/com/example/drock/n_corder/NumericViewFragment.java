package com.example.drock.n_corder;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NumericViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NumericViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumericViewFragment extends android.support.v4.app.Fragment implements IMeasurementSink {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "streamName";


    private String mStreamName;

    private TextView mTV;
    //private OnFragmentInteractionListener mListener;

    public NumericViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param streamName the name of the sensor stream to attach to
     * @return A new instance of fragment NumericViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumericViewFragment newInstance(String streamName) {
        NumericViewFragment fragment = new NumericViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, streamName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStreamName = getArguments().getString(ARG_PARAM1);
            //SensorStreamBroker.getInstance().AttachToStream(this, mStreamName);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SensorStreamBroker.getInstance().DetachFromStream(this, mStreamName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_numeric_view, container, false);
        mTV = (TextView) view.findViewById(R.id.fragmentSensorValue);
        SensorStreamBroker.getInstance().AttachToStream(this, mStreamName);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    //    if (mListener != null) {
    //        mListener.onFragmentInteraction(uri);
    //    }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // from MeasurementSink
    @Override
    public boolean update(Measurement m) {
        return setValue(m.getValue());
    }

    float mValue = 0;
    private boolean setValue(float value) {
        try {
            if(value != mValue) {
                mValue = value;
                final String str = String.format("%f", value);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTV.setText(str);
                    }
                });
            }
            return true;
        } catch (Exception e){
            Log.e("NumericViewFragment", "error setting value");
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SensorStreamBroker.getInstance().AttachToStream(this, mStreamName);
    }

    @Override
    public void onPause() {
        super.onPause();
        SensorStreamBroker.getInstance().DetachFromStream(this, mStreamName);
    }
}
