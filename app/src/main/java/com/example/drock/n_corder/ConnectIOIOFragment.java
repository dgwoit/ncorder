package com.example.drock.n_corder;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectIOIOFragment extends Fragment {


    public ConnectIOIOFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect_ioio, container, false);
    }

    public void setValue(float value) {
        View v = getView();
        TextView tv = (TextView)v.findViewById(R.id.value_text);
        tv.setText(String.format("%f", value));
    }
}
