package miteat.miteat.SQLlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import miteat.miteat.Model.Entities.FoodPortions;
import miteat.miteat.Model.Entities.Meeting;

/**
 * Created by Itzik on 24/07/2016.
 */
public class MyBookingMeetingSql {


    public static String strSeparator = "__,__";
    private static final String MY_BOOKING_MEETING_TABLE = "my_booking_meeting_table";
    private static final String USER_ID = "userId";
    private static final String ID = "id";
    private static final String NUMBER_OF_PARTNER = "numberOfPartner";
    private static final String TYPE_OF_FOOD = "typeOfFood";
    private static final String MONEY = "money";
    private static final String DATE_AND_TIME = "dateAndTime";
    private static final String DATE_AND_END_TIME = "dateAndEndTime";
    private static final String LOCATION = "location";
    private static final String INSURANCE = "insurance";
    private static final String IMAGE = "image";
    private static final String LAT_LOCATION = "latLocation";
    private static final String LON_LOCATION = "lonLocation";
    private static final String DISTANCE = "distance";
    private static final String TAKE_AWAY = "takeAway";
    private static final String FOOD_PORTIONS_IDS = "foodPortionsIds";


    public static void addMeeting(SQLiteDatabase db, Meeting meeting) {

        ContentValues values = new ContentValues();
        values.put(ID, meeting.getId());
        values.put(USER_ID, meeting.getUserId());
        values.put(DATE_AND_END_TIME, meeting.getDateAndEndTime());
        values.put(LAT_LOCATION, meeting.getLatLocation());
        values.put(LON_LOCATION, meeting.getLonLocation());
        values.put(DISTANCE, meeting.getDistance());
        values.put(TAKE_AWAY, meeting.getTakeAway());
        values.put(NUMBER_OF_PARTNER, meeting.getNumberOfPartner());
        values.put(TYPE_OF_FOOD, meeting.getTypeOfFood());
        values.put(MONEY, meeting.getMoney());
        values.put(DATE_AND_TIME, meeting.getDateAndTime());
        values.put(LOCATION, meeting.getLocation());
        values.put(INSURANCE, meeting.getInsurance());
        values.put(IMAGE, meeting.getImage());
        values.put(FOOD_PORTIONS_IDS, convertArrayToString(meeting.getFoodPortionsId()));
        db.insert(MY_BOOKING_MEETING_TABLE, ID, values);

    }

