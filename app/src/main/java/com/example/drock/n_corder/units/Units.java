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

public class Units {
    //we are going for SI units here
    public static final int UNKNOWN = 0; //unknown or other dimensionless unit
    public static final int ACCELERATION = 1; //meters per second second
    public static final int MAGNETIC_FIELD_STRENGH = 2; //tesla
    public static final int ANGULAR_SPEED = 3; //radians per second
    public static final int PRESSURE = 4; //Pascals
    public static final int DISTANCE = 5; //Meters
    public static final int ANGLE = 6; //radians
    public static final int RELATIVE_HUMIDITY = 7; //relative humidity in percent
    public static final int TEMPERATURE = 8; //Kelvins
    public static final int HEART_RATE = 9; //Beats per minute
    public static final int ILLUMINANCE = 10; //Lux
    public static final int STEPS = 11; //steps are steps... possibly a non-dimensional count
    public static final int CURRENT = 12; //electric current in Amperes
    public static final int MASS = 13; //kilogram
    public static final int WEIGHT = 14; //Newtons
    public static final int TIME = 15; //seconds
    public static final int SPEED = 16; //meters per second
    public static final int VOLTAGE = 17; //volts
    public static final int FORCE = 18; //Newtons

    public static final int BASE_UNIT_MASK = 0xFF;
    public static final int SUB_UNIT_POSITION = 8;
    public static final int getBasicUnitType(int unit) {
        return unit & BASE_UNIT_MASK;
    }
    public static final int getSubUnitType(int unit) {
        return unit >> SUB_UNIT_POSITION;
    }
    public static final int makeUnit(int basicUnit, int subUnit) {
        return (subUnit << SUB_UNIT_POSITION) | basicUnit;
    }
}
