package com.example.drock.n_corder;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewTypeActivity extends SingleFragmentActivity implements ViewTypeListFragment.OnListFragmentInteractionListener {
    String mStreamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(null != savedInstanceState) {
            mStreamName = savedInstanceState.getString(ParamNames.STREAM_NAME);
        } else {
            Intent intent = getIntent();
            mStreamName = intent.getStringExtra(ParamNames.STREAM_NAME);
        }
    }

    @Override
    protected Fragment createFragment() {
        return ViewTypeListFragment.newInstance();
    }

    @Override
    public void onListFragmentInteraction(Object object) {
        String viewType = (String)object;
        Intent intent = new Intent(this, DataViewActivity.class);
        intent.putExtra(ParamNames.STREAM_NAME, mStreamName);
        intent.putExtra(ParamNames.DISPLAY_PARAMS, viewType);
        startActivity(intent);
    }
}
