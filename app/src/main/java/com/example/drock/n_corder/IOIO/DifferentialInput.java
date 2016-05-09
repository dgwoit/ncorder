package com.example.drock.n_corder.IOIO;


import com.example.drock.n_corder.IMeasurementSink;
import com.example.drock.n_corder.IMeasurementSource;
import com.example.drock.n_corder.Measurement;
import com.example.drock.n_corder.MeasurementSource;
import com.example.drock.n_corder.SystemFactoryBroker;
import com.example.drock.n_corder.units.UnitConverter;
import com.example.drock.n_corder.units.Units;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;

public class DifferentialInput extends IOIODeviceDriver implements IMeasurementSource {
    protected MeasurementSource mDispatcher = new MeasurementSource();
    protected AnalogPinReader mPinA;
    protected AnalogPinReader mPinB;
    protected UnitConverter mUnitConverter;
    protected int mSamplesPerMeasurement = 100;

    public static IOIODeviceDriver newInstance(int basePin) {
        return new DifferentialInput(basePin);
    }

    public DifferentialInput(int basePin) {
        mPinA = new AnalogVoltageReader(basePin);
        mPinB = new AnalogVoltageReader(basePin+1);
        mUnitConverter = SystemFactoryBroker.getSystemFactory().getUnitConverterFactory().createUnitConverter(Units.VOLTAGE);
    }

    //IMeasurementSource implementations
    public void attach(IMeasurementSink sink) {
        mDispatcher.attach(sink);
    }
    public void detach(IMeasurementSink sink) {
        mDispatcher.detach(sink);
    }
    public void update(Measurement m) {
        mDispatcher.update(m);
    }


    @Override
    public void Realize(IOIO ioio) throws ConnectionLostException {
        mPinA.Realize(ioio);
        mPinB.Realize(ioio);
    }

    @Override
    public void Update() {
        try {
            float valueA = 0, valueB = 0;
            for(int i = 0; i < mSamplesPerMeasurement; i++) {
                valueA = mPinA.Read();
                valueB = mPinB.Read();
            }
            float difference = valueA - valueB;

            float value = mUnitConverter.convert(Units.VOLTAGE, difference);
            update(new Measurement(value, System.nanoTime(), mUnitConverter.getDefaultUnit()));
        }
        catch(Exception e) {

        }
        finally {

        }
    }
}
