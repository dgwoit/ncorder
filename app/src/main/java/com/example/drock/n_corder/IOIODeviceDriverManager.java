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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;

public class IOIODeviceDriverManager {
    public final static String IOIO = "IOIO"; //default stream identifier

    //sorry for the singleton
    private static IOIODeviceDriverManager instance = new IOIODeviceDriverManager();
    public static IOIODeviceDriverManager getInstance() {
        return instance;
    }

    private IOIODeviceDriverManager() {

        drivers = new TreeMap<String, IOIODeviceDriver>();
        loadDriverInfos();
    }

    private Map<String, IOIODeviceDriver> drivers;
    private Collection<IOIODeviceDriverInfo> driverInfos;

    void loadDriverInfos() {
        driverInfos = new LinkedList<IOIODeviceDriverInfo>();
        driverInfos.add(new IOIODeviceDriverInfo("Grove Hi Temp Probe - high temp", IOIOConnectionInfo.BUS_TYPE_A2D, new IOIODeviceDriverInfo.DriverInstantiator() {
            @Override
            public IOIODeviceDriver createInstance(int basePin) {
                return new GroveHiTempThermocouple(basePin, false);
            }
        }) );
        driverInfos.add(new IOIODeviceDriverInfo("Grove Hi Temp Probe - ambient temp", IOIOConnectionInfo.BUS_TYPE_A2D, new IOIODeviceDriverInfo.DriverInstantiator() {
            @Override
            public IOIODeviceDriver createInstance(int basePin) {
                return new GroveHiTempThermocouple(basePin, true);
            }
        }) );
        driverInfos.add(new IOIODeviceDriverInfo("Grove Differential Amplifier", IOIOConnectionInfo.BUS_TYPE_A2D, new IOIODeviceDriverInfo.DriverInstantiator() {
            @Override
            public IOIODeviceDriver createInstance(int basePin) {
                return new GroveDifferentialAmplifier(basePin);
            }
        }));
        driverInfos.add(new IOIODeviceDriverInfo("Analog Reader", IOIOConnectionInfo.BUS_TYPE_A2D, new IOIODeviceDriverInfo.DriverInstantiator() {
            @Override
            public IOIODeviceDriver createInstance(int basePin) {
                return new AnalogPinReader(basePin);
            }
        }));
        driverInfos.add(new IOIODeviceDriverInfo("Grove Load Cell", IOIOConnectionInfo.BUS_TYPE_A2D, new IOIODeviceDriverInfo.DriverInstantiator() {
            @Override
            public IOIODeviceDriver createInstance(int basePin) {
                return new GroveLoadCell(basePin);
            }
        }));
        driverInfos.add(new IOIODeviceDriverInfo("ACS712 Current Sensor", IOIOConnectionInfo.BUS_TYPE_A2D, new IOIODeviceDriverInfo.DriverInstantiator() {
            @Override
            public IOIODeviceDriver createInstance(int basePin) {
                return new ACS712CurrentSensor(basePin);
            }
        }));
        driverInfos.add(new IOIODeviceDriverInfo("Grove 80cm Infrared Proximity Sensor", IOIOConnectionInfo.BUS_TYPE_A2D, new IOIODeviceDriverInfo.DriverInstantiator() {
            @Override
            public IOIODeviceDriver createInstance(int basePin) {
                try {
                    //ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                    //final Class<Object> classToLoad = (Class<Object>) classLoader.loadClass("com.example.drock.n_corder.GroveInfraredProximitySensor");
                    final Class classToLoad = GroveInfraredProximitySensor.class;
                    Class[] cArg = new Class[1];
                    cArg[0] = int.class;
                    final Method m = classToLoad.getDeclaredMethod("newInstance", cArg);
                    return (IOIODeviceDriver) m.invoke(null, basePin);
                } /*catch(ClassNotFoundException x) {
                    Log.e("IOIODvcDrvInstantiator", String.format("Class Not Found: %s", x.toString()));
                }*/ catch (NoSuchMethodException x) {
                    Log.e("IOIODvcDrvInstantiator", String.format("Method No Found: %s", x.toString()));
                } catch (IllegalAccessException x) {
                    Log.e("IOIODvcDrvInstantiator", String.format("Illegal Method Access: %s", x.toString()));
                } catch (InvocationTargetException x) {
                    Log.e("IOIODvcDrvInstantiator", String.format("Invocation Target Exception: %s", x.toString()));
                }

                return new GroveInfraredProximitySensor(basePin);
            }
        }));
    }

    // assigns/associates a driver instance to a connection
    void AssignDriver(String connectionId, IOIODeviceDriver driver) {
        drivers.put(connectionId, driver);
    }

    public Collection<IOIODeviceDriver> getDrivers() {
        return drivers.values();
    }

    public Collection<IOIODeviceDriverInfo> getDriversForConnection(String connectionId) {
        IOIOConnectionTable connectionTable = new IOIOConnectionTable();
        IOIOConnectionInfo connectionInfo = connectionTable.getConnectionInfo(connectionId);
        String[] pinTypes = connectionInfo.getTypes();
        Set<IOIODeviceDriverInfo> driverInfoSet = new TreeSet<IOIODeviceDriverInfo>();
        for(String pinType: pinTypes) {
            for(IOIODeviceDriverInfo driverInfo: driverInfos) {
                if (pinType.compareTo(driverInfo.getBusProtocol()) == 0)
                    driverInfoSet.add(driverInfo);
            }
        }

        return driverInfoSet;
    }

    //sets up a connection to a device connected to the IOIO board
    //finallation of the connection process is performed in Realize that "realizes" the drivers
    public void BeginConnectToDevice(String driverId, String connectionId) {
        IOIOConnectionTable connectionInfoTable = new IOIOConnectionTable();
        IOIOConnectionInfo connectionInfo = connectionInfoTable.getConnectionInfo(connectionId);
        if(connectionInfo != null) {
            int basePin = connectionInfo.getPin();
            for (IOIODeviceDriverInfo entry : driverInfos) {
                if (entry.getName().compareTo(driverId) == 0) {
                    IOIODeviceDriver driver = entry.createInstance(basePin);
                    SensorStreamBroker streamBroker = SensorStreamBroker.getInstance();
                    streamBroker.RegisterStream(IOIO, (IMeasurementSource) driver);
                    AssignDriver(connectionId, driver);
                }
            }
        }
    }

    //finalize manufacturing of drivers using IOIO
    public void RealizeDrivers(IOIO ioio) throws ConnectionLostException {
        for(IOIODeviceDriver d : this.drivers.values())
            d.Realize(ioio);
    }
}
