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

public abstract class UnitConversionTable extends UnitConverter {
    @Override
    public float convert(int fromUnit, float fromValue, int toUnit) {
        assert Units.getBasicUnitType(fromUnit) == Units.getBasicUnitType(toUnit);
        assert Units.getBasicUnitType(fromUnit) == Units.getBasicUnitType(getDefaultUnit());
        float conversionFactor = getConversionTable()[Units.getSubUnitType(fromUnit)][Units.getSubUnitType(toUnit)];
        assert conversionFactor != 0;
        return fromValue * conversionFactor;
    }

    protected abstract float[][] getConversionTable();
}
