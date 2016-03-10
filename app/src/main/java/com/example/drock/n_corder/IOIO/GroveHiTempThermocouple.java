package com.example.drock.n_corder.IOIO;

import com.example.drock.n_corder.IMeasurementSink;
import com.example.drock.n_corder.IMeasurementSource;
import com.example.drock.n_corder.Measurement;
import com.example.drock.n_corder.MeasurementSource;
import com.example.drock.n_corder.units.TemperatureUnits;
import com.example.drock.n_corder.units.UnitConverter;
import com.example.drock.n_corder.units.UnitConverterFactory;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;

public class GroveHiTempThermocouple extends IOIODeviceDriver implements IMeasurementSource {
    AnalogPinReader mAmbientTempSensor;
    AnalogPinReader mHiTempSensor;
    boolean mDefaultToAmbient = false;
    protected MeasurementSource mDispatcher = new MeasurementSource();
    LowPassFilter mFilter = new LowPassFilter();
    UnitConverter mUnitConverter;

    public GroveHiTempThermocouple(int pinNo, boolean ambient) {
        mAmbientTempSensor = new AnalogPinReader(pinNo);
        mHiTempSensor = new AnalogPinReader(pinNo+1);
        mDefaultToAmbient = ambient;
        mFilter.setAlpha(1.0f);
        UnitConverterFactory f = UnitConverterFactory.newInstance();
        mUnitConverter = f.createUnitConverter(TemperatureUnits.CELSIUS);
    }

    @Override public void Realize(IOIO ioio) throws ConnectionLostException {
        mAmbientTempSensor.Realize(ioio);
        mHiTempSensor.Realize(ioio);
    }

    @Override public void Update() {
        try {
            float value;
            if(mDefaultToAmbient)
                value = mAmbientTempSensor.Read()*100f;
            else {
                value = getHiTemp();
            }
            value = mUnitConverter.convert(TemperatureUnits.CELSIUS, value);
            update(new Measurement(value, System.nanoTime(), mUnitConverter.getDefaultUnit()));
        }
        catch(Exception e) {

        }
        finally {

        }
    }

    protected static final float VOL_OFFSET = 350f;
    protected static final float AMP_AV     = 54.16f;

    public float getHiTemp() throws Exception {
        float value = mFilter.filter(mHiTempSensor.Read());
        float mV = value/1023.0f*5.0f*1000f; //convert value to millivolts
        mV = (mV - VOL_OFFSET)/AMP_AV; //normlize value range

        float t = 0f;
        if(mV < 0)
        {
            t = mV * -5.1920577e-4f + -1.0450598e-2f;
            t = mV * t + -8.6632643e-2f;
            t = mV * t + -3.7342377e-1f;
            t = mV * t + -8.9773540e-1f;
            t = mV * t + -1.0833638f;
            t = mV * t + -1.1662878f;
            t = mV * t + 2.5173462e1f;
            t = mV * t + 0;
        }
        else if(mV < 20.644)
        {
            t = mV * -1.052755e-8f + 1.057734e-6f;
            t = mV * t + -4.413030e-5f;
            t = mV * t + 9.804036e-4f;
            t = mV * t + -1.228034e-2f;
            t = mV * t + 8.315270e-2f;
            t = mV * t + -2.503131e-1f;
            t = mV * t + 7.860106e-2f;
            t = mV * t + 2.508355e1f;
            t = mV * t + 0;
        }
        else {
            t = mV * -3.110810e-8f + 8.802193e-6f;
            t = mV * t + -9.650715e-4f;
            t = mV * t + 5.464731e-2f;
            t = mV * t + -1.646031f;
            t = mV * t + 4.830222e1f;
            t = mV * t + -1.318058e2f;
        }

        return t;
    }

    public void attach(IMeasurementSink sink) {
        mDispatcher.attach(sink);
    }
    public void detach(IMeasurementSink sink) {
        mDispatcher.detach(sink);
    }
    public void update(Measurement m) {
        mDispatcher.update(m);
    }
}
