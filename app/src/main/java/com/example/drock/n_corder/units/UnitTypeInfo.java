package com.example.drock.n_corder.units;

public class UnitTypeInfo {
    protected int mType;
    protected String mName;
    protected String mSuffix;

    public UnitTypeInfo(int type, String name, String suffix) {
        mType = type;
        mName = name;
        mSuffix = suffix;
    }

    public int getType() { return mType; }
    public int getUnitSystem() { return Units.getBasicUnitType(mType); }
    public String getName() { return mName; }
    public String getSuffix() { return mSuffix; }
}
