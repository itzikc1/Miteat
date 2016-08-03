package miteat.miteat.SQLlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.MyApplication;
import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.User;
import miteat.miteat.Model.ModelInterface;

/**
 * Created by Itzik on 05/06/2016.
 */
public class ModelSql implements ModelInterface {


    private static final int VERSION = 2;

    MyDBHelper dbHelper;
    private static final String USER_TABLE = "user_table";
    private static final String GPS_TABLE = "gps_table";
    private static final String MEETING_TABLE = "meeting_table";


    public ModelSql() {
        dbHelper = new MyDBHelper(MyApplication.getAppContext());
    }

    @Override
    public Boolean checkIfUserExist(User user) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return LoginSql.checkUser(db, user);
    }

    @Override
    public void addGps(Gps gps) {
        int num = numberOfRow(GPS_TABLE);
        if(num==1){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            GpsSql.deleteGps(db,1);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
         GpsSql.addGps(db, gps);
    }

    @Override
    public int numberOfRow(String tableName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + tableName;
        try {
            Cursor cursor = db.rawQuery(countQuery, null);
        }catch (Exception e){
            return 1;
        }
        Cursor cursor = db.rawQuery(countQuery, null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return 1;
        }
        int cnt = cursor.getCount();
        //  cursor.close();
        return cnt;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        int num = numberOfRow(MEETING_TABLE);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        meeting.setId(num);
        MeetingSql.addmeeting(db,meeting);

    }

    @Override
    public List<Meeting> getAllMeeting() {
        return null;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {

    }

    @Override
    public Gps getGpsLocation() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return GpsSql.getGps(db);
    }

    class MyDBHelper extends SQLiteOpenHelper {

        public MyDBHelper(Context context) {
            super(context, "database.db", null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //create the DB schema
            LoginSql.create(db);
            GpsSql.create(db);
            MeetingSql.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            LoginSql.drop(db);
            GpsSql.drop(db);
            MeetingSql.drop(db);
            onCreate(db);
        }
    }
}
