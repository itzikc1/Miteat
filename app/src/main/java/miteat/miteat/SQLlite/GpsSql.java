package miteat.miteat.SQLlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.User;

/**
 * Created by Itzik on 07/07/2016.
 */
public class GpsSql {


    private static final String GPS_TABLE = "gps_table";
    private static final String ID = "id";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String TIME = "time";

    public static void addGps(SQLiteDatabase db, Gps gps) {

//        String countQuery = "SELECT  * FROM " + GPS_TABLE;
//            Cursor cursor = db.rawQuery(countQuery, null);
//        int cnt = cursor.getCount();
//        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){

            ContentValues values = new ContentValues();
            values.put(ID, gps.getId());
            values.put(LONGITUDE, gps.getLongitude());
            values.put(LATITUDE, gps.getLatitude());
            values.put(TIME, gps.getTime().toString());
            db.insert(GPS_TABLE, ID, values);
     //   }
//        else{
//            deleteGps(db,cnt);
//            addGps(db,gps);
//        }
    }

    public static Gps getGps(SQLiteDatabase db) {

        Cursor cursor = db.query(GPS_TABLE, null, null, null, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            Gps gps = null;
            return gps;
        }

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(ID);
            int longitude = cursor.getColumnIndex(LONGITUDE);
            int latitude = cursor.getColumnIndex(LATITUDE);
            int time = cursor.getColumnIndex(TIME);
           // Gps gps = new Gps(Integer.getInteger(cursor.getString(id)), cursor.getString(longitude), cursor.getString(latitude), Long.parseLong(cursor.getString(time)));
            Gps gps = new Gps(1, cursor.getString(longitude), cursor.getString(latitude), Long.parseLong(cursor.getString(time)));
            return gps;
        }
        Gps gps = null;
        return gps;
    }

    public static void deleteGps(SQLiteDatabase db, int id) {

        db.delete(GPS_TABLE, ID + " = '" + id + "'", null);

    }

    public static void create(SQLiteDatabase db) {
        db.execSQL("create table " +
                GPS_TABLE + " (" +
                ID + " INTEGER," +
                LATITUDE + " TEXT," +
                LONGITUDE + " TEXT," +
                TIME + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + GPS_TABLE);
    }
}
