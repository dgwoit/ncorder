package com.example.drock.n_corder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationListener;
import android.location.LocationManager;

public class LocationManagerHelper {
    protected Context mApplicationContext;
    protected LocationManager mLocationManager;
    public static final String ACTION_LOCATION = "com.example.drock.n_corder.location_services.ACTION_LOCATION";
    protected LocationReceiver mLocationReceiver = new LocationReceiver();

    public LocationManagerHelper(Context applicationContext) {
        mApplicationContext = applicationContext;
        mLocationManager = (LocationManager)applicationContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startLocationUpdates() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_LOCATION);
        mApplicationContext.registerReceiver(mLocationReceiver,intentFilter);
        Intent intent = new Intent(ACTION_LOCATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mApplicationContext, 0, intent, 0);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, pendingIntent);
    }

    public void stopLocationUpdates() {
        Intent intent = new Intent(ACTION_LOCATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mApplicationContext, 0, intent, PendingIntent.FLAG_NO_CREATE);
        mLocationManager.removeUpdates(pendingIntent);
        pendingIntent.cancel();
        mApplicationContext.unregisterReceiver(mLocationReceiver);
    }

    public void requestLocationUpdates(LocationListener listener) {
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
    }

    public void removeUpdates(LocationListener listener) {
        mLocationManager.removeUpdates(listener);
    }

    LocationReceiver getLocationReceiver() { return mLocationReceiver; }
}
