package com.example.drock.n_corder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

public class LocationReceiver extends BroadcastReceiver {
    static Location mLocation;
    static int mIdCnt = 0;
    int mId;

    public LocationReceiver() {
        mId = mIdCnt++;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {
            Location location = (Location) intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
            if (location != null) {
                onLocationReceived(context, location);
            }
        } else if(intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)) {
            boolean enabled = intent.getBooleanExtra(LocationManager.KEY_PROVIDER_ENABLED, false);
            onProviderEnabled(context, enabled);
        } else if(intent.hasExtra(LocationManager.KEY_STATUS_CHANGED)) {
            int status = intent.getIntExtra(LocationManager.KEY_STATUS_CHANGED, 0);
            onStatusChanged(context, status);
        }

    }

    public void onLocationReceived(Context context, Location location) {
            mLocation = location;
        }
    public void onProviderEnabled(Context context, boolean enabled) {
        Toast.makeText(context, String.format("Location %s", (enabled ? "enabled" : "disabled")), Toast.LENGTH_SHORT).show();
    }
    public void onStatusChanged(Context context, int status) {
        //Toast.makeText(context, String.format("Location status changed to %d", status), Toast.LENGTH_SHORT).show();
    }


    Location getLocation() { return mLocation; }
}
