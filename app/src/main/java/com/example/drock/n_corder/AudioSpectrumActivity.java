package com.example.drock.n_corder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

public class AudioSpectrumActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return AudioSpectrumFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
