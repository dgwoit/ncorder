package com.example.drock.n_corder;

import com.example.drock.n_corder.units.AngleUnits;
import com.example.drock.n_corder.units.DisplayUnitFormatter;
import com.example.drock.n_corder.units.DistanceUnits;
import com.example.drock.n_corder.units.MagneticFieldUnits;
import com.example.drock.n_corder.units.PressureUnits;
import com.example.drock.n_corder.units.TemperatureUnits;
import com.example.drock.n_corder.units.UnitFormatter;
import com.example.drock.n_corder.units.Units;

import java.util.Map;
import java.util.TreeMap;

public class DisplayUnitManager {
    static Map<Integer, ObservableInteger> mDisplayUnits;

    DisplayUnitManager() {
        if(null == mDisplayUnits) {
            mDisplayUnits = new TreeMap<>();
            mDisplayUnits.put(Units.ACCELERATION, new ObservableInteger(Units.ACCELERATION));
            mDisplayUnits.put(Units.ANGLE, new ObservableInteger(AngleUnits.DEGREES));
            mDisplayUnits.put(Units.ANGULAR_SPEED, new ObservableInteger(Units.ANGULAR_SPEED));
            mDisplayUnits.put(Units.CURRENT, new ObservableInteger(Units.CURRENT));
            mDisplayUnits.put(Units.DISTANCE, new ObservableInteger(DistanceUnits.CENTIMETERS));
            mDisplayUnits.put(Units.HEART_RATE, new ObservableInteger(Units.HEART_RATE));
            mDisplayUnits.put(Units.ILLUMINANCE, new ObservableInteger(Units.ILLUMINANCE));
            mDisplayUnits.put(Units.MAGNETIC_FIELD_STRENGH, new ObservableInteger(MagneticFieldUnits.MICRO_TESLA));
            mDisplayUnits.put(Units.MASS, new ObservableInteger(Units.MASS));
            mDisplayUnits.put(Units.PRESSURE, new ObservableInteger(PressureUnits.MILLIBAR));
            mDisplayUnits.put(Units.RELATIVE_HUMIDITY, new ObservableInteger(Units.RELATIVE_HUMIDITY));
            mDisplayUnits.put(Units.STEPS, new ObservableInteger(Units.STEPS));
            mDisplayUnits.put(Units.TEMPERATURE, new ObservableInteger(TemperatureUnits.CELSIUS));
            mDisplayUnits.put(Units.TIME, new ObservableInteger(Units.TIME));
            mDisplayUnits.put(Units.WEIGHT, new ObservableInteger(Units.WEIGHT));
        }
    }

    Map<Integer, ObservableInteger> getDisplayUnits() {
        return mDisplayUnits;
    }

    int getDisplayUnit(int unitSystem) {
        return mDisplayUnits.get(Units.getBasicUnitType(unitSystem)).intValue();
    }

    UnitFormatter getUnitFormatter(int unitType) {
        int unitSystem = Units.getBasicUnitType(unitType);
        int displayUnitType = getDisplayUnit(unitSystem);
        DisplayUnitFormatter unitFormatter = new DisplayUnitFormatter(displayUnitType);
        mDisplayUnits.get(unitSystem).addObserver(unitFormatter);
        return unitFormatter;
    }

    void setDisplayUnit(int unitSystem, int unit) {
        mDisplayUnits.get(unitSystem).set(unit);
    }
}
