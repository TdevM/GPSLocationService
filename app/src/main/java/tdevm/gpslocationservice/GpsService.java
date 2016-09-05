package tdevm.gpslocationservice;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.print.PrintHelper;

public class GpsService extends Service {

    private LocationListener mListener;
    private LocationManager mManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Intent i = new Intent("location_update");
                i.putExtra("Coordinates", location.getLatitude() + " " + location.getLongitude());
                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        };
        mManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


        mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, mListener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mManager.removeUpdates(mListener);
    }
}
