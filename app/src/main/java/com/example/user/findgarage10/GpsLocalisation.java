package com.example.user.findgarage10;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by student on 04-07-17.
 */

public class GpsLocalisation implements LocationListener {

    //region Callback
    public interface IGpsLocalisation {
        void localiser(Position position);
    }

    private IGpsLocalisation callback;

    public void setCallback(IGpsLocalisation callback) {
        this.callback = callback;
    }
    //endregion
    public GpsLocalisation(){

    }

    public void demande(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if(callback != null) {
                callback.localiser(null);
            }
            return;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,0,this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("TEST", "onLocationChanged");
        Log.v("position", location.getLatitude() + " - " + location.getLongitude());
        if(callback != null) {
            callback.localiser(new Position(
                            location.getLatitude(),
                            location.getLongitude()
                    )
            );
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
}
