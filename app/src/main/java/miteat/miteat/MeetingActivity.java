package miteat.miteat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;

import java.io.FileNotFoundException;

import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.ModelCloudinary;

public class MeetingActivity extends AppCompatActivity implements MainMeetingFragment.MeetingFragmentInterface, MenuListFragment.ListFragmentInterface, MenuPortionsFragment.MenuFragmentInterface {
    FragmentManager manager;
    MainMeetingFragment mainMeetingFragment;
    MenuListFragment menuListFragment;
    MenuPortionsFragment menuPortionsFragment;

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;


    private String picName;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        mainMeetingFragment = new MainMeetingFragment();
        manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.meeting_main, mainMeetingFragment);
        transaction.commit();

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
        finish();
    }

    @Override
    public void addPortions( Meeting meeting) {
        menuPortionsFragment = new MenuPortionsFragment();
        menuPortionsFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.meeting_main, menuPortionsFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

//    @Override
//    public String addPic() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
//        return picName;
//    }
//
//    @Override
//    public String takePic() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_CAMERA);
//        return picName;
//
//    }

    @Override
    public void saveInterface(Meeting meeting) {
        menuListFragment = new MenuListFragment();
        menuListFragment.setMeeting(meeting);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.meeting_main, menuListFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }


}