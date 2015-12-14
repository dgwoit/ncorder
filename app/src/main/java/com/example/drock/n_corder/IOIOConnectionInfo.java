package com.example.drock.n_corder;

import java.util.List;


/**
 * Created by drock on 12/8/2015.
 */
public class IOIOConnectionInfo {
    public final static String BUS_TYPE_A2D = "A/D";
    public final static String BUS_TYPE_SPI = "SPI";
    public final static String BUS_TYPE_UART = "UART";

    protected String mName;
    protected String[] mTypes;
    protected int mPin;

    public IOIOConnectionInfo(String name, String[] types, int pin) {
        mName = name;
        mTypes = types;
        mPin = pin;
    }

    public String getName() {
        return mName;
    }

    public String[] getTypes() {
        return mTypes;
    }

    public int getPin() {
        return mPin;
    }
}
