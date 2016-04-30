package com.example.drock.n_corder.units;

public class TimeUnits {
    //conversion constants
    public static final long NSEC_PER_MSEC = 1000000;
    public static final long NSEC_PER_SEC = 1000000000;

    //unit system values
    public static final int SECONDS = Units.makeUnit(Units.TIME, 1);
    public static final int MINUTES = Units.makeUnit(Units.TIME, 2);
    public static final int HOURS = Units.makeUnit(Units.TIME, 3);

}
