package com.example.drock.n_corder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

public class LocationReceiver extends BroadcastReceiver {
    Location mLocation;
    @Override
    public void onReceive(Context context, Intent intent) {
        Location location = (Location)intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
        if(location != null) {
            onLocationReceived(location);
        }
    }

        public void onLocationReceived(Location location) {
            mLocation = location;
        }

    Location getLocation() { return mLocation; }
}
