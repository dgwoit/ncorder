package com.example.drock.n_corder.IOIO;

public class GroveDifferentialAmplifier extends AnalogPinReader {
    static IOIODeviceDriver newInstance(int pinNo) {
        return new GroveDifferentialAmplifier(pinNo);
    }

    public GroveDifferentialAmplifier(int pinNo) {
        super( pinNo+1 );
    }
}
