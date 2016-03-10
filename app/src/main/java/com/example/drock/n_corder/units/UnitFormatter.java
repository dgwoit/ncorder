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
    int mUnit;
    String mSuffix;

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

        mSuffix = mSuffixes.get(Units.getBasicUnitType(unit)).getSuffix(unit);
    }

    public String format(float v) {
        return String.format("%f %s", v, mSuffix);
    }

    public String format(long v) {
        return String.format("%d %s", v, mSuffix);
    }
}
