package com.example.drock.n_corder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;

/**
 * Created by drock on 11/24/2015.
 * Purpose: used to create drivers for devices attached to IOIO pins
 */


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
        int basePin = connectionInfo.getPin();
        for(IOIODeviceDriverInfo entry: driverInfos) {
            if(entry.getName().compareTo(driverId) == 0) {
                IOIODeviceDriver driver = entry.createInstance(basePin);
                SensorStreamBroker streamBroker = SensorStreamBroker.getInstance();
                streamBroker.RegisterStream(IOIO, (IMeasurementSource)driver);
                AssignDriver(connectionId, driver);
            }
        }
    }

    //finalize manufacturing of drivers using IOIO
    public void RealizeDrivers(IOIO ioio) throws ConnectionLostException {
        for(IOIODeviceDriver d : this.drivers.values())
            d.Realize(ioio);
    }
}
