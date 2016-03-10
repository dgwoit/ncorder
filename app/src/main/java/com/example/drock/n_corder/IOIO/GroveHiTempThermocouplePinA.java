package com.example.drock.n_corder.IOIO;

// used to create driver for reading high temp probe
public class GroveHiTempThermocouplePinA {
    public static IOIODeviceDriver newInstance(int pin) {
        return new GroveHiTempThermocouple(pin, false);
    }
}
