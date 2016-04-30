package com.example.drock.n_corder.units;

//TODO: make DisplayUnitFormatter hold a unit formatter and implement UnitFormatterInterface
public class TimeUnitFormatter extends DisplayUnitFormatter {
    public TimeUnitFormatter(int units) {
        super(units);
    }

    @Override
    public String format(long seconds) {
        String str;
        if(getUnit() == TimeUnits.HOURS) {
            str = String.format("%02d:%02d:%02d", seconds/3600, (seconds/60) % 60, seconds % 60);
        } else if(getUnit() == TimeUnits.MINUTES) {
            str = String.format("%02d:%02d", seconds / 60, seconds % 60);
        } else {
            str = String.format("%d s", seconds);
        }

        return str;
    }
}
