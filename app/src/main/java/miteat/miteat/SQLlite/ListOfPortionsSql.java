package miteat.miteat.SQLlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import miteat.miteat.Model.Entities.ListOfPortions;

/**
 * Created by Itzik on 24/07/2016.
 */
public class ListOfPortionsSql {

    private static final String PORTIONS_LIST_TABLE = "listOfPortions_table";
    private static final String ID = "id";
    private static final String MEETING_ID = "meetingId";
    private static final String PORTIONS_IDS = "portionsIds";



    public static void addListOfPortions(SQLiteDatabase db, ListOfPortions listOfPortions) {

        ContentValues values = new ContentValues();
        values.put(ID, listOfPortions.getId());
        values.put(MEETING_ID, listOfPortions.getMeetingId());
      //  values.put(PORTIONS_IDS, listOfPortions.getTypeOfFood());

        db.insert(PORTIONS_LIST_TABLE, ID, values);

    }

}
