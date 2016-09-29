package miteat.miteat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import miteat.miteat.Model.Entities.Meeting;

public class MyBookingActivity extends AppCompatActivity implements MyBookingFragment.MyBookingFragmentInterface{
    FragmentManager manager;
    MyBookingFragment myBookingFragment;
    UserDetailsFragment userDetailsFragment;
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
        getMenuInflater().inflate(R.menu.menu_meeting_manger, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        int id = item.getItemId();
//        if (id == R.id.menu_back_main) {
//
//            Intent intent = new Intent(getApplicationContext(),
//                    MainActivity.class);
//            startActivity(intent);
//            finish();
//            return true;
//        }




        return super.onOptionsItemSelected(item);
    }

    @Override
    public void detailsLoad(String userId, Boolean confirmation) {

        userDetailsFragment = new UserDetailsFragment();
        userDetailsFragment.setUserId(userId);
        userDetailsFragment.setConfirmation(confirmation);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(myBookingFragment);
        transaction.add(R.id.myBooking,userDetailsFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void bookingMenu(Meeting meeting) {

    }
}
