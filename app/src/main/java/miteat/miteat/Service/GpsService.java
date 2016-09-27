package miteat.miteat.Service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Model;

/**
 * Created by Itzik on 10/06/2016.
 */

public class GpsService extends Service implements LocationListener {
    protected LocationManager locManager;
    SharedPreferences sharedPreferencesGet;

    @Override
    public void onCreate() {


        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            onDestroy();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }


        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, this);

        //  Log.d("TAG", "service gps on create");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Log.d("TAG", "service gps on destroy");
    }

    @Override
    public void onLocationChanged(Location location) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean checkGpsEnabeled = sharedPref.getBoolean("pref_gps", true);
        if (!checkGpsEnabeled)
            stopSelf();

        Log.d("TAG", "service gps update");
        SharedPreferences sharedPreferencesPut = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferencesPut.edit();
        editor.putString("Lat", String.valueOf(location.getLatitude()));
        editor.putString("Lon", String.valueOf(location.getLongitude()));
        editor.commit();


        sharedPreferencesGet = PreferenceManager
                .getDefaultSharedPreferences(this);
        String lon = sharedPreferencesGet.getString("Lon", "no lon");
        String lat = sharedPreferencesGet.getString("Lat", "no lat");
        long time = System.currentTimeMillis();
        Gps gps = new Gps(1, lon, lat, time);
        Model.instance().addGps(gps);

        // Log.d("Tag", lat);
        // Log.d("Tag", lon);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        //  Log.d("TAG", "service gps onStartCommand");

        return START_STICKY;
    }

}