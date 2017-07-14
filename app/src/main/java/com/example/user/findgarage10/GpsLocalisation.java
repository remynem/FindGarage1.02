package com.example.user.findgarage10;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by student on 04-07-17.
 */

public class GpsLocalisation implements LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    //region Callback
    private IGpsLocalisation callback;
    private Context context;

    //endregion
    public GpsLocalisation() {

    }

    public void setCallback(IGpsLocalisation callback) {
        this.callback = callback;
    }

    public void demande(Context context) {
        this.context = context;
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if (callback != null) {
                requestPermissions(context);
            }
            return;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (callback == null) {
            callback.localiser(new Position(
                            location.getLatitude(),
                            location.getLongitude()
                    )
            );
        }
    }

    private void requestPermissions(Context context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.i("TEST", "onStatusChanged");
        Log.v("s", s);
        Log.v("i", i + "");
        Log.v("bundle", bundle.toString());
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public interface IGpsLocalisation {
        void localiser(Position position);
    }
}
