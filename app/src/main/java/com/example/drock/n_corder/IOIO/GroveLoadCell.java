package com.example.drock.n_corder.IOIO;

import com.example.drock.n_corder.units.Units;

public class GroveLoadCell extends GroveDifferentialAmplifier {
    static IOIODeviceDriver newInstance(int pinNo) {
        return new GroveLoadCell(pinNo);
    }

    public GroveLoadCell(int pinNo) {
        super(pinNo);

    }

    @Override
    protected int getUnit() {
        return Units.WEIGHT;
    }
}
