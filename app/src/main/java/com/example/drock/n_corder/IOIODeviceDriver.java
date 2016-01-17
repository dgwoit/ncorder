package com.example.drock.n_corder;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;

/**
 * Created by drock on 11/27/2015.
 */
public abstract class IOIODeviceDriver {
    public abstract void Realize(IOIO ioio) throws ConnectionLostException;

    public abstract void Update();
}

class AnalogPinReader extends IOIODeviceDriver implements IMeasurementSource
{
    protected AnalogInput mPin;
    protected int mPinNo;
    protected MeasurementSource dispatcher = new MeasurementSource();

    public AnalogPinReader(int pinNo) {
        this.mPinNo = pinNo;
    }

    //measurement publisher methods


    @Override
    public void attach(IMeasurementSink sink) {
        dispatcher.attach(sink);
    }

    @Override
    public void detach(IMeasurementSink sink) {
        dispatcher.detach(sink);
    }

    @Override
    public void update(Measurement measurement) {
        dispatcher.update(measurement);
    }

    @Override
     public void Realize(IOIO ioio) throws ConnectionLostException {
         mPin = ioio.openAnalogInput(this.mPinNo);
     }

    public float Read() throws ConnectionLostException, InterruptedException {
        return mPin.read();
    }

    @Override
    public void Update() {
        try {
            float value = Read();
            update(new Measurement(value));
        }
        catch(ConnectionLostException e) {

        }
        catch(InterruptedException e){

        }
        finally {

        }
    }
}

class GroveDifferentialAmplifier extends AnalogPinReader {
    public GroveDifferentialAmplifier(int pinNo) {
        super( pinNo+1 );
    }
}

class LowPassFilter {
    float mAlpha = 1.0f; //1.0 = no filtering
    float mY = 0;

    public void setAlpha(float value) {
        mAlpha = value;
    }

    public float filter(float x) {
        mY = mAlpha * x - (1 - mAlpha) * mY;
        return mY;
    }
}

class GroveHiTempThermocouple extends IOIODeviceDriver implements IMeasurementSource {
    AnalogPinReader mAmbientTempSensor;
    AnalogPinReader mHiTempSensor;
    boolean mDefaultToAmbient = false;
    protected MeasurementSource mDispatcher = new MeasurementSource();
    LowPassFilter mFilter = new LowPassFilter();

    public GroveHiTempThermocouple(int pinNo, boolean ambient) {
        mAmbientTempSensor = new AnalogPinReader(pinNo);
        mHiTempSensor = new AnalogPinReader(pinNo+1);
        mDefaultToAmbient = ambient;
        mFilter.setAlpha(1.0f);
    }

    @Override public void Realize(IOIO ioio) throws ConnectionLostException {
        mAmbientTempSensor.Realize(ioio);
        mHiTempSensor.Realize(ioio);
    }

    @Override public void Update() {
        try {
            float value;
            if(mDefaultToAmbient)
                value = mAmbientTempSensor.Read();
            else {
                value = getHiTemp();
            }
            update(new Measurement(value));
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

class GroveLoadCell extends GroveDifferentialAmplifier {
    public GroveLoadCell(int pinNo) {
        super(pinNo);
    }
}