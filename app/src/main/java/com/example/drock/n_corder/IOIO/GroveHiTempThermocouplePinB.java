package com.example.drock.n_corder.IOIO;

public class GroveHiTempThermocouplePinB {
    public static IOIODeviceDriver newInstance(int pin) {
        return new GroveHiTempThermocouple(pin, true);
    }
}
