package com.example.drock.n_corder.IOIO;

import com.example.drock.n_corder.units.Units;

import ioio.lib.api.exception.ConnectionLostException;

public class AnalogVoltageReader extends AnalogPinReader {
    static IOIODeviceDriver newInstance(int pinNo) {
        return new AnalogVoltageReader(pinNo);
    }

    public AnalogVoltageReader(int pinNo) {
        super(pinNo);
    }

    @Override
    public float Read() throws ConnectionLostException, InterruptedException {
        //voltage at pin is 0-5 volt range.  Reading returned via IOIO lib is normalized the 0-1 range
        return 5.0f*super.Read();
    }

    @Override
    protected int getUnit() { return Units.VOLTAGE; }
}
