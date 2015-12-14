package com.example.drock.n_corder;

import java.util.UUID;

/**
 * Created by drock on 12/12/2015.
 */
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
