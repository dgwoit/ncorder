package com.example.drock.n_corder;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.drock.n_corder.units.AccelerationUnits;
import com.example.drock.n_corder.units.AngleUnits;
import com.example.drock.n_corder.units.DisplayUnitFormatter;
import com.example.drock.n_corder.units.DistanceUnits;
import com.example.drock.n_corder.units.MagneticFieldUnits;
import com.example.drock.n_corder.units.PressureUnits;
import com.example.drock.n_corder.units.SpeedUnits;
import com.example.drock.n_corder.units.TemperatureUnits;
import com.example.drock.n_corder.units.TimeUnitFormatter;
import com.example.drock.n_corder.units.TimeUnits;
import com.example.drock.n_corder.units.UnitFormatter;
import com.example.drock.n_corder.units.Units;

import java.util.Map;
import java.util.TreeMap;

public class DisplayUnitManager {
    static Map<Integer, Setting> mDisplayUnits;
    Context mContext;

    class Setting {
        Setting(int defaultValue, String settingKey) {
            value = new ObservableInteger(defaultValue);
            key = settingKey;
        }

        public ObservableInteger value;
        String key;
    }

    DisplayUnitManager(Context context) {
        mContext = context;

        if(null == mDisplayUnits) {
            mDisplayUnits = new TreeMap<>();
            mDisplayUnits.put(Units.UNKNOWN, new Setting(Units.UNKNOWN, "unit_unknown"));
            mDisplayUnits.put(Units.ACCELERATION, new Setting(AccelerationUnits.METERS_PER_SEC_SEC, "unit_acceleration"));
            mDisplayUnits.put(Units.ANGLE, new Setting(AngleUnits.DEGREES, "unit_angle"));
            mDisplayUnits.put(Units.ANGULAR_SPEED, new Setting(Units.ANGULAR_SPEED, "unit_speed"));
            mDisplayUnits.put(Units.CURRENT, new Setting(Units.CURRENT, "unit_current"));
            mDisplayUnits.put(Units.DISTANCE, new Setting(DistanceUnits.CENTIMETERS, "unit_distance"));
            mDisplayUnits.put(Units.HEART_RATE, new Setting(Units.HEART_RATE, "unit_heart_rate"));
            mDisplayUnits.put(Units.ILLUMINANCE, new Setting(Units.ILLUMINANCE, "unit_illuminance"));
            mDisplayUnits.put(Units.MAGNETIC_FIELD_STRENGH, new Setting(MagneticFieldUnits.MICRO_TESLA, "unit_magnetic_field"));
            mDisplayUnits.put(Units.MASS, new Setting(Units.MASS, "unit_mass"));
            mDisplayUnits.put(Units.PRESSURE, new Setting(PressureUnits.MILLIBAR, "unit_pressure"));
            mDisplayUnits.put(Units.RELATIVE_HUMIDITY, new Setting(Units.RELATIVE_HUMIDITY, "unit_humidity"));
            mDisplayUnits.put(Units.STEPS, new Setting(Units.STEPS, "unit_steps"));
            mDisplayUnits.put(Units.TEMPERATURE, new Setting(TemperatureUnits.CELSIUS, "unit_temperature"));
            mDisplayUnits.put(Units.TIME, new Setting(TimeUnits.SECONDS, "unit_time"));
            mDisplayUnits.put(Units.WEIGHT, new Setting(Units.WEIGHT, "unit_weight"));
            mDisplayUnits.put(Units.SPEED, new Setting(SpeedUnits.METERS_PER_SECOND, "unit_speed"));
            mDisplayUnits.put(Units.FORCE, new Setting(Units.FORCE, "unit_force"));
            mDisplayUnits.put(Units.VOLTAGE, new Setting(Units.VOLTAGE, "unit_voltage"));
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        for(Setting setting: mDisplayUnits.values()) {
            setting.value.mValue = preferences.getInt(setting.key, setting.value.intValue());
        }
    }

    Map<Integer, Setting> getDisplayUnits() {
        return mDisplayUnits;
    }

    int getDisplayUnit(int unitSystem) {
        return mDisplayUnits.get(Units.getBasicUnitType(unitSystem)).value.intValue();
    }

    UnitFormatter getUnitFormatter(int unitType) {
        int unitSystem = Units.getBasicUnitType(unitType);
        int displayUnitType = getDisplayUnit(unitSystem);
        DisplayUnitFormatter unitFormatter;
        if(unitSystem == Units.TIME)
            unitFormatter = new TimeUnitFormatter(displayUnitType);
        else {
            unitFormatter = new DisplayUnitFormatter(displayUnitType);
            unitFormatter.setSignificantDigits(getSignificantDigits());
        }
        mDisplayUnits.get(unitSystem).value.addObserver(unitFormatter);
        return unitFormatter;
    }

    void setDisplayUnit(int unitSystem, int unit) {
        Setting setting = mDisplayUnits.get(unitSystem);
        setting.value.set(unit);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        preferences.edit().putInt(setting.key, unit).commit();
    }

    int getSignificantDigits() {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            int significantDigits = Integer.parseInt(preferences.getString("pref_significant_digits", "3"));
            return significantDigits;
        } catch(Exception ex) {
            Log.e("DisUnitMgr", ex.toString());
        }

        return 3;
    }
}
