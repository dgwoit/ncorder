package com.example.drock.n_corder.units;

public class MagneticFieldUnitSuffixer implements IUnitSuffixer {
    public String getSuffix(int unit) {
        String suffix = "";
        if(unit == MagneticFieldUnits.MICRO_TESLA) {
            suffix = "uT";
        } else if(unit == MagneticFieldUnits.TESLA) {
            suffix = "T";
        }

        return suffix;
    }
}
