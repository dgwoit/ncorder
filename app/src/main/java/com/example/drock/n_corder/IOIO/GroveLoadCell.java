package com.example.drock.n_corder.IOIO;

import com.example.drock.n_corder.Measurement;
import com.example.drock.n_corder.units.TemperatureUnits;
import com.example.drock.n_corder.units.Units;

import ioio.lib.api.exception.ConnectionLostException;

public class GroveLoadCell extends GroveDifferentialAmplifier {
    LowPassFilter mFilter = new LowPassFilter();
    float mTare = 0;

    static IOIODeviceDriver newInstance(int pinNo) {
        return new GroveLoadCell(pinNo);
    }

    public GroveLoadCell(int pinNo) {
        super(pinNo);
        mFilter.setAlpha(1.0f);
    }

    @Override
    protected int getUnit() {
        return Units.WEIGHT;
    }

    @Override
    public float Read() throws ConnectionLostException, InterruptedException {
        float value = 0;
        for(int i = 0; i < 100; i++)
            value += super.Read();
        if(mTare == 0 || value < mTare)
            mTare = value;
        value -= mTare;
        //value = mFilter.filter(value);
        return value;
    }
}
