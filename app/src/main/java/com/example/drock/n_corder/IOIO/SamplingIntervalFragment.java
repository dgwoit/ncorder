package com.example.drock.n_corder.IOIO;

import android.support.v4.app.Fragment;

import com.example.drock.n_corder.ListFragmentBase;

import java.util.ArrayList;

/**
 * Created by drock on 4/25/2016.
 */
public class SamplingIntervalFragment extends ListFragmentBase {
    public static Fragment newInstance() {
        return new ListFragmentBase();
    }

    @Override
    protected void createListItems() {
        ArrayList<ListItem> items = new ArrayList<ListItem>();
        items.add(new ListItem("10 milliseconds", new Integer(10)));
        items.add(new ListItem("25 milliseconds", new Integer(25)));
        items.add(new ListItem("50 milliseconds", new Integer(50)));
        items.add(new ListItem("100 milliseconds", new Integer(100)));
        items.add(new ListItem("250 milliseconds", new Integer(250)));
        items.add(new ListItem("500 milliseconds", new Integer(500)));
        items.add(new ListItem("1 second", new Integer(1000)));
        items.add(new ListItem("2.5 seconds", new Integer(1000)));
        items.add(new ListItem("5 seconds", new Integer(1000)));
        items.add(new ListItem("10 seconds", new Integer(10000)));
        items.add(new ListItem("15 seconds", new Integer(10000)));
        items.add(new ListItem("30 seconds", new Integer(10000)));
        items.add(new ListItem("1 minute", new Integer(60000)));
        items.add(new ListItem("5 minutes", new Integer(5*60000)));
        items.add(new ListItem("10 minutes", new Integer(10*60000)));
        items.add(new ListItem("15 minutes", new Integer(15*60000)));
        items.add(new ListItem("30 minutes", new Integer(30*60000)));
        items.add(new ListItem("1 hour", new Integer(60*60000)));
        setListItems(items);
    }
}
