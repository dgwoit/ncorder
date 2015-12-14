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

class SeeedDifferentialAmplifier extends AnalogPinReader {
    public SeeedDifferentialAmplifier(int pinNo) {
        super( pinNo+1 );
    }
}

class SeeedHiTempThermocouple extends IOIODeviceDriver implements IMeasurementSource {
    AnalogPinReader mAmbientTempSensor;
    AnalogPinReader mHiTempSensor;
    protected MeasurementSource mDispatcher = new MeasurementSource();

    public SeeedHiTempThermocouple(int pinNo) {
        mAmbientTempSensor = new AnalogPinReader(pinNo);
        mHiTempSensor = new AnalogPinReader(pinNo+1);
    }

    @Override public void Realize(IOIO ioio) throws ConnectionLostException {
        mAmbientTempSensor.Realize(ioio);
        mHiTempSensor.Realize(ioio);
    }

    @Override public void Update() {
        try {
            mAmbientTempSensor.Read();
            float value = mHiTempSensor.Read();
            update(new Measurement(value));
        }
        catch(ConnectionLostException e) {

        }
        catch(InterruptedException e){

        }
        finally {

        }
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