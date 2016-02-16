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
package com.example.drock.n_corder;

import android.util.Log;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class SensorStreamBroker {
    private static SensorStreamBroker mInstance = new SensorStreamBroker();

    public static SensorStreamBroker getInstance() {
        return mInstance;
    }

    Map<String, IMeasurementSource> mStreams = new ConcurrentSkipListMap<String, IMeasurementSource>();


    public void RegisterStream(String moniker, IMeasurementSource source) {
        assert mStreams != null;
        mStreams.put(moniker, source);
    }

    public void UnregisterStream(IMeasurementSource source) {
        assert mStreams != null;
        for(Map.Entry<String, IMeasurementSource> entry : mStreams.entrySet()) {
            if(entry.getValue() == source)
                mStreams.remove(entry.getKey());
        }
    }

    public void AttachToStream(IMeasurementSink sink, String sourceName) {
        try {
            assert mStreams != null;
            if (mStreams.containsKey(sourceName))
                mStreams.get(sourceName).attach(sink);
        } catch(Exception e) {
            Log.e("SensorStreamBroker", e.toString());
        }
    }

    public void DetachFromStream(IMeasurementSink sink, String sourceName) {
        assert mStreams != null;
        if(mStreams.containsKey(sourceName))
            mStreams.get(sourceName).detach(sink);
    }
}
