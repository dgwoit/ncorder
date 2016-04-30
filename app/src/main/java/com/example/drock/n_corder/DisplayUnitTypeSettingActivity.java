package com.example.drock.n_corder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.example.drock.n_corder.units.UnitTypeInfo;

public class DisplayUnitTypeSettingActivity extends SingleFragmentActivity implements ListFragmentBase.OnListFragmentInteractionListener {
    protected String mUnitSystemString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(null != savedInstanceState) {
            mUnitSystemString = savedInstanceState.getString(ParamNames.UNIT_SYSTEM);
        } else {
            Intent intent = getIntent();
            mUnitSystemString = intent.getStringExtra(ParamNames.UNIT_SYSTEM);
        }

        super.onCreate(savedInstanceState);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null)
//            actionBar.setTitle(unit system);

    }

    @Override
    protected Fragment createFragment() {
        int unitSystem = Integer.parseInt(mUnitSystemString);
        return DisplayUnitTypeSettingFragment.newInstance(unitSystem);
    }

    @Override
    public void onListFragmentInteraction(Object object) {
        UnitTypeInfo unitTypeInfo = (UnitTypeInfo)object;
        DisplayUnitManager displayUnitManager = new DisplayUnitManager(this);
        displayUnitManager.setDisplayUnit(unitTypeInfo.getUnitSystem(), unitTypeInfo.getType());
        finish();
    }
}
