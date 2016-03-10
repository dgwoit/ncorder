package com.example.drock.n_corder.units;

public class DefaultUnitSuffixer implements IUnitSuffixer {
    String mSuffix;

    public DefaultUnitSuffixer(String suffix) {
        mSuffix = suffix;
    }

    @Override
    public String getSuffix(int unit) { return mSuffix; }
}
