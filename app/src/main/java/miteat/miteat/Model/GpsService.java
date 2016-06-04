package miteat.miteat.Model;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import miteat.miteat.MainActivity;

public class GpsService extends Service implements LocationListener {
    protected LocationManager locManager;
    SharedPreferences sharedPreferencesGet;
    LocationListener listener;

    @Override
    public void onCreate() {
        super.onCreate();
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!enabled) {
           onDestroy();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,30000, 0, this);

        Log.d("TAG","service on create");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG","service on destroy");
    }
    @Override
    public void onLocationChanged(Location location) {
        SharedPreferences sharedPreferencesPut = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferencesPut.edit();
        editor.putString("Lat", String.valueOf(location.getLatitude()));
        editor.putString("Lon", String.valueOf(location.getLongitude()));
        editor.commit();

        sharedPreferencesGet = PreferenceManager
                .getDefaultSharedPreferences(this);
        String lat = sharedPreferencesGet.getString("Lat", "no lat");
        String lon = sharedPreferencesGet.getString("Lon", "no lon");
        Log.d("Tag", lat);
        Log.d("Tag", lon);
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
        Log.d("TAG","service onStartCommand");
        super.onStartCommand(intent, flags, startId);
        ServiceWorker worker = new ServiceWorker();
        worker.start();
        return START_STICKY;
    }



    class ServiceWorker extends Thread {

        public void run() {

        }
    }

}