    public static Meeting getMeetingWithUser(SQLiteDatabase db, int idMeeting,String user) {


        String [] selectionArgs ={String.valueOf(idMeeting),user};
        Cursor cursor = db.query(MY_BOOKING_MEETING_TABLE, null, ID + " = ?" + "and " + USER_ID + " = ?", selectionArgs, null, null, null);
        Meeting meeting = null;

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            return meeting;
        }


        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ID);
            int userIdIndex = cursor.getColumnIndex(USER_ID);
            int numberOfPartnerIndex = cursor.getColumnIndex(NUMBER_OF_PARTNER);
            int typeOfFoodIndex = cursor.getColumnIndex(TYPE_OF_FOOD);
            int moneyIndex = cursor.getColumnIndex(MONEY);
            int dateAndTimeIndex = cursor.getColumnIndex(DATE_AND_TIME);
            int dateAndEndTimeIndex = cursor.getColumnIndex(DATE_AND_END_TIME);
            int locationIndex = cursor.getColumnIndex(LOCATION);
            int latLocationIndex = cursor.getColumnIndex(LAT_LOCATION);
            int lonLocationIndex = cursor.getColumnIndex(LON_LOCATION);
            int insuranceIndex = cursor.getColumnIndex(INSURANCE);
            int takeAwayIndex = cursor.getColumnIndex(TAKE_AWAY);
            int foodPortionsIdIndex = cursor.getColumnIndex(FOOD_PORTIONS_IDS);
            int imageIndex = cursor.getColumnIndex(IMAGE);
            int distanceIndex = cursor.getColumnIndex(DISTANCE);

            int id = Integer.parseInt(cursor.getString(idIndex));
            String userId = cursor.getString(userIdIndex);
            int numberOfPartner = Integer.parseInt(cursor.getString(numberOfPartnerIndex));
            String typeOfFood = cursor.getString(typeOfFoodIndex);
            int money = Integer.parseInt(cursor.getString(moneyIndex));
            Long dateAndTime = Long.parseLong(cursor.getString(dateAndTimeIndex));
            Long dateAndEndTime = Long.parseLong(cursor.getString(dateAndEndTimeIndex));
            String location = cursor.getString(locationIndex);
            Double latLocation = Double.parseDouble(cursor.getString(latLocationIndex));
            Double lonLocation = Double.parseDouble(cursor.getString(lonLocationIndex));
            Boolean insurance = Boolean.parseBoolean(cursor.getString(insuranceIndex));
            int takeAway = Integer.parseInt(cursor.getString(takeAwayIndex));
            // int[] foodPortionsId = convertStringToArray(cursor.getString(foodPortionsIdIndex));//cheak if null
            String image = cursor.getString(imageIndex);


             meeting = new Meeting(id, userId, numberOfPartner, typeOfFood, money, dateAndTime, dateAndEndTime, location, latLocation, lonLocation, insurance, takeAway);
            meeting.setImage(image);
            if (cursor.getString(distanceIndex) != null) {
                Double distance = Double.parseDouble(cursor.getString(distanceIndex));
                meeting.setDistance(distance);
            }

        }
        return meeting;
    }
    public static List<Meeting> getAllMeetingWithUser(SQLiteDatabase db,String user) {


        List<Meeting> meetings = new LinkedList<Meeting>();
        Calendar cls = Calendar.getInstance();

        Cursor cursor = db.query(MY_BOOKING_MEETING_TABLE, null, null, null, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            return meetings;
        }


        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ID);
            int userIdIndex = cursor.getColumnIndex(USER_ID);
            int numberOfPartnerIndex = cursor.getColumnIndex(NUMBER_OF_PARTNER);
            int typeOfFoodIndex = cursor.getColumnIndex(TYPE_OF_FOOD);
            int moneyIndex = cursor.getColumnIndex(MONEY);
            int dateAndTimeIndex = cursor.getColumnIndex(DATE_AND_TIME);
            int dateAndEndTimeIndex = cursor.getColumnIndex(DATE_AND_END_TIME);
            int locationIndex = cursor.getColumnIndex(LOCATION);
            int latLocationIndex = cursor.getColumnIndex(LAT_LOCATION);
            int lonLocationIndex = cursor.getColumnIndex(LON_LOCATION);
            int insuranceIndex = cursor.getColumnIndex(INSURANCE);
            int takeAwayIndex = cursor.getColumnIndex(TAKE_AWAY);
            int foodPortionsIdIndex = cursor.getColumnIndex(FOOD_PORTIONS_IDS);
            int imageIndex = cursor.getColumnIndex(IMAGE);
            int distanceIndex = cursor.getColumnIndex(DISTANCE);
            do {
            int id = Integer.parseInt(cursor.getString(idIndex));
            String userId = cursor.getString(userIdIndex);
            int numberOfPartner = Integer.parseInt(cursor.getString(numberOfPartnerIndex));
            String typeOfFood = cursor.getString(typeOfFoodIndex);
            int money = Integer.parseInt(cursor.getString(moneyIndex));
            Long dateAndTime = Long.parseLong(cursor.getString(dateAndTimeIndex));
            Long dateAndEndTime = Long.parseLong(cursor.getString(dateAndEndTimeIndex));
            String location = cursor.getString(locationIndex);
            Double latLocation = Double.parseDouble(cursor.getString(latLocationIndex));
            Double lonLocation = Double.parseDouble(cursor.getString(lonLocationIndex));
            Boolean insurance = Boolean.parseBoolean(cursor.getString(insuranceIndex));
            int takeAway = Integer.parseInt(cursor.getString(takeAwayIndex));
            // int[] foodPortionsId = convertStringToArray(cursor.getString(foodPortionsIdIndex));//cheak if null
            String image = cursor.getString(imageIndex);



            Meeting meeting = new Meeting(id,userId, numberOfPartner, typeOfFood, money, dateAndTime, dateAndEndTime, location, latLocation, lonLocation, insurance, takeAway);
            meeting.setImage(image);
                if(cursor.getString(distanceIndex)!=null){
                    Double distance = Double.parseDouble(cursor.getString(distanceIndex));
                    meeting.setDistance(distance);
                }

            meetings.add(meeting);
            } while (cursor.moveToNext());
        }
        return meetings;

    }

    public static int[] getFoodPortions(SQLiteDatabase db, int id,String user) {

        String [] selectionArgs ={String.valueOf(id),user};
        Cursor cursor = db.query(MY_BOOKING_MEETING_TABLE, null, ID + " = ?" + "and " + USER_ID + " = ?", selectionArgs, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            return null;
        }


        if (cursor.moveToFirst()) {
            int foodPortionsIdIndex = cursor.getColumnIndex(FOOD_PORTIONS_IDS);
            int[] foodPortionsId = convertStringToArray(cursor.getString(foodPortionsIdIndex));//cheak if null
            return foodPortionsId;
        }
        return null;
    }



    public static void deleteRefuseBooking(SQLiteDatabase db, String idUser,int meetingId) {
        String [] selectionArgs ={String.valueOf(meetingId),idUser};
        String[] params = new String[1];
        params[0] = String.valueOf(0);
        db.delete(MY_BOOKING_MEETING_TABLE, ID + " = '" + meetingId + "'"+" and "+USER_ID+ " = '" +idUser+"'", null);
    }

    public static void deleteMeeting(SQLiteDatabase db, int id) {

        db.delete(MY_BOOKING_MEETING_TABLE, ID + " = '" + id + "'", null);

    }

    public static void create(SQLiteDatabase db) {
        db.execSQL("create table " +
                MY_BOOKING_MEETING_TABLE + " (" +
                ID + " INTEGER," +
                USER_ID + " TEXT," +
                NUMBER_OF_PARTNER + " INTEGER," +
                TYPE_OF_FOOD + " TEXT," +
                MONEY + " INTEGER," +
                DATE_AND_TIME + " TEXT," +
                DATE_AND_END_TIME + " TEXT," +
                LOCATION + " TEXT," +
                LAT_LOCATION + " DOUBLE," +
                LON_LOCATION + " DOUBLE," +
                INSURANCE + " BOOLEAN," +
                DISTANCE + " DOUBLE," +
                TAKE_AWAY + " INTEGER," +
                IMAGE + " TEXT," +
                FOOD_PORTIONS_IDS + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + MY_BOOKING_MEETING_TABLE);
    }

    public static String convertArrayToString(List<FoodPortions> foodPortionsId) {
        if(foodPortionsId.size()>0) {
            String str = "";
            for (int i = 0; i < foodPortionsId.size(); i++) {
                str = str + foodPortionsId.get(i).getId();
                // Do not append comma at the end of last element
                if (i < foodPortionsId.size() - 1) {
                    str = str + strSeparator;
                }
            }
            return str;
        }
        else{
            return null;
        }
    }

    public static int[] convertStringToArray(String str) {
        if (str != null) {

            String[] arr = str.split(strSeparator);
            int[] ids;
            ids = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                ids[i] = Integer.valueOf(arr[i]);
            }
            return ids;
        } else {
             return null;
        }

    }
}


