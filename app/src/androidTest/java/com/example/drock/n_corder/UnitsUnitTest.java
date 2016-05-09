package com.example.drock.n_corder;

import com.example.drock.n_corder.units.AccelerationConverter;
import com.example.drock.n_corder.units.AccelerationUnits;
import com.example.drock.n_corder.units.AngleUnitConverter;
import com.example.drock.n_corder.units.AngleUnits;
import com.example.drock.n_corder.units.AngularSpeedUnits;
import com.example.drock.n_corder.units.DistanceUnitConverter;
import com.example.drock.n_corder.units.DistanceUnits;
import com.example.drock.n_corder.units.MagneticFieldUnitConverter;
import com.example.drock.n_corder.units.MagneticFieldUnits;
import com.example.drock.n_corder.units.PressureUnitConverter;
import com.example.drock.n_corder.units.PressureUnits;
import com.example.drock.n_corder.units.SpeedUnitConverter;
import com.example.drock.n_corder.units.SpeedUnits;
import com.example.drock.n_corder.units.TemperatureUnitConverter;
import com.example.drock.n_corder.units.TemperatureUnits;
import com.example.drock.n_corder.units.UnitFormatter;
import com.example.drock.n_corder.units.Units;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public class UnitsUnitTest extends TestCase {
    public void testAccelerationConversion() {
        AccelerationConverter converter = new AccelerationConverter();
        assertThat((double)converter.convert(AccelerationUnits.FEET_PER_SEC_SEC, 1, AccelerationUnits.METERS_PER_SEC_SEC), is(closeTo(0.3048, 0.001)));
        assertThat((double)converter.convert(AccelerationUnits.METERS_PER_SEC_SEC, 1, AccelerationUnits.FEET_PER_SEC_SEC), is(closeTo(3.281, 0.001)));
    }

    public void testAngleUnitConverter() {
        AngleUnitConverter converter = new AngleUnitConverter();
        assertThat((double)converter.convert(AngleUnits.DEGREES, 180, AngleUnits.RADIANS), is(closeTo(Math.PI, 0.001)));
        assertThat((double) converter.convert(AngleUnits.RADIANS, (float) -Math.PI / 4f, AngleUnits.DEGREES), is(closeTo(-45, 0.001)));
    }

    public void testAngularSpeedConverter() {
        AngleUnitConverter converter = new AngleUnitConverter();
        assertThat((double)converter.convert(AngularSpeedUnits.DEGREES_PER_SECOND, 180, AngularSpeedUnits.RADIANS_PER_SECOND), is(closeTo(Math.PI, 0.001)));
        assertThat((double)converter.convert(AngularSpeedUnits.RADIANS_PER_SECOND, (float)-Math.PI/4f, AngularSpeedUnits.DEGREES_PER_SECOND), is(closeTo(-45, 0.001)));
    }

    public void testDistanceUnitConverter() {
        DistanceUnitConverter converter = new DistanceUnitConverter();
        assertThat((double)converter.convert(DistanceUnits.METERS, 1, DistanceUnits.CENTIMETERS), is(closeTo(100, 0.0001)));
        assertThat((double)converter.convert(DistanceUnits.METERS, 1, DistanceUnits.INCHES), is(closeTo(100/2.54, 0.0001)));
        assertThat((double)converter.convert(DistanceUnits.CENTIMETERS, 100, DistanceUnits.METERS), is(closeTo(1, 0.0001)));
        assertThat((double)converter.convert(DistanceUnits.CENTIMETERS, 100, DistanceUnits.INCHES), is(closeTo(100/2.54, 0.0001)));
        assertThat((double)converter.convert(DistanceUnits.INCHES, 1, DistanceUnits.CENTIMETERS), is(closeTo(2.54, 0.0001)));
        assertThat((double)converter.convert(DistanceUnits.INCHES, 1, DistanceUnits.METERS), is(closeTo(0.0254, 0.0001)));
    }

    public void testMagneticFieldUnitConverter() {
        MagneticFieldUnitConverter converter = new MagneticFieldUnitConverter();
        assertThat((double)converter.convert(MagneticFieldUnits.TESLA, 1, MagneticFieldUnits.GAUSS), is(closeTo(1e4, 0.0001)));
        assertThat((double)converter.convert(MagneticFieldUnits.TESLA, 1, MagneticFieldUnits.MICRO_TESLA), is(closeTo(1e6, 0.0001)));
        assertThat((double)converter.convert(MagneticFieldUnits.MICRO_TESLA, 1, MagneticFieldUnits.TESLA), is(closeTo(1e-6, 0.0001)));
        assertThat((double)converter.convert(MagneticFieldUnits.MICRO_TESLA, 1, MagneticFieldUnits.GAUSS), is(closeTo(1e-2, 0.0001)));
        assertThat((double)converter.convert(MagneticFieldUnits.GAUSS, 1, MagneticFieldUnits.TESLA), is(closeTo(1e-4, 0.0001)));
        assertThat((double)converter.convert(MagneticFieldUnits.GAUSS, 1, MagneticFieldUnits.MICRO_TESLA), is(closeTo(1e2, 0.0001)));
    }

    public void testPressureUnitConverter() {
        PressureUnitConverter converter = new PressureUnitConverter();
        assertThat((double)converter.convert(PressureUnits.KILOPASCAL, 1, PressureUnits.MILLIBAR), is(closeTo(1e2, 0.0001)));
        assertThat((double)converter.convert(PressureUnits.KILOPASCAL, 1, PressureUnits.PSI), is(closeTo(0.145038, 0.0001)));
        assertThat((double)converter.convert(PressureUnits.MILLIBAR, 1, PressureUnits.KILOPASCAL), is(closeTo(0.01, 0.0001)));
        assertThat((double)converter.convert(PressureUnits.MILLIBAR, 1, PressureUnits.PSI), is(closeTo(0.0145038, 0.0001)));
        assertThat((double)converter.convert(PressureUnits.PSI, 1, PressureUnits.KILOPASCAL), is(closeTo(6.89476, 0.0001)));
        assertThat((double)converter.convert(PressureUnits.PSI, 1, PressureUnits.MILLIBAR), is(closeTo(68.9476, 0.0001)));
    }

    public void testTemperatureUnitConverter() {
        TemperatureUnitConverter converter = new TemperatureUnitConverter();
        assertThat((double)converter.convert(TemperatureUnits.KELVIN, 100, TemperatureUnits.CELSIUS), is(closeTo(-173.15, 0.0001)));
        assertThat((double)converter.convert(TemperatureUnits.KELVIN, 100, TemperatureUnits.FAHRENHEIT), is(closeTo(-279.67, 0.0001)));
        assertThat((double)converter.convert(TemperatureUnits.CELSIUS, 100, TemperatureUnits.KELVIN), is(closeTo(373.15, 0.0001)));
        assertThat((double)converter.convert(TemperatureUnits.CELSIUS, 100, TemperatureUnits.FAHRENHEIT), is(closeTo(212, 0.0001)));
        assertThat((double)converter.convert(TemperatureUnits.FAHRENHEIT, 100, TemperatureUnits.KELVIN), is(closeTo(310.928, 0.001)));
        assertThat((double)converter.convert(TemperatureUnits.FAHRENHEIT, 100, TemperatureUnits.CELSIUS), is(closeTo(37.7778, 0.0001)));
    }

    public void testSpeedUnitConverter() {
        SpeedUnitConverter converter = new SpeedUnitConverter();
        assertThat((double)converter.convert(SpeedUnits.METERS_PER_SECOND, 1, SpeedUnits.FEET_PER_SECOND), is(closeTo(3.28084, 0.0001)));
        assertThat((double)converter.convert(SpeedUnits.FEET_PER_SECOND, 1, SpeedUnits.METERS_PER_SECOND), is(closeTo(0.3048000097536, 0.0001)));
    }

    //TODO: test formatting at different scales
    public void testUnitFormatter() {
        UnitFormatter formatter = new UnitFormatter(Units.UNKNOWN);


    }
}
