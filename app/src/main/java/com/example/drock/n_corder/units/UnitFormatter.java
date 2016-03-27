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
package com.example.drock.n_corder.units;

import java.util.HashMap;
import java.util.Map;


public class UnitFormatter {
    Map<Integer, IUnitSuffixer> mSuffixes = new HashMap<>();
    Map<Integer, String> mUnitNames = new HashMap<>();
    Map<Integer, String> mUnitSystemNames = new HashMap<>();
    int mUnit;
    String mSuffix;
    String mUnitName;
    String mUnitSystemName;

    //this probably should be turned into a factor for turning out textual unit information
    public UnitFormatter(int unit) {
        mUnit = unit;
        mSuffixes.put(Units.UNKNOWN, new DefaultUnitSuffixer(""));
        mSuffixes.put(Units.ACCELERATION, new DefaultUnitSuffixer("m/s^2"));
        mSuffixes.put(Units.ANGLE, new DefaultUnitSuffixer("rad"));
        mSuffixes.put(Units.MAGNETIC_FIELD_STRENGH, new MagneticFieldUnitSuffixer());
        mSuffixes.put(Units.ANGULAR_SPEED, new DefaultUnitSuffixer("rad/s"));
        mSuffixes.put(Units.PRESSURE, new DefaultUnitSuffixer("Pa"));
        mSuffixes.put(Units.DISTANCE, new DefaultUnitSuffixer("m"));
        mSuffixes.put(Units.RELATIVE_HUMIDITY, new DefaultUnitSuffixer("%"));
        mSuffixes.put(Units.TEMPERATURE, new DefaultUnitSuffixer("K"));
        mSuffixes.put(Units.HEART_RATE, new DefaultUnitSuffixer("BPM"));
        mSuffixes.put(Units.ILLUMINANCE, new DefaultUnitSuffixer("lx"));
        mSuffixes.put(Units.STEPS, new DefaultUnitSuffixer("steps"));
        mSuffixes.put(Units.CURRENT, new DefaultUnitSuffixer("A"));
        mSuffixes.put(Units.MASS, new DefaultUnitSuffixer("kg"));
        mSuffixes.put(Units.WEIGHT, new DefaultUnitSuffixer("N"));
        mSuffixes.put(Units.TIME, new DefaultUnitSuffixer("s"));

        mUnitNames.put(Units.UNKNOWN, "UNKNOWN UNIT");
        mUnitNames.put(Units.ACCELERATION, "METERS PER SECOND PER SECOND");
        mUnitNames.put(Units.ANGLE, "RADIANS");
        mUnitNames.put(Units.MAGNETIC_FIELD_STRENGH, "TESLA");
        mUnitNames.put(Units.ANGULAR_SPEED, "RADIANS PER SECOND");
        mUnitNames.put(Units.PRESSURE, "PASCALS");
        mUnitNames.put(Units.DISTANCE, "METERS");
        mUnitNames.put(Units.RELATIVE_HUMIDITY, "% HUMIDITY");
        mUnitNames.put(Units.TEMPERATURE, "KELVIN");
        mUnitNames.put(Units.HEART_RATE, "BEATS PER MINUTE");
        mUnitNames.put(Units.ILLUMINANCE, "LUMENS");
        mUnitNames.put(Units.STEPS, "STEPS");
        mUnitNames.put(Units.CURRENT, "AMPS");
        mUnitNames.put(Units.MASS, "KILOGRAMS");
        mUnitNames.put(Units.WEIGHT, "NEWTONS");
        mUnitNames.put(Units.TIME, "SECONDS");

        mUnitSystemNames.put(Units.UNKNOWN, "UNKNOWN");
        mUnitSystemNames.put(Units.ACCELERATION, "ACCELERATION");
        mUnitSystemNames.put(Units.ANGLE, "ANGLE");
        mUnitSystemNames.put(Units.MAGNETIC_FIELD_STRENGH, "MAGNETIC FIELD STRENGH");
        mUnitSystemNames.put(Units.ANGULAR_SPEED, "ANGULARE SPEED");
        mUnitSystemNames.put(Units.PRESSURE, "PRESSURE");
        mUnitSystemNames.put(Units.DISTANCE, "DISTANCE");
        mUnitSystemNames.put(Units.RELATIVE_HUMIDITY, "RELATIVE HUMIDITY");
        mUnitSystemNames.put(Units.TEMPERATURE, "TEMPERATURE");
        mUnitSystemNames.put(Units.HEART_RATE, "HEART RATE");
        mUnitSystemNames.put(Units.ILLUMINANCE, "ILLUMINANCE");
        mUnitSystemNames.put(Units.STEPS, "STEPS");
        mUnitSystemNames.put(Units.CURRENT, "CURRENT");
        mUnitSystemNames.put(Units.MASS, "MASS");
        mUnitSystemNames.put(Units.WEIGHT, "WEIGHT");
        mUnitSystemNames.put(Units.TIME, "TIME");

        mSuffix = mSuffixes.get(Units.getBasicUnitType(unit)).getSuffix(unit);
        mUnitName = mUnitNames.get(Units.getBasicUnitType(unit));
        mUnitSystemName = mUnitSystemNames.get(Units.getBasicUnitType(unit));
    }

    public String format(float v) {
        return String.format("%f %s", v, mSuffix);
    }

    public String format(long v) {
        return String.format("%d %s", v, mSuffix);
    }
    
    public String getUnitName() { return mUnitName; }
    public String getUnitSystemName() { return mUnitSystemName; }
}
