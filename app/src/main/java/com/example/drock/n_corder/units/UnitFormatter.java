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
    Map<Integer, String> mSuffixes = new HashMap<Integer, String>();
    int mUnit;
    String mSuffix;

    public UnitFormatter(int unit) {
        mUnit = unit;
        mSuffixes.put(Units.UNKNOWN, "");
        mSuffixes.put(Units.ACCELERATION, "m/s^2");
        mSuffixes.put(Units.ANGLE, "rad");
        mSuffixes.put(Units.MAGNETIC_FIELD_STRENGH, "T");
        mSuffixes.put(Units.ANGULAR_SPEED, "rad/s");
        mSuffixes.put(Units.PRESSURE, "Pa");
        mSuffixes.put(Units.DISTANCE, "m");
        mSuffixes.put(Units.RELATIVE_HUMIDITY, "%");
        mSuffixes.put(Units.TEMPERATURE, "K");
        mSuffixes.put(Units.HEART_RATE, "BPM");
        mSuffixes.put(Units.ILLUMINANCE, "lx");
        mSuffixes.put(Units.STEPS, "steps");
        mSuffixes.put(Units.CURRENT, "A");
        mSuffixes.put(Units.MASS, "kg");
        mSuffixes.put(Units.WEIGHT, "N");
        mSuffixes.put(Units.TIME, "s");

        mSuffix = mSuffixes.get(Units.getBasicUnitType(unit));
    }

    public String format(float v) {
        return String.format("%f %s", v, mSuffix);
    }

    public String format(long v) {
        return String.format("%d %s", v, mSuffix);
    }
}
