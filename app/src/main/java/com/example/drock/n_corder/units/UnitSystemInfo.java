package com.example.drock.n_corder.units;

import java.util.LinkedList;
import java.util.List;

public class UnitSystemInfo {
    protected int mUnitSystem;
    protected String mUnitSystemName;
    protected int mSystemUnit;
    protected List<UnitTypeInfo> mUnitTypes = new LinkedList<>();

    public UnitSystemInfo(int unitSystem, String unitSystemName, int systemUnit) {
        mUnitSystem = unitSystem;
        mUnitSystemName = unitSystemName;
        mSystemUnit = systemUnit;
    }

    public int getUnitSystem() { return mUnitSystem; }
    public String getUnitSystemName() { return mUnitSystemName; }
    public int getSystemUnit() { return mSystemUnit; }
    public List<UnitTypeInfo> getUnitTypes() { return mUnitTypes; }

    //construction facilitator
    public UnitSystemInfo addUnit(int unitType, String unitName, String unitSuffix) {
        mUnitTypes.add(new UnitTypeInfo(unitType, unitName, unitSuffix));
        return this;
    }
    public UnitTypeInfo getUnitTypeInfo(int unitType) {
        for(UnitTypeInfo unitTypeInfo: mUnitTypes) {
            if(unitType == unitTypeInfo.getType())
                return unitTypeInfo;
        }

        return null;
    }
}
