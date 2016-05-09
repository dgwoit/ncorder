package com.example.drock.n_corder.units;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UnitSystemTable {
    Map<Integer, UnitSystemInfo> mUnitSystems = new TreeMap<>();
    public UnitSystemTable() {
        mUnitSystems.put(Units.UNKNOWN, new UnitSystemInfo(Units.UNKNOWN, "UNKNOWN", Units.UNKNOWN)
            .addUnit(Units.UNKNOWN, "Undefined", ""));
        mUnitSystems.put(Units.ACCELERATION, new UnitSystemInfo(Units.ACCELERATION, "ACCELERATION", AccelerationUnits.METERS_PER_SEC_SEC)
                .addUnit(AccelerationUnits.METERS_PER_SEC_SEC, "Meters per Second²", "m/s²")
                .addUnit(AccelerationUnits.FEET_PER_SEC_SEC, "Feet per Second²", "ft/s²"));
        mUnitSystems.put(Units.ANGLE, new UnitSystemInfo(Units.ANGLE,"ANGLE",AngleUnits.RADIANS)
            .addUnit(AngleUnits.DEGREES, "Degrees", "º")
            .addUnit(AngleUnits.RADIANS, "Radians", "rad"));
        mUnitSystems.put(Units.MAGNETIC_FIELD_STRENGH, new UnitSystemInfo(Units.MAGNETIC_FIELD_STRENGH,"MAGNETIC FIELD STRENGTH",MagneticFieldUnits.TESLA)
            .addUnit(MagneticFieldUnits.TESLA, "Tesla", "T")
            .addUnit(MagneticFieldUnits.MICRO_TESLA, "Microtesla", "\u03BCT")
            .addUnit(MagneticFieldUnits.GAUSS, "Gauss", "G"));
        mUnitSystems.put(Units.ANGULAR_SPEED, new UnitSystemInfo(Units.ANGULAR_SPEED,"ANGULAR SPEED",AngularSpeedUnits.DEGREES_PER_SECOND)
            .addUnit(AngularSpeedUnits.RADIANS_PER_SECOND, "Radians per Second", "Rad/s")
            .addUnit(AngularSpeedUnits.DEGREES_PER_SECOND, "Degreess per Second", "º/s"));
        mUnitSystems.put(Units.PRESSURE, new UnitSystemInfo(Units.PRESSURE,"PRESSURE",PressureUnits.KILOPASCAL)
            .addUnit(PressureUnits.MILLIBAR, "Millibar", "mb")
            .addUnit(PressureUnits.KILOPASCAL, "Kilopascals", "kPa")
            .addUnit(PressureUnits.PSI, "Pounds per Square Inch", "psi"));
        mUnitSystems.put(Units.DISTANCE, new UnitSystemInfo(Units.DISTANCE,"DISTANCE",DistanceUnits.METERS)
            .addUnit(DistanceUnits.CENTIMETERS, "Centimeters", "cm")
            .addUnit(DistanceUnits.METERS, "Meters", "m")
            .addUnit(DistanceUnits.INCHES, "Inches", "in"));
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
        mUnitSystems.put(Units.VOLTAGE, new UnitSystemInfo(Units.VOLTAGE,"VOLTAGE",Units.VOLTAGE)
                .addUnit(Units.VOLTAGE, "Volts", "V"));
        mUnitSystems.put(Units.MASS, new UnitSystemInfo(Units.MASS,"MASS",Units.MASS)
            .addUnit(Units.MASS, "Kilogram", "kg"));
        mUnitSystems.put(Units.WEIGHT, new UnitSystemInfo(Units.WEIGHT,"WEIGHT",Units.WEIGHT)
            .addUnit(Units.WEIGHT, "Newtons", "N"));
        mUnitSystems.put(Units.TIME, new UnitSystemInfo(Units.TIME,"TIME",TimeUnits.SECONDS)
            .addUnit(TimeUnits.SECONDS, "Seconds", "s")
            .addUnit(TimeUnits.MINUTES, "Minutes", "MM:SS")
            .addUnit(TimeUnits.HOURS, "Hours", "HH:MM:SS")
        );
        mUnitSystems.put(Units.FORCE, new UnitSystemInfo(Units.FORCE,"FORCE",Units.FORCE)
                .addUnit(Units.FORCE, "Newtons", "N"));
        mUnitSystems.put(Units.SPEED, new UnitSystemInfo(Units.SPEED, "SPEED", SpeedUnits.METERS_PER_SECOND)
                .addUnit(SpeedUnits.METERS_PER_SECOND, "Meters per Second", "m/s")
                .addUnit(SpeedUnits.FEET_PER_SECOND, "Feet per Second", "ft/s")
        );
    }

    public Map<Integer, UnitSystemInfo> getUnitSystems() {
        return mUnitSystems;
    }

    public UnitSystemInfo getUnitSystemInfo(int unitSystem) {
        return mUnitSystems.get(Units.getBasicUnitType(unitSystem));
    }
}
