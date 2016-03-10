package com.example.drock.n_corder.IOIO;

import com.example.drock.n_corder.units.Units;

import ioio.lib.api.exception.ConnectionLostException;

public class ACS712CurrentSensor extends AnalogPinReader {
    LowPassFilter mFilter = new LowPassFilter();
    final static float READING_BIAS = 0.4894f;

    static IOIODeviceDriver newInstance(int pinNo) {
        return new ACS712CurrentSensor(pinNo);
    }

    ACS712CurrentSensor(int pinNo) {
        super(pinNo + 1);
        mFilter.setAlpha(0.1f);
        mFilter.setState(READING_BIAS);
    }

    @Override
    protected int getUnit() { return Units.CURRENT; }

    @Override
    public float Read() throws ConnectionLostException, InterruptedException {
        float value = super.Read();
        value = mFilter.filter(value);
        //return value;

        // value has a positive bias of about 0.5
        // input voltage from sensor is in the range of 0-5 volts
        // .185 volts per AMP
        float current = (value - READING_BIAS) * 5.0f / 0.185f;
        return current;
    }
}
