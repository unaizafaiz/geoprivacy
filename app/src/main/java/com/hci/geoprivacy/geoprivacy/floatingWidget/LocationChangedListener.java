package com.hci.geoprivacy.geoprivacy.floatingWidget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by maitraikansal on 11/18/17.
 */

public class LocationChangedListener extends BroadcastReceiver {

   /* @Override
    public void onReceive(Context context, Intent intent) {
        if (context != null) {
            intent = new Intent(context, FloatingPromptService.class);
            context.startService(intent);
        }
    }
*/
    private final static String TAG = "LocationProviderChanged";

    boolean isGpsEnabled;
    boolean isNetworkEnabled;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED"))
        {
            Log.i(TAG, "Location Providers changed");

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            //Start your Activity if location was enabled:
            if (isGpsEnabled || isNetworkEnabled) {
                Intent i = new Intent(context, FloatingPromptActivity.class);
                context.startActivity(i);
            }
        }
    }
}
