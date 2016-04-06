package com.example.drock.n_corder.units;

import java.util.Observable;
import java.util.Observer;

public class DisplayUnitFormatter extends UnitFormatter implements Observer {
    public DisplayUnitFormatter(int unit) {
        super(unit);
    }

    @Override
    public void update(Observable observable, Object data) {
        init((Integer)data);
    }
}
