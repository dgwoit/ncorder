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

import com.example.drock.n_corder.ISystemFactory;
import com.example.drock.n_corder.SystemFactoryBroker;

import java.util.HashMap;
import java.util.Map;


public class UnitFormatter implements IUnitFormatter {
    protected int mUnit;
    protected int mSystemUnit;
    protected String mSuffix;
    protected String mUnitName;
    protected String mUnitSystemName;
    protected UnitConverter mUnitConverter;
    protected int mSignificantDigits;

    //this probably should be turned into a factor for turning out textual unit information
    public UnitFormatter(int unit) {
        init(unit);
    }

    public void init(int unit) {
        mUnit = unit;
        ISystemFactory systemFactory = SystemFactoryBroker.getSystemFactory();
        UnitSystemTable unitSystemTable = systemFactory.getUnitSystemTable();
        UnitSystemInfo unitSystemInfo = unitSystemTable.getUnitSystemInfo(mUnit);
        UnitTypeInfo unitTypeInfo = unitSystemInfo.getUnitTypeInfo(unit);
        mSuffix = unitTypeInfo.getSuffix();
        mUnitName = unitTypeInfo.getName();
        mUnitSystemName = unitSystemInfo.getUnitSystemName();
        mUnitConverter = systemFactory.getUnitConverterFactory().createUnitConverter(unit);
        mSystemUnit = unitSystemInfo.getSystemUnit();
        mSignificantDigits = 3;
    }

    public void setSignificantDigits(int digits) {mSignificantDigits = digits;}

    public String formatSystem(float v) {
        return format(mUnitConverter.convert(mSystemUnit, v, mUnit));
    }

    @Override
    public String format(float v) {
        String str;
        if(v == 0) { //can't use log function on 0
            str = String.format("%." + (mSignificantDigits-1) +"f %s", v, mSuffix);
        } else {
            float original = v;
            int power = (int) (Math.log10(Math.abs(v)));
            float powerFactor = (float) Math.pow(10, -power + mSignificantDigits);
            v = (float) (v * powerFactor);
            v = Math.round(v);
            v = (float) (v / powerFactor);
            if (power >= 0) {
                if (power <= 0)
                    str = String.format("%." + (mSignificantDigits - power) + "f %s", v, mSuffix);
                else if (power < mSignificantDigits) {
                    str = String.format("%" + (power + 1) + "." + (mSignificantDigits - power - 1) + "f %s", v, mSuffix);
                } else
                    str = String.format("%." + (mSignificantDigits - 1) + "E %s", v, mSuffix);
            } else {
                str = String.format("%." + (mSignificantDigits - 1) + "E %s", v, mSuffix);
                //str = String.format("%."+(mSignificantDigits-power)+"f %s", v, mSuffix);
            }
        }

        return str;
    }

    @Override
    public String format(long v) {
        return String.format("%d %s", v, mSuffix);
    }
    public int getUnit() { return mUnit; }
    public String getUnitName() { return mUnitName; }
    public String getUnitSystemName() { return mUnitSystemName; }
}
