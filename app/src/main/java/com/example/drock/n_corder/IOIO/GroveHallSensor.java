package com.example.drock.n_corder.IOIO;

import com.example.drock.n_corder.units.DistanceUnits;
import com.example.drock.n_corder.units.MagneticFieldUnits;

import ioio.lib.api.exception.ConnectionLostException;

public class GroveHallSensor extends AnalogPinReader {
        static IOIODeviceDriver newInstance(int pinNo) {
            return new GroveHallSensor(pinNo);
        }

        GroveHallSensor(int pinNo) {
            super(pinNo+1);
        }

        @Override
        public int getUnit() { return MagneticFieldUnits.UNKNOWN; }

        @Override
        public float Read() throws ConnectionLostException, InterruptedException {
            float value = super.Read();
            return value;
        }
}
