package com.example.drock.n_corder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

public class LocationManagerHelper {
    Context mApplicationContext;
    LocationManager mLocationManager;
    public static final String ACTION_LOCATION = "com.example.drock.n_corder.location_services.ACTION_LOCATION";

    public LocationManagerHelper(Context applicationContext) {
        mApplicationContext = applicationContext;
        mLocationManager = (LocationManager)applicationContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startLocationUpdates() {
        Intent intent = new Intent(ACTION_LOCATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mApplicationContext, 0, intent, 0);
    }

    public void stopLocationUpdates() {
        Intent intent = new Intent(ACTION_LOCATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mApplicationContext, 0, intent, PendingIntent.FLAG_NO_CREATE);
        mLocationManager.removeUpdates(pendingIntent);
        pendingIntent.cancel();
    }
}
