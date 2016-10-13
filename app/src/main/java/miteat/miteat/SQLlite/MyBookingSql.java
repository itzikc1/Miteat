package miteat.miteat.SQLlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import miteat.miteat.Model.Entities.Booking;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.UserDetails;

/**
 * Created by Itzik on 27/09/2016.
 */
public class MyBookingSql {


    private static final String MY_BOOKING_TABLE = "my_booking_table";
    private static final String ID = "id";
    private static final String MEETING = "meeting";
    private static final String CONFIRMATION = "confirmation";
    private static final String NUMBER_OF_PARTNER = "number_of_partner";
    private static final String USER_ID_OF_BOOKING = "user_id_of_booking";
    private static final String MEETING_OR_BOOKING = "meeting_or_booking";
    private static final String HISTORY = "history";

    public static void addBooking(SQLiteDatabase db, Booking booking) {

        ContentValues values = new ContentValues();
        values.put(ID, booking.getId());
        values.put(MEETING, booking.getMeeting().getId());
        values.put(CONFIRMATION, booking.getConfirmation());
        values.put(NUMBER_OF_PARTNER, booking.getNumberOfPartner());
        values.put(USER_ID_OF_BOOKING, booking.getUserIdOfBooking());
        values.put(MEETING_OR_BOOKING, booking.getMeetingOrBooking());
        values.put(HISTORY, 0);
        db.insert(MY_BOOKING_TABLE, ID, values);

    }


    public static List<Booking> getAllBooking(SQLiteDatabase db) {

        List<Booking> bookings = new LinkedList<Booking>();



        String[] selectionArgs = {String.valueOf(1), String.valueOf(0)};
        Cursor cursor = db.query(MY_BOOKING_TABLE, null, MEETING_OR_BOOKING + " = ?" + "and " + HISTORY + " = ?", selectionArgs, null, null, null);


//        String[] params = new String[1];
//        params[0] = String.valueOf(1);
//        Cursor cursor = db.query(MY_BOOKING_TABLE, null, MEETING_OR_BOOKING + " = ?", params, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {

            return bookings;
        }

