package com.example.drock.n_corder;

import android.content.Context;

import com.example.drock.n_corder.units.UnitConverterFactory;
import com.example.drock.n_corder.units.UnitSystemTable;

public class SystemFactory implements ISystemFactory {
    Context mApplicationContext;
    DisplayUnitManager mDisplayUnitManager;
    UnitSystemTable mUnitSystemTable;
    UnitConverterFactory mUnitConverterFactory;
    LocationManagerHelper mLocationManagerHelper;
    MeasurementFactory mMeasurementFactory;

    public SystemFactory(Context applicationContext) {mApplicationContext = applicationContext;}

    @Override
    public UnitSystemTable getUnitSystemTable() {
        if(null == mUnitSystemTable)
            mUnitSystemTable = new UnitSystemTable();
        return mUnitSystemTable;
    }

    @Override
    public DisplayUnitManager getDisplayUnitManager() {
        if(null == mDisplayUnitManager)
            mDisplayUnitManager = new DisplayUnitManager(mApplicationContext);
        return mDisplayUnitManager;
    }

    @Override
    public UnitConverterFactory getUnitConverterFactory() {
        if(null == mUnitConverterFactory) {
            mUnitConverterFactory = UnitConverterFactory.newInstance();
        }
        return mUnitConverterFactory;
    }

    public LocationManagerHelper getLocationManagerHelper() {
        if(null == mLocationManagerHelper) {
            mLocationManagerHelper = new LocationManagerHelper(mApplicationContext);
        }
        return mLocationManagerHelper;
    }

    public MeasurementFactory getMeasurementFactory() {
        if(null == mMeasurementFactory) {
            mMeasurementFactory = new MeasurementFactory();
        }
        return mMeasurementFactory;
    }
}
