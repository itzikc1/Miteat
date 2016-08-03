package miteat.miteat;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Service.GpsService;

public class MainActivity extends AppCompatActivity implements LoginFragment.Delegate {
    protected LocationManager locManager;
    FragmentManager manager;
    LoginFragment loginFragment;
    MainFragment mainFragment;
    SettingsFragment settingsFragment;
    int PLACE_PICKER_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!enabled) {
            showDialogGPS();
        }
        else {

            Intent intent = new Intent(MainActivity.this, GpsService.class);
            startService(intent);

//            startActivity(
//                    new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//            Intent intent = new Intent(MainActivity.this,GpsService.class);
//            startService(intent);
        }
        manager = getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        mainFragment = new MainFragment();
        transaction.add(R.id.frag_container, mainFragment);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            settings();
            Log.d("Log","settings");
            return true;
        }

        if (id == R.id.menu_addMeeting) {
                        Intent intent = new Intent(getApplicationContext(),
                   MeetingActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.menu_logIn) {
            loginStartFragment();
            Log.d("Log","logIn");
            return true;
        }

        if (id == R.id.menu_myBooking) {
            PlacePicker();

            return true;
        }
        if (id == R.id.search) {

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void loginStartFragment(){
        loginFragment = new LoginFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container,loginFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }
    private void settings(){
        settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container,settingsFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    private void showDialogGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Enable GPS");
        builder.setMessage("Please enable GPS");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(
                        new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                Intent intent = new Intent(MainActivity.this,GpsService.class);
                startService(intent);
            }
        });
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void finishLogin() {
        mainFragment = new MainFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container,mainFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }
    @Override
    public void goToRegister() {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this,GpsService.class);
        stopService(intent);
        finish();
    }
    public void PlacePicker(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent = builder.build(getApplicationContext());
            startActivityForResult(intent,PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        Place place = PlacePicker.getPlace(data,this);
        String a = String.format("Place: %s",place.getAddress());
        Log.d("adress",a);
    }
    public void search(){


    }
}
