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

import java.util.UUID;

public class IOIODeviceDriverInfo implements Comparable<IOIODeviceDriverInfo> {
    public interface DriverInstantiator {
        public IOIODeviceDriver createInstance(int basePin);
    }

    protected String mName;
    protected String mBusProtocol;
    DriverInstantiator mInstantiator;

    public IOIODeviceDriverInfo(String name, String busProtocol, DriverInstantiator instantiator) {
        mName = name;
        mBusProtocol = busProtocol;
        mInstantiator = instantiator;
    }

    public String getName() {
        return mName;
    }

    public String getBusProtocol() {
        return mBusProtocol;
    }

    public IOIODeviceDriver createInstance(int basePin) {
        return mInstantiator.createInstance(basePin);
    }

    @Override
    public int compareTo(IOIODeviceDriverInfo rhVal) {
        return mName.compareTo(rhVal.getName());
    }
}
