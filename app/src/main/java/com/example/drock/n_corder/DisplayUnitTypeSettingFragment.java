package com.example.drock.n_corder;

import com.example.drock.n_corder.units.UnitSystemInfo;
import com.example.drock.n_corder.units.UnitSystemTable;
import com.example.drock.n_corder.units.UnitTypeInfo;

import java.util.ArrayList;

public class DisplayUnitTypeSettingFragment extends ListFragmentBase {
    protected UnitSystemInfo mUnitSystem;

    static public DisplayUnitTypeSettingFragment newInstance(int unitSystem) {
        return new DisplayUnitTypeSettingFragment(unitSystem);
    }

    public DisplayUnitTypeSettingFragment(int unitSystem) {
        UnitSystemTable unitSystemTable = new UnitSystemTable();
        mUnitSystem = unitSystemTable.getUnitSystemInfo(unitSystem);
    }

    @Override
    protected void createListItems() {
        ArrayList<ListItem> listItems = new ArrayList<>();
        for(UnitTypeInfo unitType: mUnitSystem.getUnitTypes()) {
            listItems.add(new ListItem(String.format("%s (%s)", unitType.getName(), unitType.getSuffix()), unitType));
        }
        setListItems(listItems);
    }


}
