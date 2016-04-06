package com.example.drock.n_corder;

import android.app.ListFragment;

import com.example.drock.n_corder.units.UnitSystemInfo;
import com.example.drock.n_corder.units.UnitSystemTable;

import java.util.ArrayList;

public class DisplayUnitSettingsListFragment  extends ListFragmentBase {
    protected UnitSystemTable mUnitSystemTable = new UnitSystemTable();
    protected DisplayUnitManager mDisplayUnitManager = new DisplayUnitManager(); //maybe retrieve from factory

    static public DisplayUnitSettingsListFragment newInstance() {
        return new DisplayUnitSettingsListFragment();
    }

    public DisplayUnitSettingsListFragment() {
        super();
    }

    @Override
    protected void createListItems() {
        ArrayList<ListFragmentBase.ListItem> listItems = new ArrayList<>();
        for(Integer unitSystem: mDisplayUnitManager.getDisplayUnits().keySet()) {
            UnitSystemInfo unitSystemInfo = mUnitSystemTable.getUnitSystemInfo(unitSystem);
            listItems.add(new ListItem(unitSystemInfo.getUnitSystemName(), unitSystemInfo));
        }
        setListItems(listItems);
    }
}
