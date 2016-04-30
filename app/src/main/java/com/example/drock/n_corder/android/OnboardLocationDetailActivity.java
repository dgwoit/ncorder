package com.example.drock.n_corder.android;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.drock.n_corder.ListFragmentBase;
import com.example.drock.n_corder.LocationManagerHelper;
import com.example.drock.n_corder.ParamNames;
import com.example.drock.n_corder.SensorStreamBroker;
import com.example.drock.n_corder.SingleFragmentActivity;
import com.example.drock.n_corder.SystemFactoryBroker;
import com.example.drock.n_corder.ViewTypeActivity;

public class OnboardLocationDetailActivity extends SingleFragmentActivity implements ListFragmentBase.OnListFragmentInteractionListener{
    @Override
    protected Fragment createFragment() {
        return new OnboardLocationDetailFragment();
    }

    @Override
    public void onListFragmentInteraction(Object object) {
        LocationListenerMeasurementSourceAdapter adapter = (LocationListenerMeasurementSourceAdapter)object;
        if(adapter != null) {
            String streamMoniker = adapter.getMoniker();
            SensorStreamBroker.getInstance().RegisterStream(streamMoniker, adapter);
            LocationManagerHelper helper = SystemFactoryBroker.getSystemFactory().getLocationManagerHelper();
            helper.requestLocationUpdates(adapter);

            Intent intent = new Intent(this, ViewTypeActivity.class);
            intent.putExtra(ParamNames.STREAM_NAME, streamMoniker);
            startActivity(intent);
        }
    }
}
