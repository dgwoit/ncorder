/*
* THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.example.drock.n_corder.android;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.example.drock.n_corder.Measurement;
import com.example.drock.n_corder.MeasurementFactory;
import com.example.drock.n_corder.MeasurementSource;
import com.example.drock.n_corder.SystemFactoryBroker;
import com.example.drock.n_corder.units.UnitConverter;
import com.example.drock.n_corder.units.UnitConverterFactory;

public class AndroidSensorEventAdapter extends MeasurementSource implements SensorEventListener {
    int mValueSelector;
    int mFromUnit;
    UnitConverter mUnitConverter;
    MeasurementFactory mMeasurementFactory;

    public AndroidSensorEventAdapter(int valueSelector, int unit) {
        mValueSelector = valueSelector;
        mFromUnit = unit;
        mUnitConverter = UnitConverterFactory.newInstance().createUnitConverter(unit);
        mMeasurementFactory = SystemFactoryBroker.getSystemFactory().getMeasurementFactory();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Measurement m = CreateMeasurement(event);
        update(m);
    }

    protected Measurement CreateMeasurement(SensorEvent event) {
        float value = event.values[mValueSelector];
        value = mUnitConverter.convert(mFromUnit, value);
        return mMeasurementFactory.createMeasurement(value, mUnitConverter.getDefaultUnit(), event.timestamp);
    }
}
