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

import com.example.drock.n_corder.android.AndroidSensorHelper;
import com.example.drock.n_corder.units.AngleUnits;
import com.example.drock.n_corder.units.DistanceUnits;
import com.example.drock.n_corder.units.MagneticFieldUnits;
import com.example.drock.n_corder.units.PressureUnits;
import com.example.drock.n_corder.units.TemperatureUnits;
import com.example.drock.n_corder.units.Units;

public class AndroidSensorHelperTable {
    private AndroidSensorHelper[] mHelpers;

    AndroidSensorHelperTable() {
        Init();
    }

    void Init() {
        String[] xyz = {"x", "y", "z"};
        mHelpers = new AndroidSensorHelper[] {
                new AndroidSensorHelper(Sensor.TYPE_ACCELEROMETER, new String[]{"x acceleration", "y acceleration", "z acceleration"}, Units.ACCELERATION),
                new AndroidSensorHelper(Sensor.TYPE_AMBIENT_TEMPERATURE, new String[]{"ambient temperature"}, TemperatureUnits.CELSIUS),
                new AndroidSensorHelper(Sensor.TYPE_GAME_ROTATION_VECTOR, new String[]{"rotation vector x", "rotation vector y", "rotation vector z"}, Units.ANGULAR_SPEED),
                new AndroidSensorHelper(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, xyz, Units.ANGULAR_SPEED),
                new AndroidSensorHelper(Sensor.TYPE_GRAVITY, xyz, Units.ACCELERATION),
                new AndroidSensorHelper(Sensor.TYPE_GYROSCOPE, xyz, AngleUnits.RADIANS),
                new AndroidSensorHelper(Sensor.TYPE_GYROSCOPE_UNCALIBRATED, xyz, AngleUnits.RADIANS),
                new AndroidSensorHelper(Sensor.TYPE_HEART_RATE, xyz, Units.HEART_RATE),
                new AndroidSensorHelper(Sensor.TYPE_LIGHT, new String[]{"ambient light level"}, Units.ILLUMINANCE),
                new AndroidSensorHelper(Sensor.TYPE_LINEAR_ACCELERATION, xyz, Units.ACCELERATION),
                new AndroidSensorHelper(Sensor.TYPE_MAGNETIC_FIELD, xyz, MagneticFieldUnits.MICRO_TESLA),
                new AndroidSensorHelper(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, new String[]{"x uncalibrated", "y uncalibrated", "z uncalibrated", "x bias", "y bias", "z bias"}, Units.MAGNETIC_FIELD_STRENGH),
                new AndroidSensorHelper(Sensor.TYPE_ORIENTATION, xyz, AngleUnits.DEGREES),
                new AndroidSensorHelper(Sensor.TYPE_PRESSURE, new String[]{"pressure"}, PressureUnits.MILLIBAR),
                new AndroidSensorHelper(Sensor.TYPE_PROXIMITY, new String[]{"proximity"}, DistanceUnits.CENTIMETERS),
                new AndroidSensorHelper(Sensor.TYPE_RELATIVE_HUMIDITY, new String[]{"humidity"}, Units.RELATIVE_HUMIDITY),
                new AndroidSensorHelper(Sensor.TYPE_ROTATION_VECTOR, xyz, Units.UNKNOWN), //unit vector
                new AndroidSensorHelper(Sensor.TYPE_SIGNIFICANT_MOTION, new String[]{"significant motion"}, Units.UNKNOWN),
                new AndroidSensorHelper(Sensor.TYPE_STEP_COUNTER, new String[]{"steps"}, Units.STEPS),
        };
    }

    AndroidSensorHelper getEntry(int sensorType) {
        for(AndroidSensorHelper helper : mHelpers) {
            if(helper.getSensorType() == sensorType)
                return helper;
        }

        return null;
    }
}
