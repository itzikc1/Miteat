package miteat.miteat.SQLlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Itzik on 27/09/2016.
 */
public class IdMeetingMakerSql {
    private static final String ID_MEETING_MAKER = "id_meeting_maker";
    private static final String ID = "id";

    public static void addId(SQLiteDatabase db, int id) {

        ContentValues values = new ContentValues();
        values.put(ID, id + 1);
        db.insert(ID_MEETING_MAKER, ID, values);
    }

    public static int getId(SQLiteDatabase db) {

        Cursor cursor = db.query(ID_MEETING_MAKER, null, null, null, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            addId(db, 0);
            return 0;
        }

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ID);
            int id = Integer.parseInt(cursor.getString(idIndex));
            return id;
        }
        return 0;
    }

    public static void deleteId(SQLiteDatabase db, int id) {
        db.delete(ID_MEETING_MAKER, ID + " = '" + id + "'", null);
        addId(db, id);
    }


    public static void create(SQLiteDatabase db) {
        db.execSQL("create table " +
                ID_MEETING_MAKER + " (" +
                ID + " INTEGER);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + ID_MEETING_MAKER);
    }


}
