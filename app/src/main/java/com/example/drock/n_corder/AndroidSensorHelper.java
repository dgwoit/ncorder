package com.example.drock.n_corder;

/**
 * Created by drock on 12/5/2015.
 */
public class AndroidSensorHelper {
    private int mType;
    private String[] mValueInfo;

    public AndroidSensorHelper(int type, String[] valueInfo) {
        mType = type;
        mValueInfo = valueInfo;
    }
    public int getType() {return mType;}
    public int getValueCount() {return mValueInfo.length;}
    public String getValueDescription(int index ) { return mValueInfo[index]; }
}
