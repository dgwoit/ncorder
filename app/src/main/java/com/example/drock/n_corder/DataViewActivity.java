package com.example.drock.n_corder;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

public class DataViewActivity extends AppCompatActivity {
    private String mStreamName;
    private String mDisplayParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Live Data");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(null != savedInstanceState) {
            mStreamName = savedInstanceState.getString(ParamNames.STREAM_NAME);
            mDisplayParams = savedInstanceState.getString(ParamNames.DISPLAY_PARAMS);
        } else {
            Intent intent = getIntent();
            mStreamName = intent.getStringExtra(ParamNames.STREAM_NAME);
            mDisplayParams = intent.getStringExtra(ParamNames.DISPLAY_PARAMS);
        }

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if(null ==  fragment) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this, IOIOAccessService.class));
    }

    protected Fragment createFragment() {
        DataViewFactory factory = new DataViewFactory();
        return factory.createFragment(mDisplayParams, mStreamName);
    }
}
