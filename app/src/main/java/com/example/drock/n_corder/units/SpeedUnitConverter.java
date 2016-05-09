package com.example.drock.n_corder.units;

public class SpeedUnitConverter extends UnitConversionTable {
    protected final static float[][] mConversionTable = {
            {1, 0, 0, 0},
            {0, 1, 3.28084f},
            {0, 1/3.28084f, 1},
    };

    public float[][] getConversionTable() { return mConversionTable; }

    @Override
    public int getDefaultUnit() {
        return SpeedUnits.METERS_PER_SECOND;
    }
}
