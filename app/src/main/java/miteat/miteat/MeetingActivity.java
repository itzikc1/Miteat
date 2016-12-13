package miteat.miteat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import miteat.miteat.Model.Entities.FoodPortions;
import miteat.miteat.Model.Entities.Meeting;

public class MeetingActivity extends AppCompatActivity implements MainMeetingFragment.MeetingFragmentInterface, MenuListFragment.ListFragmentInterface, MenuPortionsFragment.MenuFragmentInterface, MeetingListFragment.MeetingListFragmentInterface {
    FragmentManager manager;
    MainMeetingFragment mainMeetingFragment;
    MenuListFragment menuListFragment;
    MenuPortionsFragment menuPortionsFragment;
    MeetingListFragment meetingListFragment;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String picName;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        meetingListFragment = new MeetingListFragment();
        manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.meeting_main, meetingListFragment);
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

        int id = item.getItemId();
        if (id == R.id.menu_back_main) {

            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.menu_plus_meeting) {
            mainMeetingFragment = new MainMeetingFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.meeting_main, mainMeetingFragment);
            invalidateOptionsMenu();
            transaction.commit();
            Log.d("Log", "pluse");
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void menuStart(Meeting meeting) {

        menuListFragment = new MenuListFragment();
        menuListFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.meeting_main, menuListFragment);
        invalidateOptionsMenu();
        transaction.commit();

    }

    @Override
    public void saveOrCencel() {

        meetingListFragment = new MeetingListFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.meeting_main, meetingListFragment);
        invalidateOptionsMenu();
        transaction.commit();

    }

    @Override
    public void addPortions(Meeting meeting) {
        menuPortionsFragment = new MenuPortionsFragment();
        menuPortionsFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.meeting_main, menuPortionsFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void editPortions(FoodPortions f, Meeting meeting) {

        menuPortionsFragment = new MenuPortionsFragment();
        menuPortionsFragment.setMeeting(meeting);
        menuPortionsFragment.edit(f);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.meeting_main, menuPortionsFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void refresh(Meeting meeting) {

        menuListFragment = new MenuListFragment();
        menuListFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.meeting_main, menuListFragment);
        invalidateOptionsMenu();
        transaction.commit();

    }

    @Override
    public void backButton(Meeting meeting) {
        mainMeetingFragment = new MainMeetingFragment();
        mainMeetingFragment.setMenuList(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.meeting_main, mainMeetingFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }


    @Override
    public void saveInterface(Meeting meeting) {
        menuListFragment = new MenuListFragment();
        menuListFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.meeting_main, menuListFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }


    @Override
    public void refreshList() {
        meetingListFragment = new MeetingListFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.meeting_main, meetingListFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void editMeeting(Meeting meeting) {
        mainMeetingFragment = new MainMeetingFragment();
        mainMeetingFragment.setMenuList(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.meeting_main, mainMeetingFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(intent);
        finish();
    }
}