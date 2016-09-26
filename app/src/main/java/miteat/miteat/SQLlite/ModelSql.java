package miteat.miteat.SQLlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import miteat.miteat.Model.Entities.FoodPortions;
import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.User;
import miteat.miteat.Model.Entities.UserDetails;
import miteat.miteat.Model.ModelInterface;
import miteat.miteat.MyApplication;

/**
 * Created by Itzik on 05/06/2016.
 */
public class ModelSql implements ModelInterface {


    private static final int VERSION = 3;

    MyDBHelper dbHelper;
    private static final String USER_TABLE = "user_table";
    private static final String GPS_TABLE = "gps_table";
    private static final String MEETING_TABLE = "meeting_table";
    private static final String FOOD_PORTIONS_TABLE = "food_portions_table";
    private static final String USER_DETAILS_TABLE = "user_details_table";


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
        if (num == 1) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            GpsSql.deleteGps(db, 1);
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
        } catch (Exception e) {
            return 1;
        }
        Cursor cursor = db.rawQuery(countQuery, null);
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            return 1;
        }
        int cnt = cursor.getCount();
        //  cursor.close();
        return cnt;
    }

    @Override
    public void addMeeting(Meeting meeting) {
       // int num = numberOfRow(MEETING_TABLE);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(MEETING_TABLE, null, null, null, null, null, null);
        int num = cursor.getCount();
        if(meeting.getId()==-1){
            meeting.setId(num);
        }
        MeetingSql.addMeeting(db, meeting);
        for(int i =0; i<meeting.getFoodPortionsId().size();i++){
            meeting.getFoodPortionsId().get(i).setId(i);
            meeting.getFoodPortionsId().get(i).setMeetingId(meeting.getId());
            addFoodPortions( meeting.getFoodPortionsId().get(i));

        }

    }
    public void addFoodPortions(FoodPortions foodPortions){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        FoodPortionsSql.addFoodPortions(db,foodPortions);
    }

    @Override
    public List<Meeting> getAllMeeting() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Meeting> meetings = MeetingSql.getAllMeeting(db);
        for(int i =0;i<meetings.size();i++){
            int[] ids = MeetingSql.getFoodPortions(db, meetings.get(i).getId());
            meetings.get(i).setFoodPortionsId(FoodPortionsSql.getAllPortionsId(db, ids, meetings.get(i).getId()));
        }
        return meetings;
    }

    @Override
    public Meeting getMeeting(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Meeting meeting = MeetingSql.getMeeting(db, id);
        int[] ids = MeetingSql.getFoodPortions(db, id);
        meeting.setFoodPortionsId(FoodPortionsSql.getAllPortionsId(db, ids, id));
        return meeting;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        MeetingSql.deleteMeeting(db,meeting.getId());
        FoodPortionsSql.deleteFoodPortions(db,meeting.getId());
    }

    @Override
    public Gps getGpsLocation() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return GpsSql.getGps(db);
    }

    @Override
    public UserDetails getUserDetails(String id) {
        return null;
    }

    @Override
    public void bookingToMeeting() {

    }

    @Override
    public void setUserDetails(UserDetails userDetails) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        UserDetailsSql.addUserDetails(db,userDetails);
    }

    @Override
    public UserDetails getUserDetails() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return UserDetailsSql.getUserDetails(db);
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
            FoodPortionsSql.create(db);
            UserDetailsSql.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            LoginSql.drop(db);
            GpsSql.drop(db);
            MeetingSql.drop(db);
            FoodPortionsSql.drop(db);
            UserDetailsSql.drop(db);
            onCreate(db);
        }
    }
}
