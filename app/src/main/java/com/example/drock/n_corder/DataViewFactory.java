package com.example.drock.n_corder;

import android.support.v4.app.Fragment;

/**
 * Created by drock on 1/10/2016.
 */
public class DataViewFactory {
    public class ViewType {
        public static final String NumericDisplay = "numericDisplay";
        public static final String DataPlot = "dataPlot";
    }

    Fragment createFragment(String viewType, String streamName) {
        switch(viewType) {
            case ViewType.DataPlot:
                return ValuePlotFragment.newInstance(streamName);

            case ViewType.NumericDisplay:
            default:
                return NumericViewFragment.newInstance(streamName);
        }
    }
}
