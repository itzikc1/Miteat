package miteat.miteat.Service;

import android.app.IntentService;
import android.app.Service;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import miteat.miteat.Model.Entities.Booking;
import miteat.miteat.Model.Model;
import miteat.miteat.R;

/**
 * Created by Itzik on 19/10/2016.
 */
public class UpdateService extends Service implements ComponentCallbacks2 {
    //public class UpdateService extends IntentService {
    SharedPreferences sharedPreferencesGet;
    private static final int MINUTE = 1000 * 60 * 5;
    Intent intent;
    private final Handler handler = new Handler();
    private static final String MY_BOOKING_TABLE = "my_booking_table";
    private static final String MY_BOOKING_MEETING_TABLE = "my_booking_meeting_table";
    private static final String USER_NAME = Model.instance().getUserDetails().getUserName();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference bookingReference = databaseReference.child(MY_BOOKING_TABLE).child(USER_NAME);
    DatabaseReference meetingReference = databaseReference.child(MY_BOOKING_MEETING_TABLE).child(USER_NAME);

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        handler.removeCallbacks(doUpdates);
        handler.postDelayed(doUpdates, 100);
        sharedPreferencesGet = PreferenceManager
                .getDefaultSharedPreferences(this);
        bookingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot stSnapshot : dataSnapshot.getChildren()) {
                    Booking booking = stSnapshot.getValue(Booking.class);
                    switch (booking.getConfirmation()) {
                        case 1:
                            sendNotification(getString(R.string.youYetOneBooking), getString(R.string.theUser) + booking.getMeeting().getUserId() + getString(R.string.accept));
                            break;
                        case 2:
                            sendNotification(getString(R.string.refusedBooking), getString(R.string.theUser) + booking.getMeeting().getUserId() + getString(R.string.refusedBooking));
                            break;
                        case 3:
                            sendNotification(getString(R.string.cancelBooking), getString(R.string.theUser) + booking.getMeeting().getUserId() + getString(R.string.canceledBooking));
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        meetingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot stSnapshot : dataSnapshot.getChildren()) {
                    Booking booking = stSnapshot.getValue(Booking.class);
                    switch (booking.getConfirmation()) {
                        case 0:
                            sendNotification(getString(R.string.youYetOneBooking), getString(R.string.theUser) + booking.getUserIdOfBooking() + getString(R.string.wantToEnjoy));
                            break;
                        case 3:
                            sendNotification(getString(R.string.cancelBooking), getString(R.string.theUser) + booking.getUserIdOfBooking() + getString(R.string.canceledBooking));
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Runnable doUpdates = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, MINUTE);
            // Log.d("TAG", "service update");
            //   Model.instance().updateMeetingToBookingWithTime();
        }
    };

    private void sendNotification(String title, String text) {

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.drawable.chef);
        notification.setContentTitle(title);
        notification.setContentText(text);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(1, notification.build());
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
                break;
            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
                break;
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
                break;
        }
    }
}
