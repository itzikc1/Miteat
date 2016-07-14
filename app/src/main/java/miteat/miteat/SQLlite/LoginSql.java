package miteat.miteat.SQLlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import miteat.miteat.Model.Entities.User;

/**
 * Created by Itzik on 05/06/2016.
 */
public class LoginSql {

    private static final String  USER_TABLE = "user_table";
    private static final String PERSON_ID = "personId";
    private static final String PASSWORD = "password";
    private static final String CHEKKBOX = "checkBox";

    public static void register(SQLiteDatabase db, User sp) {

        ContentValues values = new ContentValues();
        values.put(PERSON_ID, sp.getUser());
        values.put(PASSWORD, sp.getPassword());
        values.put(CHEKKBOX, sp.getCheckBoxStart().toString());
        db.insert(USER_TABLE, PERSON_ID,values);
    }
    public static User getUser(SQLiteDatabase db) {

        User logIn = null;

        Cursor cursor = db.query(USER_TABLE, null, null, null, null, null, null);


        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return logIn = new User("1","1");
        }

        if (cursor.moveToFirst()) {
            int personId = cursor.getColumnIndex(PERSON_ID);
            int password = cursor.getColumnIndex(PASSWORD);

            logIn = new User(cursor.getString(personId), cursor.getString(password));
            return logIn;
        }

        return logIn;
    }


    public static boolean checkUser(SQLiteDatabase db, User sp) {

        User logData = getUser(db);

        if ((logData.getUser().equals(sp.getUser()))&&(logData.getPassword().equals(sp.getPassword()))) {
            return true;
        } else {
            return false;
        }
    }
    public static void create(SQLiteDatabase db) {
        db.execSQL("create table " +
                USER_TABLE + " (" +
                PERSON_ID + " TEXT," +
                PASSWORD + " TEXT," +
                CHEKKBOX + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + USER_TABLE);
    }

}
