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

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IOIODeviceDriverInfo implements Comparable<IOIODeviceDriverInfo> {
    public interface DriverInstantiator {
        public IOIODeviceDriver createInstance(int basePin);
    }

    protected String mName;
    protected String mBusProtocol;
    protected Class mClassToLoad;

    public IOIODeviceDriverInfo(String name, String busProtocol, Class classToLoad) {
        mName = name;
        mBusProtocol = busProtocol;
        mClassToLoad = classToLoad;
    }

    public String getName() {
        return mName;
    }

    public String getBusProtocol() {
        return mBusProtocol;
    }

    public IOIODeviceDriver createInstance(int basePin) {
        try {
            Class[] cArg = new Class[1];
            cArg[0] = int.class;
            final Method m = mClassToLoad.getDeclaredMethod("newInstance", cArg);
            return (IOIODeviceDriver) m.invoke(null, basePin);
        } /*catch(ClassNotFoundException x) {
                    Log.e("IOIODvcDrvInstantiator", String.format("Class Not Found: %s", x.toString()));
                }*/
        catch (NoSuchMethodException x) {
            Log.e("IOIODvcDrvInstantiator", String.format("Method No Found: %s", x.toString()));
        } catch (IllegalAccessException x) {
            Log.e("IOIODvcDrvInstantiator", String.format("Illegal Method Access: %s", x.toString()));
        } catch (InvocationTargetException x) {
            Log.e("IOIODvcDrvInstantiator", String.format("Invocation Target Exception: %s", x.toString()));
        }

        return null;
    }

    @Override
    public int compareTo(IOIODeviceDriverInfo rhVal) {
        return mName.compareTo(rhVal.getName());
    }
}
