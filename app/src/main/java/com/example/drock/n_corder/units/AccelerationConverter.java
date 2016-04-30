package com.example.drock.n_corder.units;

public class AccelerationConverter extends UnitConversionTable {
    protected static final float mConversionTable[][]={
            {1f, 0f, 0f},
            {0f, 1f, 0.3048f},
            {0f, 1f/0.3048f, 1f}};

    @Override
    protected float[][] getConversionTable() {
        return mConversionTable;
    }

    @Override
    public int getDefaultUnit() {
        return AccelerationUnits.METERS_PER_SEC_SEC;
    }
}
