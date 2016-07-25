package miteat.miteat.SQLlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.Meeting;

/**
 * Created by Itzik on 24/07/2016.
 */
public class MeetingSql {


    private static final String MEETING_TABLE = "meeting_table";
    private static final String ID = "id";
    private static final String NUMBER_OF_PARTNER = "numberOfPartner";
    private static final String TYPE_OF_FOOD = "typeOfFood";
    private static final String MONEY = "money";
    private static final String DATE_AND_TIME = "dateAndTime";
    private static final String LOCATION = "location";
    private static final String INSURANCE = "insurance";
    private static final String IMAGE = "image";
    private static final String FOOD_PORTIONS_IDS = "foodPortionsIds";


    public static void addmeeting(SQLiteDatabase db, Meeting meeting) {

        ContentValues values = new ContentValues();
        values.put(ID, meeting.getId());
        values.put(NUMBER_OF_PARTNER, meeting.getNumberOfPartner());
        values.put(TYPE_OF_FOOD, meeting.getTypeOfFood());
        values.put(MONEY, meeting.getMoney());
        values.put(DATE_AND_TIME, meeting.getDateAndTime());
        values.put(LOCATION, meeting.getLocation());
        values.put(INSURANCE, meeting.getInsurance());
        values.put(FOOD_PORTIONS_IDS, meeting.getFoodPortionsIds());
        db.insert(MEETING_TABLE, ID, values);

    }

    public static Meeting getMeeting(SQLiteDatabase db, int id) {

//        Cursor cursor = db.query(GPS_TABLE, null, null, null, null, null, null);
//        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
//            Gps gps = null;
//            return gps;
//        }
//
//        if (cursor.moveToFirst()) {
//            int id = cursor.getColumnIndex(ID);
//            int longitude = cursor.getColumnIndex(LONGITUDE);
//            int latitude = cursor.getColumnIndex(LATITUDE);
//            int time = cursor.getColumnIndex(TIME);
//            Gps gps = new Gps(Integer.getInteger(cursor.getString(id)), cursor.getString(longitude), cursor.getString(latitude), Long.parseLong(cursor.getString(time)));
//            return gps;
//        }
//        Gps gps = null;
        return null;
    }

    public static void deleteMeeting(SQLiteDatabase db, int id) {

        db.delete(MEETING_TABLE, ID + " = '" + id + "'", null);

    }


    public static void create(SQLiteDatabase db) {
        db.execSQL("create table " +
                MEETING_TABLE + " (" +
                ID + " INTEGER," +
                NUMBER_OF_PARTNER + " INTEGER," +
                TYPE_OF_FOOD + " TEXT," +
                MONEY + " INTEGER," +
                DATE_AND_TIME + " TEXT," +
                LOCATION + " TEXT," +
                INSURANCE + " TEXT," +
                IMAGE + " TEXT," +
                FOOD_PORTIONS_IDS + " INTEGER);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + MEETING_TABLE);
    }
}


