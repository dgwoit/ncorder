package com.example.drock.n_corder.IOIO;

import com.example.drock.n_corder.IMeasurementSink;
import com.example.drock.n_corder.IMeasurementSource;
import com.example.drock.n_corder.Measurement;
import com.example.drock.n_corder.MeasurementSource;
import com.example.drock.n_corder.units.UnitConverter;
import com.example.drock.n_corder.units.UnitConverterFactory;
import com.example.drock.n_corder.units.Units;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;

public class AnalogPinReader extends IOIODeviceDriver implements IMeasurementSource
{
    protected AnalogInput mPin;
    protected int mPinNo;
    protected MeasurementSource dispatcher = new MeasurementSource();
    protected UnitConverter mUnitConverter;

    static IOIODeviceDriver newInstance(int pinNo) {
        return new AnalogPinReader(pinNo);
    }

    public AnalogPinReader(int pinNo) {
        this.mPinNo = pinNo;
    }

    //measurement publisher methods
    protected int getUnit() {
        return Units.UNKNOWN;
    }

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
        mUnitConverter = UnitConverterFactory.newInstance().createUnitConverter(getUnit());
    }

    public float Read() throws ConnectionLostException, InterruptedException {
        return mPin.read();
    }

    @Override
    public void Update() {
        try {
            float value = Read();
            if(Units.getSubUnitType(getUnit()) != 0) {
                value = mUnitConverter.convert(getUnit(), value);
                update(new Measurement(value, System.nanoTime(), mUnitConverter.getDefaultUnit()));
            } else { //unit type, unit sub-type unknown - no conversion
                update(new Measurement(value, System.nanoTime(), getUnit()));
            }
        }
        catch(ConnectionLostException e) {

        }
        catch(InterruptedException e){

        }
        finally {

        }
    }
}
