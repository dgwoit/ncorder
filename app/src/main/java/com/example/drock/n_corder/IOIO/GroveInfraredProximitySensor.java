package com.example.drock.n_corder.IOIO;

import com.example.drock.n_corder.units.DistanceUnits;

import ioio.lib.api.exception.ConnectionLostException;

public class GroveInfraredProximitySensor extends AnalogPinReader {
    final static float VREF = 5f;

    static IOIODeviceDriver newInstance(int pinNo) {
        return new GroveInfraredProximitySensor(pinNo);
    }

    GroveInfraredProximitySensor(int pinNo) {
        super(pinNo);
    }

    @Override
    public int getUnit() { return DistanceUnits.METERS; }

    @Override
    public float Read() throws ConnectionLostException, InterruptedException {
        float value = super.Read();
        float voltage = value * VREF;
        float distance = 0.29988f * (float)Math.pow(voltage , -1.173);
        return distance;
    }
}
