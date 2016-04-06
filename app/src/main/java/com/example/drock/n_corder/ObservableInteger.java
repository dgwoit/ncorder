package com.example.drock.n_corder;

import java.util.Observable;

public class ObservableInteger extends Observable {
    public ObservableInteger(Integer value) {
        mValue = value;
    }

    public static ObservableInteger valueOf(int value) {
        return new ObservableInteger(value);
    }

    Integer mValue;
    public Integer get() {
        return mValue;
    }
    public void set(Integer value) {
        if(!mValue.equals(value)) {
            mValue = value;
            setChanged();
            notifyObservers(mValue);
        }
    }
    public int intValue() {
        return mValue;
    }
}
