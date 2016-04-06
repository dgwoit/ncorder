package com.example.drock.n_corder;

import com.example.drock.n_corder.units.UnitConverterFactory;
import com.example.drock.n_corder.units.UnitSystemTable;

public class SystemFactory implements ISystemFactory {
    DisplayUnitManager mDisplayUnitManager;
    UnitSystemTable mUnitSystemTable;
    UnitConverterFactory mUnitConverterFactory;

    @Override
    public UnitSystemTable getUnitSystemTable() {
        if(null == mUnitSystemTable)
            mUnitSystemTable = new UnitSystemTable();
        return mUnitSystemTable;
    }

    @Override
    public DisplayUnitManager getDisplayUnitManager() {
        if(null == mDisplayUnitManager)
            mDisplayUnitManager = new DisplayUnitManager();
        return mDisplayUnitManager;
    }

    @Override
    public UnitConverterFactory getUnitConverterFactory() {
        if(null == mUnitConverterFactory) {
            mUnitConverterFactory = UnitConverterFactory.newInstance();
        }
        return mUnitConverterFactory;
    }
}
