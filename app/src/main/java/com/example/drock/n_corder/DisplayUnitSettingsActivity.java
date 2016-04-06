package com.example.drock.n_corder;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.drock.n_corder.units.UnitSystemInfo;

public class DisplayUnitSettingsActivity  extends SingleFragmentActivity implements ListFragmentBase.OnListFragmentInteractionListener {
    @Override
    protected Fragment createFragment() {
        return DisplayUnitSettingsListFragment.newInstance();
    }

    @Override
    public void onListFragmentInteraction(Object object) {
        UnitSystemInfo unitTypeInfo = (UnitSystemInfo)object;
        Intent intent = new Intent(this, DisplayUnitTypeSettingActivity.class);
        intent.putExtra(ParamNames.UNIT_SYSTEM, Integer.toString(unitTypeInfo.getUnitSystem()));
        startActivity(intent);
    }
}
