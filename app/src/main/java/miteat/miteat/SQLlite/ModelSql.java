package miteat.miteat.SQLlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import miteat.miteat.Model.Entities.Booking;
import miteat.miteat.Model.Entities.Feedback;
import miteat.miteat.Model.Entities.FoodPortions;
import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.History;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.User;
import miteat.miteat.Model.Entities.UserDetails;
import miteat.miteat.Model.ModelInterface;
import miteat.miteat.MyApplication;

/**
 * Created by Itzik on 05/06/2016.
 */
public class ModelSql implements ModelInterface {


    private static final int VERSION = 1;
    public SaveInFirebase saveInFirebase;
    MyDBHelper dbHelper;
    private static final String USER_TABLE = "user_table";
    private static final String GPS_TABLE = "gps_table";
    private static final String MEETING_TABLE = "meeting_table";
    private static final String FOOD_PORTIONS_TABLE = "food_portions_table";
    private static final String USER_DETAILS_TABLE = "user_details_table";
    private static final String ID_MEETING_MAKER = "id_meeting_maker";
    private static final String FEED_BACK_TABLE = "feedBack_table";
    private static final String MY_BOOKING_TABLE = "my_booking_table";
    private static final String MY_BOOKING_MEETING_TABLE = "my_booking_meeting_table";
    private static final String MY_BOOKING_FOOD_PORTIONS_TABLE = "my_booking_food_portions_table";
    private String userName ="nivi";

    public ModelSql() {
        saveInFirebase = new SaveInFirebase(MyApplication.getAppContext());
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
        return cnt;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (meeting.getId() == -1) {
            int id = IdMeetingMakerSql.getId(db);
            IdMeetingMakerSql.deleteId(db, id);
            meeting.setId(id);
        }
        MeetingSql.addMeeting(db, meeting);
        saveInFirebase.addMeeting(meeting);
        for (int i = 0; i < meeting.getFoodPortionsId().size(); i++) {
            meeting.getFoodPortionsId().get(i).setId(i);
            meeting.getFoodPortionsId().get(i).setMeetingId(meeting.getId());
            addFoodPortions(meeting.getFoodPortionsId().get(i));
        }
    }

