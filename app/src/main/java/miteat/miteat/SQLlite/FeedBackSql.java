package miteat.miteat.SQLlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import miteat.miteat.Model.Entities.Feedback;
import miteat.miteat.Model.Entities.UserDetails;

/**
 * Created by Itzik on 28/09/2016.
 */
public class FeedBackSql {


    private static final String FEED_BACK_TABLE = "feedBack_table";
    private static final String ID = "id";
    private static final String ID_BOOKING = "idBooking";
    private static final String ID_MEETING = "idMeeting";
    private static final String FROM_USER_ID = "fromUserId";
    private static final String TO_USER_ID = "toUserId";
    private static final String FEEDBACK_TEXT = "feedBackText";
    private static final String REPLY_TEXT = "replyText";
    private static final String AVG_STAR = "avgStar";
    private static final String CLEANING_STAR = "cleaningStar";
    private static final String SERVICE_STAR = "serviceStar";
    private static final String ATMOSPHERE_STAR = "atmosphereStar";
    private static final String VALUE_STAR = "valueStar";
    private static final String TAKE_AWAY = "take_away";
    private static final String DATE_AND_TIME = "date_and_time";


    public static void addFeedBackFromUser(SQLiteDatabase db, Feedback feedback) {

        ContentValues values = new ContentValues();
        values.put(ID,feedback.getId());
        values.put(ID_BOOKING,feedback.getIdBooking());
        values.put(ID_MEETING, feedback.getIdMeeting());
        values.put(FROM_USER_ID,feedback.getFromUserId());
        values.put(TO_USER_ID,feedback.getToUserId());
        values.put(FEEDBACK_TEXT, feedback.getFeedBackText());
        values.put(REPLY_TEXT, feedback.getReplyText());
        values.put(AVG_STAR, feedback.getAvgStar());
        values.put(CLEANING_STAR,feedback.getCleaningStar());
        values.put(SERVICE_STAR, feedback.getServiceStar());
        values.put(ATMOSPHERE_STAR, feedback.getAtmosphereStar());
        values.put(VALUE_STAR, feedback.getValueStar());
        values.put(TAKE_AWAY, feedback.isTakeAwy());
        values.put(DATE_AND_TIME, feedback.getDateAndTime());
        db.insert(FEED_BACK_TABLE, ID, values);
    }

    public static Feedback getFeedback(SQLiteDatabase db,String idUserGaveFeedback,String idBooking, int IdMeeting, Long startTime) {


        String[] selectionArgs = {String.valueOf(idUserGaveFeedback),String.valueOf(idBooking), String.valueOf(IdMeeting),String.valueOf(startTime)};
        Cursor cursor = db.query(FEED_BACK_TABLE, null, FROM_USER_ID + " = ?" + "and " + ID_BOOKING + " = ?" + "and " + ID_MEETING + " = ?" + "and " + DATE_AND_TIME + " = ?", selectionArgs, null, null, null);



        Feedback feedback = null;
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            return feedback;
        }
        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(ID);
           // int idMeetingIndex = cursor.getColumnIndex(ID_MEETING);
           // int idBookingIndex = cursor.getColumnIndex(ID_BOOKING);
            int fromUserIdIndex = cursor.getColumnIndex(FROM_USER_ID);
            int toUserIdIndex = cursor.getColumnIndex(TO_USER_ID);
            int feedBackTextIndex = cursor.getColumnIndex(FEEDBACK_TEXT);
            int replyTextIndex = cursor.getColumnIndex(REPLY_TEXT);
            int avgStarIndex = cursor.getColumnIndex(AVG_STAR);
            int cleaningStarIndex = cursor.getColumnIndex(CLEANING_STAR);
            int serviceStarIndex = cursor.getColumnIndex(SERVICE_STAR);
            int atmosphereStarIndex = cursor.getColumnIndex(ATMOSPHERE_STAR);
            int valueStarIndex = cursor.getColumnIndex(VALUE_STAR);
            int takeAwyIndex = cursor.getColumnIndex(TAKE_AWAY);
          //  int dateAndTimeIndex = cursor.getColumnIndex(DATE_AND_TIME);

            int id = Integer.parseInt(cursor.getString(idIndex));
            String fromUserId = cursor.getString(fromUserIdIndex);
            String toUserId = cursor.getString(toUserIdIndex);
            String feedBackText = cursor.getString(feedBackTextIndex);
            String replyText = cursor.getString(replyTextIndex);
            float avgStar = Float.parseFloat(cursor.getString(avgStarIndex));
            float cleaningStar = Float.parseFloat(cursor.getString(cleaningStarIndex));
            float serviceStar = Float.parseFloat(cursor.getString(serviceStarIndex));
            float atmosphereStar = Float.parseFloat(cursor.getString(atmosphereStarIndex));
            float valueStar = Float.parseFloat(cursor.getString(valueStarIndex));
          //  Long dateAndTime = Long.parseLong(cursor.getString(dateAndTimeIndex));
            Boolean takeAway = Boolean.parseBoolean(cursor.getString(takeAwyIndex));

            feedback = new Feedback(id,IdMeeting,idBooking,fromUserId,toUserId,feedBackText,replyText,cleaningStar,serviceStar,atmosphereStar,valueStar,takeAway,startTime);

            }
        return feedback;
    }


    public static void create(SQLiteDatabase db) {
        db.execSQL("create table " +
                FEED_BACK_TABLE + " (" +
                ID + " INTEGER," +
                ID_BOOKING + " TEXT," +
                ID_MEETING + " INTEGER," +
                FROM_USER_ID + " TEXT," +
                TO_USER_ID + " TEXT," +
                FEEDBACK_TEXT + " TEXT," +
                REPLY_TEXT + " TEXT," +
                AVG_STAR + " FLOAT," +
                CLEANING_STAR + " FLOAT," +
                SERVICE_STAR + " FLOAT," +
                ATMOSPHERE_STAR + " FLOAT," +
                VALUE_STAR + " FLOAT," +
                TAKE_AWAY + " BOOLEAN," +
                DATE_AND_TIME + " TEXT);");

     }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + FEED_BACK_TABLE);
    }

}



