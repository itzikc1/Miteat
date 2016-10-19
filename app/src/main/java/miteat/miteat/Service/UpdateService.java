package miteat.miteat.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import miteat.miteat.Model.Model;

/**
 * Created by Itzik on 19/10/2016.
 */
public class UpdateService extends Service {
    private static final int MINUTE = 1000 * 60 *1 ;
    Intent intent;
    private final Handler handler = new Handler();


    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent();
    }




//    @Override
//    public void onStart(Intent intent, int startId) {
//        handler.removeCallbacks(doUpdates);
//        handler.postDelayed(doUpdates, TWO_MINUTES);
//    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        handler.removeCallbacks(doUpdates);
         handler.postDelayed(doUpdates, 1000);

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
            Log.d("TAG", "service update");
            Model.instance().updateMeetingToBookingWithTime();

            //to do
        }
    };
}
