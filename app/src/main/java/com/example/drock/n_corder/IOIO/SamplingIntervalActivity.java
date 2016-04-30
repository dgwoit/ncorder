package com.example.drock.n_corder.IOIO;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import com.example.drock.n_corder.ParamNames;
import com.example.drock.n_corder.SingleFragmentActivity;

public class SamplingIntervalActivity extends SingleFragmentActivity implements SamplingIntervalFragment.OnListFragmentInteractionListener{
    @Override
    protected Fragment createFragment() {
        return SamplingIntervalFragment.newInstance();
    }

    @Override
    public void onListFragmentInteraction(Object object) {
        int interval = ((Integer)object);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putInt("sampling_interval", interval).commit();

        Intent intent = new Intent(this, IOIOConfigureConnectionActivity.class);
        startActivity(intent);
    }
}
