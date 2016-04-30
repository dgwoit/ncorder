package com.example.drock.n_corder.android;

import com.example.drock.n_corder.ListFragmentBase;

import java.util.ArrayList;

public class OnboardLocationDetailFragment extends ListFragmentBase {
    @Override
    protected void createListItems() {
        ArrayList<ListItem> items = new ArrayList<>();
        items.add(new ListItem("Speed", new LocationListenerSpeedAdapter()));
        items.add(new ListItem("Bearing", new LocationListenerBearingAdapter()));
        items.add(new ListItem("Altitude", new LocationListenerAltitudeAdapter()));
        setListItems(items);
    }
}
