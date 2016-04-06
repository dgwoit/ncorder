package com.example.drock.n_corder.units;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UnitSystemTable {
    Map<Integer, UnitSystemInfo> mUnitSystems = new TreeMap<>();
    public UnitSystemTable() {
        mUnitSystems.put(Units.UNKNOWN, new UnitSystemInfo(Units.UNKNOWN, "UNKNOWN", Units.UNKNOWN)
            .addUnit(Units.UNKNOWN, "Undefined", ""));
        mUnitSystems.put(Units.ACCELERATION, new UnitSystemInfo(Units.ACCELERATION, "ACCELERATION", Units.ACCELERATION)
            .addUnit(Units.ACCELERATION, "Meters per Second per Second", "m/s²"));
        mUnitSystems.put(Units.ANGLE, new UnitSystemInfo(Units.ANGLE,"ANGLE",AngleUnits.RADIANS)
            .addUnit(AngleUnits.DEGREES, "Degrees", "º")
            .addUnit(AngleUnits.RADIANS, "Radians", "rad"));
        mUnitSystems.put(Units.MAGNETIC_FIELD_STRENGH, new UnitSystemInfo(Units.MAGNETIC_FIELD_STRENGH,"MAGNETIC FIELD STRENGTH",MagneticFieldUnits.TESLA)
            .addUnit(MagneticFieldUnits.TESLA, "Tesla", "T")
            .addUnit(MagneticFieldUnits.MICRO_TESLA, "Microtesla", "\u03BCT"));
        mUnitSystems.put(Units.ANGULAR_SPEED, new UnitSystemInfo(Units.ANGULAR_SPEED,"ANGULAR SPEED",Units.ANGULAR_SPEED));
        mUnitSystems.put(Units.PRESSURE, new UnitSystemInfo(Units.PRESSURE,"PRESSURE",PressureUnits.PASCAL)
            .addUnit(PressureUnits.MILLIBAR, "Millibar", "mb")
            .addUnit(PressureUnits.PASCAL, "Pascal", "Pa"));
        mUnitSystems.put(Units.DISTANCE, new UnitSystemInfo(Units.DISTANCE,"DISTANCE",DistanceUnits.METERS)
            .addUnit(DistanceUnits.CENTIMETERS, "Centimeters", "cm")
            .addUnit(DistanceUnits.METERS, "Meters", "m"));
        mUnitSystems.put(Units.RELATIVE_HUMIDITY, new UnitSystemInfo(Units.RELATIVE_HUMIDITY,"RELATIVE HUMIDITY",Units.RELATIVE_HUMIDITY)
            .addUnit(Units.RELATIVE_HUMIDITY, "Relative Humidity", "%"));
        mUnitSystems.put(Units.TEMPERATURE, new UnitSystemInfo(Units.TEMPERATURE,"TEMPERATURE",TemperatureUnits.KELVIN)
            .addUnit(TemperatureUnits.CELSIUS, "Celsius", "ºC")
            .addUnit(TemperatureUnits.KELVIN, "Kelvin", "K")
            .addUnit(TemperatureUnits.FAHRENHEIT, "Fahrenheit", "°F"));
        mUnitSystems.put(Units.HEART_RATE, new UnitSystemInfo(Units.HEART_RATE,"HEART RATE",Units.HEART_RATE)
            .addUnit(Units.HEART_RATE, "Beats Per Minute", "bpm"));
        mUnitSystems.put(Units.ILLUMINANCE, new UnitSystemInfo(Units.ILLUMINANCE,"ILLUMINANCE",Units.ILLUMINANCE)
            .addUnit(Units.ILLUMINANCE, "Lumens", "lm"));
        mUnitSystems.put(Units.STEPS, new UnitSystemInfo(Units.STEPS,"STEPS",Units.STEPS)
            .addUnit(Units.STEPS, "Steps", "steps"));
        mUnitSystems.put(Units.CURRENT, new UnitSystemInfo(Units.CURRENT,"CURRENT",Units.CURRENT)
            .addUnit(Units.CURRENT, "Amps", "A"));
        mUnitSystems.put(Units.MASS, new UnitSystemInfo(Units.MASS,"MASS",Units.MASS)
            .addUnit(Units.MASS, "Kilogram", "kg"));
        mUnitSystems.put(Units.WEIGHT, new UnitSystemInfo(Units.WEIGHT,"WEIGHT",Units.WEIGHT)
            .addUnit(Units.WEIGHT, "Newtons", "N"));
        mUnitSystems.put(Units.TIME, new UnitSystemInfo(Units.TIME,"TIME",Units.TIME)
            .addUnit(Units.TIME, "Seconds", "s"));
    }

    public Map<Integer, UnitSystemInfo> getUnitSystems() {
        return mUnitSystems;
    }

    public UnitSystemInfo getUnitSystemInfo(int unitSystem) {
        return mUnitSystems.get(Units.getBasicUnitType(unitSystem));
    }
}