        if (cursor.moveToFirst()) {


            int idIndex = cursor.getColumnIndex(ID);
            int meetingIndex = cursor.getColumnIndex(MEETING);
            int confirmationIndex = cursor.getColumnIndex(CONFIRMATION);
            int numberOfPartnerIndex = cursor.getColumnIndex(NUMBER_OF_PARTNER);
            int userIdOfBookingIndex = cursor.getColumnIndex(USER_ID_OF_BOOKING);
            int meetingOrBookingIndex = cursor.getColumnIndex(MEETING_OR_BOOKING);

            int historyIndex = cursor.getColumnIndex(HISTORY);

            do {

                String id = cursor.getString(idIndex);
                Meeting meeting = new Meeting(Integer.parseInt(cursor.getString(meetingIndex)));
                int confirmation = Integer.parseInt(cursor.getString(confirmationIndex));
                int numberOfPartner = Integer.parseInt(cursor.getString(numberOfPartnerIndex));
                String userIdOfBooking = cursor.getString(userIdOfBookingIndex);
                int meetingOrBooking = Integer.parseInt(cursor.getString(meetingOrBookingIndex));

                int history = Integer.parseInt(cursor.getString(historyIndex));

                Booking booking = new Booking(id, meeting, userIdOfBooking, meetingOrBooking);
                booking.setConfirmation(confirmation);
                booking.setNumberOfPartner(numberOfPartner);


                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        return bookings;
    }

    public static List<Booking> getAllHistory(SQLiteDatabase db) {

        List<Booking> bookings = new LinkedList<Booking>();


                String[] params = new String[1];
        params[0] = String.valueOf(1);
        Cursor cursor = db.query(MY_BOOKING_TABLE, null, HISTORY + " = ?", params, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            return bookings;
        }
        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(ID);
            int meetingIndex = cursor.getColumnIndex(MEETING);
            int confirmationIndex = cursor.getColumnIndex(CONFIRMATION);
            int numberOfPartnerIndex = cursor.getColumnIndex(NUMBER_OF_PARTNER);
            int userIdOfBookingIndex = cursor.getColumnIndex(USER_ID_OF_BOOKING);
            int meetingOrBookingIndex = cursor.getColumnIndex(MEETING_OR_BOOKING);

            int historyIndex = cursor.getColumnIndex(HISTORY);

            do {

                String id = cursor.getString(idIndex);
                Meeting meeting = new Meeting(Integer.parseInt(cursor.getString(meetingIndex)));
                int confirmation = Integer.parseInt(cursor.getString(confirmationIndex));
                int numberOfPartner = Integer.parseInt(cursor.getString(numberOfPartnerIndex));
                String userIdOfBooking = cursor.getString(userIdOfBookingIndex);
                int meetingOrBooking = Integer.parseInt(cursor.getString(meetingOrBookingIndex));

                int history = Integer.parseInt(cursor.getString(historyIndex));
                Booking booking = new Booking(id, meeting, userIdOfBooking, meetingOrBooking);
                booking.setConfirmation(confirmation);
                booking.setNumberOfPartner(numberOfPartner);
                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        return bookings;
    }

    public static void enterBookingOrMeetingToHistory(SQLiteDatabase db, String idBooking, int idMeeting) {

        ContentValues args = new ContentValues();
        args.put(HISTORY,1);
        db.update(MY_BOOKING_TABLE, args, ID + "= ?"+ "and " + MEETING + " = ?", new String[]{String.valueOf(idBooking),String.valueOf(idMeeting)});

    }

    public static List<Booking> getAllMeeting(SQLiteDatabase db) {

        List<Booking> bookings = new LinkedList<Booking>();

        String[] selectionArgs = {String.valueOf(0), String.valueOf(0)};
        Cursor cursor = db.query(MY_BOOKING_TABLE, null, MEETING_OR_BOOKING + " = ?" + "and " + HISTORY + " = ?", selectionArgs, null, null, null);

//        String[] params = new String[1];
//        params[0] = String.valueOf(0);
//        Cursor cursor = db.query(MY_BOOKING_TABLE, null, MEETING_OR_BOOKING + " = ?", params, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            return bookings;
        }
        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(ID);
            int meetingIndex = cursor.getColumnIndex(MEETING);
            int confirmationIndex = cursor.getColumnIndex(CONFIRMATION);
            int numberOfPartnerIndex = cursor.getColumnIndex(NUMBER_OF_PARTNER);
            int userIdOfBookingIndex = cursor.getColumnIndex(USER_ID_OF_BOOKING);
            int meetingOrBookingIndex = cursor.getColumnIndex(MEETING_OR_BOOKING);

            do {
                String id = cursor.getString(idIndex);
                Meeting meeting = new Meeting(Integer.parseInt(cursor.getString(meetingIndex)));
                int confirmation = Integer.parseInt(cursor.getString(confirmationIndex));
                int numberOfPartner = Integer.parseInt(cursor.getString(numberOfPartnerIndex));
                String userIdOfBooking = cursor.getString(userIdOfBookingIndex);
                int meetingOrBooking = Integer.parseInt(cursor.getString(meetingOrBookingIndex));
                Booking booking = new Booking(id, meeting, userIdOfBooking, meetingOrBooking);
                booking.setConfirmation(confirmation);
                booking.setNumberOfPartner(numberOfPartner);
                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        return bookings;
    }

    //accept the meeting
    public static void makeAcceptInMyMeeting(SQLiteDatabase db, Booking booking) {

        ContentValues args = new ContentValues();
        args.put(CONFIRMATION, booking.getConfirmation());
        db.update(MY_BOOKING_TABLE, args, ID + "= ?", new String[]{String.valueOf(booking.getId())});

    }

    public static int getAllBookingNumberOfPartner(SQLiteDatabase db, Meeting meeting) {

        int number = 0;
        String[] selectionArgs = {meeting.getUserId(), String.valueOf(meeting.getId())};
        Cursor cursor = db.query(MY_BOOKING_TABLE, null, ID + " = ?" + "and " + MEETING + " = ?", selectionArgs, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            return number;
        }


        if (cursor.moveToFirst()) {

            int numberOfPartnerIndex = cursor.getColumnIndex(NUMBER_OF_PARTNER);
            do {
                int numberOfPartner = Integer.parseInt(cursor.getString(numberOfPartnerIndex));
                number += numberOfPartner;
            } while (cursor.moveToNext());

        }

        return number;

    }

    public static void deleteBooking(SQLiteDatabase db, String id) {
        db.delete(MY_BOOKING_TABLE, ID + " = '" + id + "'", null);
    }

    public static void deleteRefuseBooking(SQLiteDatabase db, String id, int meetingId) {
        String[] selectionArgs = {id, String.valueOf(meetingId)};
        String[] params = new String[1];
        params[0] = String.valueOf(0);
        db.delete(MY_BOOKING_TABLE, ID + " = '" + id + "'" + " and " + MEETING + " = '" + meetingId + "'", null);

    }

    public static void create(SQLiteDatabase db) {
        db.execSQL("create table " +
                MY_BOOKING_TABLE + " (" +
                ID + " TEXT," +
                MEETING + " INTEGER," +
                CONFIRMATION + " INTEGER," +
                MEETING_OR_BOOKING + " INTEGER," +
                NUMBER_OF_PARTNER + " INTEGER," +
                HISTORY + " INTEGER," +
                USER_ID_OF_BOOKING + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + MY_BOOKING_TABLE);
    }

}
