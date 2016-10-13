package miteat.miteat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import miteat.miteat.Model.Entities.Meeting;

public class MyBookingActivity extends AppCompatActivity implements MyBookingFragment.MyBookingFragmentInterface, InfoBookingFragment.InfoBookingFragmentInterface, BookingListMenuFragment.BookingListMenuFragmentInterface,UserDetailsFragment.UserDetailsInterface {
    FragmentManager manager;
    MyBookingFragment myBookingFragment;
    UserDetailsFragment userDetailsFragment;
    InfoBookingFragment infoBookingFragment;
    BookingListMenuFragment bookingListMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);

        myBookingFragment = new MyBookingFragment();
        manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.myBooking, myBookingFragment);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bookin_menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.back_to_main) {

            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void detailsLoad(String userId, Boolean confirmation) {

        userDetailsFragment = new UserDetailsFragment();
        userDetailsFragment.setUserId(userId);
        userDetailsFragment.setConfirmation(confirmation);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(myBookingFragment);
        transaction.add(R.id.myBooking, userDetailsFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void backToListBooking() {


        myBookingFragment = new MyBookingFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.myBooking, myBookingFragment);
        invalidateOptionsMenu();
        transaction.commit();

    }

    @Override
    public void bookingGoTOhMenu(Meeting meeting) {
        bookingListMenuFragment = new BookingListMenuFragment();
        bookingListMenuFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(infoBookingFragment);
        transaction.add(R.id.myBooking, bookingListMenuFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void bookingMenu(Meeting meeting) {

        infoBookingFragment = new InfoBookingFragment();
        infoBookingFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.myBooking, infoBookingFragment);
        invalidateOptionsMenu();
        transaction.commit();

    }

    @Override
    public void refreshBookingList() {
        myBookingFragment = new MyBookingFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.myBooking, myBookingFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void backPressFromMenu() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(bookingListMenuFragment);
        transaction.show(infoBookingFragment);
        invalidateOptionsMenu();
        transaction.commit();


    }

    @Override
    public void backPressUserDetails() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(userDetailsFragment);
        transaction.show(myBookingFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }
}
