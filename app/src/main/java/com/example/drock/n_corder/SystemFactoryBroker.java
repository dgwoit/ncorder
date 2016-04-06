package com.example.drock.n_corder;

public class SystemFactoryBroker {
    static ISystemFactory mSystemFactory;

    public static ISystemFactory getSystemFactory() {
        if(null == mSystemFactory) { //create system factory
            mSystemFactory = new SystemFactory();
        }

        return mSystemFactory;
    }
}
