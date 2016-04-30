package com.example.drock.n_corder.units;

public class AngularSpeedConverter extends UnitConversionTable {
    protected static final float mConversionTable[][] = {
            {1, 0, 0},
            {0, 1, (float)(360. / (2. * Math.PI))},
            {0, (float)(2. * Math.PI / 360.), 1}
    };

    @Override
    public int getDefaultUnit() { return AngularSpeedUnits.RADIANS_PER_SECOND; }

    @Override
    public float[][] getConversionTable() { return mConversionTable; }
}
