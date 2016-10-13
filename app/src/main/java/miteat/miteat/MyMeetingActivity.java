package miteat.miteat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import miteat.miteat.Model.Entities.Meeting;

public class MyMeetingActivity extends AppCompatActivity implements MyMeetingFragment.MyMeetingFragmentInterface,UserDetailsFragment.UserDetailsInterface,BookingListMenuFragment.BookingListMenuFragmentInterface,InfoBookingFragment.InfoBookingFragmentInterface {

    FragmentManager manager;
    MyMeetingFragment myMeetingFragment;
    UserDetailsFragment userDetailsFragment;
    InfoBookingFragment infoBookingFragment;
    BookingListMenuFragment bookingListMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meeting);

        myMeetingFragment = new MyMeetingFragment();
        manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.myMeeting, myMeetingFragment);
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
        transaction.hide(myMeetingFragment);
        transaction.add(R.id.myMeeting, userDetailsFragment);
        invalidateOptionsMenu();
        transaction.commit();

    }

    @Override
    public void meetingMenu(Meeting meeting) {
        infoBookingFragment = new InfoBookingFragment();
        infoBookingFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.myMeeting, infoBookingFragment);
        invalidateOptionsMenu();
        transaction.commit();

    }

    @Override
    public void refreshMeetingList() {

        myMeetingFragment = new MyMeetingFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.myMeeting, myMeetingFragment);
        invalidateOptionsMenu();
        transaction.commit();

    }

    @Override
    public void backPressUserDetails() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(userDetailsFragment);
        transaction.show(myMeetingFragment);
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
    public void backToListBooking() {
        myMeetingFragment = new MyMeetingFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.myMeeting, myMeetingFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void bookingGoTOhMenu(Meeting meeting) {
        bookingListMenuFragment = new BookingListMenuFragment();
        bookingListMenuFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(infoBookingFragment);
        transaction.add(R.id.myMeeting, bookingListMenuFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }
}

