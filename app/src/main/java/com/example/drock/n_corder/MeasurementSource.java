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

import java.util.ArrayList;
import java.util.List;

interface IMeasurementSource {
    public void attach(IMeasurementSink sink);
    public void detach(IMeasurementSink sink);
    public void update(Measurement m);
}

public class MeasurementSource implements IMeasurementSource {
    private List<IMeasurementSink> sinks = new ArrayList<IMeasurementSink>();

    public void attach(IMeasurementSink subscriber) {
        synchronized (sinks) {
            if (this.sinks.indexOf(subscriber) == -1)
                this.sinks.add(subscriber);
        }
    }

    public void detach(IMeasurementSink subscriber) {
        synchronized (sinks) {
            this.sinks.remove(subscriber);
        }
    }

    public void update(Measurement m) {
        synchronized (sinks) {
            for (IMeasurementSink s : this.sinks) {
                if (!s.update(m))
                    this.sinks.remove(s);
            }
        }
    }
}