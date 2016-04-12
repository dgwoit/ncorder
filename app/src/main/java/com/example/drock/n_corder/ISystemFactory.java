package com.example.drock.n_corder;

import android.content.Context;

import com.example.drock.n_corder.units.UnitConverterFactory;
import com.example.drock.n_corder.units.UnitSystemTable;

public interface ISystemFactory {
    DisplayUnitManager getDisplayUnitManager();
    UnitSystemTable getUnitSystemTable();
    UnitConverterFactory getUnitConverterFactory();
    LocationManagerHelper getLocationManagerHelper();
    MeasurementFactory getMeasurementFactory();
}
