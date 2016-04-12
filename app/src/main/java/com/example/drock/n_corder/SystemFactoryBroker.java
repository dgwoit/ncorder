package com.example.drock.n_corder;

import android.content.Context;

public class SystemFactoryBroker {
    static ISystemFactory mSystemFactory;

    public static void initSystemFactory(Context applicationContext) {
        mSystemFactory = new SystemFactory(applicationContext);
    }

    public static ISystemFactory getSystemFactory() {
        return mSystemFactory;
    }
}