    public void addFoodPortions(FoodPortions foodPortions) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        FoodPortionsSql.addFoodPortions(db, foodPortions);
    }

    @Override
    public List<Meeting> getAllMeeting() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // List<Meeting> meetings = MeetingSql.getAllMeeting(db);
        List<Meeting> meetings = MeetingSql.getAllMeeting(db);
        for (int i = 0; i < meetings.size(); i++) {
            int[] ids = MeetingSql.getFoodPortions(db, meetings.get(i).getId());
            meetings.get(i).setFoodPortionsId(FoodPortionsSql.getAllPortionsId(db, ids, meetings.get(i).getId()));
        }
        return meetings;

        // return modelFirebase.getAllMeeting();
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
        MeetingSql.deleteMeeting(db, meeting.getId());
        //check if all ready booking
        saveInFirebase.deleteMeeting(meeting);
        FoodPortionsSql.deleteFoodPortions(db, meeting.getId());
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
    public void bookingToMeeting(Booking booking) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        MyBookingSql.addBooking(db, booking);//enter the booking to table
        addMeetingForBooking(booking.getMeeting());//add the meting with user id to meeting table
        //  addToMeetingAfterBooking(booking);
        saveInFirebase.bookingToMeeting(booking);
    }

    //this for the host table with fierbase need to change
    public void addToMeetingAfterBooking(Booking booking) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        booking.setMeetingOrBooking(0);
        MyBookingSql.addBooking(db, booking);//enter the booking to table
        addMeetingForBooking(booking.getMeeting());//add the meting with user id to meeting table
    }

    public void addMeetingForBooking(Meeting meeting) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        MyBookingMeetingSql.addMeeting(db, meeting);

        for (int i = 0; i < meeting.getFoodPortionsId().size(); i++) {
            meeting.getFoodPortionsId().get(i).setId(i);
            meeting.getFoodPortionsId().get(i).setMeetingId(meeting.getId());
            addMyBookingFoodPortions(meeting.getFoodPortionsId().get(i), meeting.getUserId());
        }

    }

    public void addMyBookingFoodPortions(FoodPortions foodPortions, String userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        MyBookingFoodPortionsSql.addFoodPortions(db, foodPortions, userId);//enter to MyBookingFoodPortions table by user name
    }


    @Override
    public void setUserDetails(UserDetails userDetails) {

        int num = numberOfRow(USER_DETAILS_TABLE);
        if (num == 1) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            UserDetailsSql.deleteUserDetails(db, userDetails.getUserName());
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        UserDetailsSql.addUserDetails(db, userDetails);
        saveInFirebase.setUserDetails(userDetails);
    }

    @Override
    public UserDetails getUserDetails() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(USER_DETAILS_TABLE, null, null, null, null, null, null);
        int num = cursor.getCount();

        if (num == 0) {
            UserDetails userDetails = new UserDetails(userName, "mail", "0525541676");
            // userDetails.setNumberOfStarAvg(3.6f);
            return userDetails;
        }

        // UserDetails userDetails=UserDetailsSql.getUserDetails(db);
        //userDetails.setFeedbacks();
        return UserDetailsSql.getUserDetails(db);
    }

    @Override
    public List<Booking> getMyBookingList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Booking> bookings = MyBookingSql.getAllBooking(db);
        for (int i = 0; i < bookings.size(); i++) {
            SQLiteDatabase dbc = dbHelper.getReadableDatabase();
            Booking booking = bookings.get(i);
            bookings.get(i).setMeeting(MyBookingMeetingSql.getMeetingWithUser(dbc, booking.getMeeting().getId(), booking.getId()));
            Calendar cls = Calendar.getInstance();
            Long nowTime = new Long(0);
            nowTime = cls.getTimeInMillis();
            if (nowTime.compareTo(bookings.get(i).getMeeting().getDateAndEndTime()) == 1) {
                SQLiteDatabase dbe = dbHelper.getWritableDatabase();
                MyBookingSql.enterBookingOrMeetingToHistory(dbe, bookings.get(i).getId(), bookings.get(i).getMeeting().getId());
                if (bookings.get(i).getConfirmation() == 0 || bookings.get(i).getConfirmation() == 2) {
                    makeRefuseFromMyBooking(bookings.get(i));
                }
                bookings.remove(i);
                continue;
            }

            SQLiteDatabase dbp = dbHelper.getReadableDatabase();
            int[] ids = MyBookingMeetingSql.getFoodPortions(dbp, booking.getMeeting().getId(), booking.getId());
            bookings.get(i).getMeeting().setFoodPortionsId(MyBookingFoodPortionsSql.getAllPortionsId(dbp, ids, booking.getMeeting().getId(), booking.getId()));

        }
        return bookings;
    }

    @Override
    public Boolean checkIfBooking(Booking booking) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Meeting meeting1 = MyBookingMeetingSql.getMeetingWithUser(db, booking.getMeeting().getId(), booking.getMeeting().getUserId());
        if (meeting1 == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<Booking> getOrderToBooking() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //get from firebase
        List<Booking> bookings = MyBookingSql.getAllMeeting(db);
        for (int i = 0; i < bookings.size(); i++) {
            SQLiteDatabase dbc = dbHelper.getReadableDatabase();
            Booking booking = bookings.get(i);
            bookings.get(i).setMeeting(MyBookingMeetingSql.getMeetingWithUser(dbc, booking.getMeeting().getId(), booking.getId()));

            Calendar cls = Calendar.getInstance();
            Long nowTime = new Long(0);
            nowTime = cls.getTimeInMillis();
            if (nowTime.compareTo(bookings.get(i).getMeeting().getDateAndEndTime()) == 1) {
                SQLiteDatabase dbe = dbHelper.getWritableDatabase();
                MyBookingSql.enterBookingOrMeetingToHistory(dbe, bookings.get(i).getId(), bookings.get(i).getMeeting().getId());
                if (bookings.get(i).getConfirmation() == 0 || bookings.get(i).getConfirmation() == 2) {
                    makeRefuseFromMyMeeting(bookings.get(i));
                }
                bookings.remove(i);

                continue;
            }

            SQLiteDatabase dbp = dbHelper.getReadableDatabase();
            int[] ids = MyBookingMeetingSql.getFoodPortions(dbp, booking.getMeeting().getId(), booking.getId());
            bookings.get(i).getMeeting().setFoodPortionsId(MyBookingFoodPortionsSql.getAllPortionsId(dbp, ids, booking.getMeeting().getId(), booking.getId()));
        }
        return bookings;

    }

    @Override
    public boolean makeAccept(Booking booking) {
        //say to book maker with firebase

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        MyBookingSql.makeAcceptInMyMeeting(db, booking);
        saveInFirebase.makeAccept(booking);
        return true;
    }

    @Override
    public boolean makeRefuseFromMyMeeting(Booking booking) {
        // add Number Of Partner After Delete Booking
        addNumberOfPartnerAfterDeleteBooking(booking.getMeeting().getId(), booking.getNumberOfPartner());
        deleteBooking(booking);
        saveInFirebase.makeRefuseFromMyMeeting(booking);
        return false;
    }

    @Override
    public boolean makeRefuseFromMyBooking(Booking booking) {
        //add partners to meeting in meeting user
        deleteBooking(booking);
        saveInFirebase.makeRefuseFromMyBooking(booking);
        return false;
    }


    public void deleteBooking(Booking booking) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        MyBookingSql.deleteRefuseBooking(db, booking.getId(), booking.getMeeting().getId());
        MyBookingMeetingSql.deleteRefuseBooking(db, booking.getId(), booking.getMeeting().getId());//delete from meeting table

    }

    public void addNumberOfPartnerAfterDeleteBooking(int idMeeting, int number) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        MeetingSql.addNumberOfPartnerAfterDeleteBooking(db, idMeeting, number);

    }

    public void addNumberOfPartnerAfterPlusBooking(int idMeeting, int number) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        MeetingSql.addNumberOfPartnerAfterPlusBooking(db, idMeeting, number);

    }

    @Override
    public List<Meeting> getAllMeetingToBooking() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // List<Meeting> meetings = MeetingSql.getAllMeeting(db);
        List<Meeting> meetings = MeetingSql.getAllMeeting(db);
        for (int i = 0; i < meetings.size(); i++) {
            int[] ids = MeetingSql.getFoodPortions(db, meetings.get(i).getId());
            meetings.get(i).setFoodPortionsId(FoodPortionsSql.getAllPortionsId(db, ids, meetings.get(i).getId()));
            int num = meetings.get(i).getNumberOfPartner() - MyBookingSql.getAllBookingNumberOfPartner(db, meetings.get(i));//minus partners
            Calendar cls = Calendar.getInstance();
            Long nowTime = new Long(0);
            nowTime = cls.getTimeInMillis();

            if (meetings.get(i).getDateAndEndTime().compareTo(nowTime) == -1) {
                saveInFirebase.deleteMeetingToBooking(meetings.get(i));
            }
            if ((num < 0) || (num == 0) || (meetings.get(i).getDateAndEndTime().compareTo(nowTime) == -1)) {
                //saveInFirebase.deleteMeetingToBooking(meetings.get(i));
                meetings.remove(meetings.get(i));
            } else {
               // meetings.get(i).setNumberOfPartner(num);
                saveInFirebase.uploadMeetingToBooking(meetings.get(i));
            }
        }
        return meetings;
    }

    @Override
    public List<History> getAllHistoryBookingAndMeeting() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<History> histories = new LinkedList<History>();
        List<Booking> bookings = MyBookingSql.getAllHistory(db);
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            SQLiteDatabase dbc = dbHelper.getReadableDatabase();
            SQLiteDatabase dbp = dbHelper.getReadableDatabase();

            if (booking.getMeetingOrBooking() == 1) {
                bookings.get(i).setMeeting(MyBookingMeetingSql.getMeetingWithUser(dbc, booking.getMeeting().getId(), booking.getId()));
                int[] ids = MyBookingMeetingSql.getFoodPortions(dbp, booking.getMeeting().getId(), booking.getId());
                bookings.get(i).getMeeting().setFoodPortionsId(MyBookingFoodPortionsSql.getAllPortionsId(dbp, ids, booking.getMeeting().getId(), booking.getId()));
            } else {
                bookings.get(i).setMeeting(MyBookingMeetingSql.getMeetingWithUser(dbc, booking.getMeeting().getId(), booking.getId()));
                int[] ids = MyBookingMeetingSql.getFoodPortions(dbp, booking.getMeeting().getId(), booking.getId());
                bookings.get(i).getMeeting().setFoodPortionsId(MyBookingFoodPortionsSql.getAllPortionsId(dbp, ids, booking.getMeeting().getId(), booking.getId()));
            }

            histories.add(new History(bookings.get(i).getId(), bookings.get(i), getFeedBackAfterMeeting(bookings.get(i).getUserIdOfBooking(), bookings.get(i).getId(), bookings.get(i).getMeeting().getId(), bookings.get(i).getMeeting().getDateAndTime())));
        }
        return histories;
    }

    @Override
    public void giveFeedBack(Feedback feedback) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        FeedBackSql.addFeedBackFromUser(db, feedback);
    }

    @Override
    public void setUpdateToMeetingWithNumberOfPartner(List<Booking> bookings) {


    }


    public Feedback getFeedBackAfterMeeting(String idUserGaveFeedback, String idBooking, int IdMeeting, Long startTime) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return FeedBackSql.getFeedback(db, idUserGaveFeedback, idBooking, IdMeeting, startTime);
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
            IdMeetingMakerSql.create(db);
            FeedBackSql.create(db);
            MyBookingSql.create(db);
            MyBookingFoodPortionsSql.create(db);
            MyBookingMeetingSql.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            LoginSql.drop(db);
            GpsSql.drop(db);
            MeetingSql.drop(db);
            FoodPortionsSql.drop(db);
            UserDetailsSql.drop(db);
            IdMeetingMakerSql.drop(db);
            FeedBackSql.drop(db);
            MyBookingSql.drop(db);
            MyBookingMeetingSql.drop(db);
            MyBookingFoodPortionsSql.drop(db);
            onCreate(db);
        }
    }
}
