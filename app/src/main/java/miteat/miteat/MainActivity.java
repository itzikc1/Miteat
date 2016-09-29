package miteat.miteat;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Service.GpsService;

public class MainActivity extends AppCompatActivity implements LoginFragment.Delegate, MainFragment.MainListFragmentInterface, BookingFragment.BookingFragmentInterface ,UserDetailsFragment.UserDetailsInterface,MyDetailsFragment.MyDetailsFragmentInterface,BookingListMenuFragment.BookingListMenuFragmentInterface {
    protected LocationManager locManager;
    FragmentManager manager;
    LoginFragment loginFragment;
    MainFragment mainFragment;
    SettingsFragment settingsFragment;
    BookingFragment bookingFragment;
    MyDetailsFragment myDetailsFragment;
    BookingListMenuFragment bookingListMenuFragment;
    int PLACE_PICKER_REQUEST = 1;
    UserDetailsFragment userDetailsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            showDialogGPS();
        } else {

            Intent intent = new Intent(MainActivity.this, GpsService.class);
            startService(intent);

//            startActivity(
//                    new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//            Intent intent = new Intent(MainActivity.this,GpsService.class);
//            startService(intent);
        }
        manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
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
            Log.d("Log", "settings");
            return true;
        }

        if (id == R.id.menu_addMeeting) {
            Intent intent = new Intent(getApplicationContext(),
                    MeetingActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.menu_my_details) {
            myDetailsFragment = new MyDetailsFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frag_container, myDetailsFragment);
            invalidateOptionsMenu();
            transaction.commit();

            return true;
        }

        if (id == R.id.menu_logIn) {
            loginStartFragment();
            Log.d("Log", "logIn");
            return true;
        }

        if (id == R.id.menu_myBooking) {
            Intent intent = new Intent(getApplicationContext(),
                    MyBookingActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.search) {

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void loginStartFragment() {
        loginFragment = new LoginFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container, loginFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    private void settings() {
        settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container, settingsFragment);
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
                Intent intent = new Intent(MainActivity.this, GpsService.class);
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
        transaction.replace(R.id.frag_container, mainFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void goToRegister() {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, GpsService.class);
        stopService(intent);
        finish();
    }


    public void search() {


    }

    @Override
    public void booking(Meeting meeting) {

        bookingFragment = new BookingFragment();
        bookingFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container, bookingFragment);
        invalidateOptionsMenu();
        transaction.commit();

    }

    @Override
    public void finishBooking() {

        mainFragment = new MainFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container, mainFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void detailsLoad(String userId,Boolean confirmation) {

        userDetailsFragment = new UserDetailsFragment();
        userDetailsFragment.setUserId(userId);
        userDetailsFragment.setConfirmation(confirmation);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(bookingFragment);
        transaction.add(R.id.frag_container,userDetailsFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void bookingMenu(Meeting meeting) {
        bookingListMenuFragment = new BookingListMenuFragment();
        bookingListMenuFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(bookingFragment);
        transaction.add(R.id.frag_container,bookingListMenuFragment);
        invalidateOptionsMenu();
        transaction.commit();

    }

    @Override
    public void backPressUserDetails() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(userDetailsFragment);
        transaction.show(bookingFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void backPressMyDetails() {
        mainFragment = new MainFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag_container, mainFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void backPressFromMenu() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(bookingListMenuFragment);
        transaction.show(bookingFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }
}
