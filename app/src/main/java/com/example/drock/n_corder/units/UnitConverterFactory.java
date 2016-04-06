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

public class UnitConverterFactory {
    public static UnitConverterFactory newInstance() {
        return new UnitConverterFactory();
    }

    public UnitConverter createUnitConverter(int unit) {
        int baseUnit = Units.getBasicUnitType(unit);
        switch(baseUnit) {
            case Units.ANGLE:
                return new AngleUnitConverter();

            case Units.DISTANCE:
                return new DistanceUnitConverter();

            case Units.PRESSURE:
                return new PressureUnitConverter();

            case Units.MAGNETIC_FIELD_STRENGH:
                return new MagneticFieldUnitConverter();

            case Units.TEMPERATURE:
                return new TemperatureUnitConverter();

            default:
                return new NullUnitConverter(unit);
        }
    }
}
