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

public class TemperatureUnitConverter extends UnitConverter {
    @Override
    public int getDefaultUnit() { return TemperatureUnits.KELVIN; }

    @Override
    public float convert(int fromUnit, float fromValue, int toUnit) {
        float toValue = 0;
        final float kelvinToCelsiusAdjust = -273.15f;
        if(fromUnit == TemperatureUnits.KELVIN && toUnit == TemperatureUnits.CELSIUS)
            toValue = fromValue + kelvinToCelsiusAdjust;
        else if(fromUnit == TemperatureUnits.CELSIUS) {
            if(toUnit == TemperatureUnits.KELVIN)
                toValue = fromValue - kelvinToCelsiusAdjust;
            else if(toUnit == TemperatureUnits.FAHRENHEIT) {
                toValue = fromValue * 9f /5f + 32f;
            }
        } else if(fromUnit == TemperatureUnits.KELVIN && toUnit == TemperatureUnits.FAHRENHEIT)
            toValue = (fromValue + kelvinToCelsiusAdjust) * 9f /5f + 32f;
        else if(fromUnit == TemperatureUnits.FAHRENHEIT) {
            if(toUnit == TemperatureUnits.KELVIN)
                toValue = (fromValue - 32f) * 5f / 9f - kelvinToCelsiusAdjust;
            else if(toUnit == TemperatureUnits.CELSIUS)
                toValue = (fromValue - 32f) * 5f / 9f;
        } else if(fromUnit == toUnit)
            toValue = fromValue;
        else
            toValue = 0;

        return toValue;
    }
}
