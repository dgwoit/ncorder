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
package com.example.drock.n_corder.IOIO;

import com.example.drock.n_corder.IMeasurementSink;
import com.example.drock.n_corder.Measurement;
import com.example.drock.n_corder.MeasurementSource;
import com.example.drock.n_corder.units.DistanceUnits;
import com.example.drock.n_corder.units.TemperatureUnits;
import com.example.drock.n_corder.units.UnitConverter;
import com.example.drock.n_corder.units.UnitConverterFactory;
import com.example.drock.n_corder.units.Units;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import com.example.drock.n_corder.IMeasurementSource;

public abstract class IOIODeviceDriver {
    public abstract void Realize(IOIO ioio) throws ConnectionLostException;

    public abstract void Update();
}

class LowPassFilter {
    float mAlpha = 1.0f; //1.0 = no filtering
    float mY = 0;

    public void setAlpha(float value) {
        mAlpha = value;
    }
    public void setState(float value) { mY = value; }

    public float filter(float x) {
        mY = mAlpha * x + (1f - mAlpha) * mY;
        return mY;
    }
}

