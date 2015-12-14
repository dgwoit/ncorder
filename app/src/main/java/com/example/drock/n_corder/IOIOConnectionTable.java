package com.example.drock.n_corder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by drock on 12/8/2015.
 */
public class IOIOConnectionTable {

    List<IOIOConnectionInfo> mConnectionInfo;


    public IOIOConnectionTable() {
        LoadSettings();
    }

    //we should be able to load this from a file so we can have different connection info for the
    //IOIO OTG board, and for the Seeed Studio IOIO OTG base shield board
    public void LoadSettings() {
        mConnectionInfo = new LinkedList<IOIOConnectionInfo>();

        //add data for Seeed Studio IOIO OTG grove base shield board
        mConnectionInfo.add(new IOIOConnectionInfo("J1", new String[]{IOIOConnectionInfo.BUS_TYPE_SPI, IOIOConnectionInfo.BUS_TYPE_UART}, 4));
        mConnectionInfo.add(new IOIOConnectionInfo("J2", new String[]{IOIOConnectionInfo.BUS_TYPE_SPI, IOIOConnectionInfo.BUS_TYPE_UART}, 1));
        mConnectionInfo.add(new IOIOConnectionInfo("J3", new String[]{IOIOConnectionInfo.BUS_TYPE_UART}, 11));
        mConnectionInfo.add(new IOIOConnectionInfo("J6", new String[]{IOIOConnectionInfo.BUS_TYPE_UART}, 13));
        mConnectionInfo.add(new IOIOConnectionInfo("J7", new String[]{IOIOConnectionInfo.BUS_TYPE_A2D, IOIOConnectionInfo.BUS_TYPE_UART}, 33));
        mConnectionInfo.add(new IOIOConnectionInfo("J8", new String[]{IOIOConnectionInfo.BUS_TYPE_A2D, IOIOConnectionInfo.BUS_TYPE_UART}, 37));
    }

    public List<IOIOConnectionInfo> getConnectionInfo() {
        return mConnectionInfo;
    }

    public IOIOConnectionInfo getConnectionInfo(String connectionId) {
        for(IOIOConnectionInfo info: mConnectionInfo) {
            if(info.getName().compareTo(connectionId) == 0)
                return info;
        }

        return null;
    }
}
