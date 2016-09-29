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


    public static void addBooking(SQLiteDatabase db, Booking booking) {

        ContentValues values = new ContentValues();
        values.put(ID, booking.getId());
        values.put(MEETING, booking.getMeeting().getId());
        values.put(CONFIRMATION, booking.isConfirmation());
        values.put(NUMBER_OF_PARTNER, booking.getNumberOfPartner());
        values.put(USER_ID_OF_BOOKING, booking.getUserIdOfBooking());
        db.insert(MY_BOOKING_TABLE, ID, values);

    }


    public static List<Booking> getAllBooking(SQLiteDatabase db) {

        List<Booking> bookings = new LinkedList<Booking>();

        Cursor cursor = db.query(MY_BOOKING_TABLE, null, null, null, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {

            return bookings;
        }


        if (cursor.moveToFirst()) {


            int idIndex = cursor.getColumnIndex(ID);
            int meetingIndex = cursor.getColumnIndex(MEETING);
            int confirmationIndex = cursor.getColumnIndex(CONFIRMATION);
            int numberOfPartnerIndex = cursor.getColumnIndex(NUMBER_OF_PARTNER);
            int userIdOfBookingIndex = cursor.getColumnIndex(USER_ID_OF_BOOKING);

            do {
                String id = cursor.getString(idIndex);
                Meeting meeting = new Meeting(Integer.parseInt(cursor.getString(meetingIndex)));
              //  meeting.setId(Integer.parseInt(cursor.getString(meetingIndex)));
                boolean confirmation = Boolean.parseBoolean(cursor.getString(confirmationIndex));
                int numberOfPartner = Integer.parseInt(cursor.getString(numberOfPartnerIndex));
                String userIdOfBooking = cursor.getString(userIdOfBookingIndex);
                Booking booking = new Booking(id, meeting, userIdOfBooking);
                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        return bookings;
    }


    public static void deleteBooking(SQLiteDatabase db, String id) {
        db.delete(MY_BOOKING_TABLE, ID + " = '" + id + "'", null);
    }

    public static void create(SQLiteDatabase db) {
        db.execSQL("create table " +
                MY_BOOKING_TABLE + " (" +
                ID + " TEXT," +
                MEETING + " INTEGER," +
                CONFIRMATION + " BOOLEAN," +
                NUMBER_OF_PARTNER + " INTEGER," +
                USER_ID_OF_BOOKING + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + MY_BOOKING_TABLE);
    }

}
