package miteat.miteat.SQLlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import miteat.miteat.Model.Entities.Feedback;
import miteat.miteat.Model.Entities.UserDetails;

/**
 * Created by Itzik on 22/09/2016.
 */
public class UserDetailsSql {

    public static String strSeparator = "__,__";
    private static final String USER_DETAILS_TABLE = "user_details_table";
    private static final String USER_NAME = "user_name";
    private static final String PIC_PROFILE = "picProfile";
    private static final String EARN_MONEY = "earnMoney";
    private static final String NUMBER_OF_STAR_AVG = "numberOfStarAvg";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String ADDRESS = "address";
    private static final String AVG_PAY_FOR_MEAL = "avgPayForMeal";
    private static final String MAIL = "mail";
    private static final String FEEDBACKS = "feedbacks";
    private static final String LOVE_TAKEAWAY = "loveTakeAway";
    private static final String FAVORITE_DISH = "favoriteDish";
    private static final String MARITAL_STATUS = "maritalStatus";

    public static void addUserDetails(SQLiteDatabase db, UserDetails userDetails) {


        ContentValues values = new ContentValues();
        values.put(USER_NAME, userDetails.getUserName());
        values.put(PIC_PROFILE, userDetails.getPicProfile());
        values.put(EARN_MONEY, userDetails.getEarnMoney());
        values.put(NUMBER_OF_STAR_AVG, userDetails.getNumberOfStarAvg());
        values.put(PHONE_NUMBER, userDetails.getPhoneNumber());
        values.put(ADDRESS, userDetails.getAddress());
        values.put(AVG_PAY_FOR_MEAL, userDetails.getAvgPayForMeal());
        values.put(MAIL, userDetails.getMail());
        values.put(FEEDBACKS, convertArrayToString(userDetails.getFeedbacks()));
        values.put(LOVE_TAKEAWAY, userDetails.getLoveTakeAway());
        values.put(FAVORITE_DISH, userDetails.getFavoriteDish());
        values.put(MARITAL_STATUS, userDetails.getMaritalStatus());
        db.insert(USER_DETAILS_TABLE, USER_NAME, values);

    }


    public static UserDetails getUserDetails(SQLiteDatabase db) {

        Cursor cursor = db.query(USER_DETAILS_TABLE, null, null, null, null, null, null);
        UserDetails userDetails = null;
        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {

            return userDetails;
        }




        if (cursor.moveToFirst()) {

            int userNameIndex = cursor.getColumnIndex(USER_NAME);
            int picProfileIndex = cursor.getColumnIndex(PIC_PROFILE);
            int earnMoneyIndex = cursor.getColumnIndex(EARN_MONEY);
            int numberOfStarAvgIndex = cursor.getColumnIndex(NUMBER_OF_STAR_AVG);
            int phoneNumberIndex = cursor.getColumnIndex(PHONE_NUMBER);
            int addressIndex = cursor.getColumnIndex(ADDRESS);
            int avgPayForMealIndex = cursor.getColumnIndex(AVG_PAY_FOR_MEAL);
            int mailIndex = cursor.getColumnIndex(MAIL);
            int feedbacksIndex = cursor.getColumnIndex(FEEDBACKS);
            int loveTakeAwayIndex = cursor.getColumnIndex(LOVE_TAKEAWAY);
            int favoriteDishIndex  = cursor.getColumnIndex(FAVORITE_DISH);
            int maritalStatusIndex = cursor.getColumnIndex(MARITAL_STATUS);

            String userName = cursor.getString(userNameIndex);
            String picProfile = cursor.getString(picProfileIndex);
            double earnMoney = Double.parseDouble(cursor.getString(earnMoneyIndex));
            float numberOfStarAvg = Float.parseFloat(cursor.getString(numberOfStarAvgIndex));
            double phoneNumber = Double.parseDouble(cursor.getString(phoneNumberIndex));
            String address = cursor.getString(addressIndex);
            double avgPayForMeal = Double.parseDouble(cursor.getString(avgPayForMealIndex));
            String mail = cursor.getString(mailIndex);
          //  List<Feedback> feedbacks = cursor.getString(feedbacksIndex);
            Boolean loveTakeAway = Boolean.parseBoolean(cursor.getString(loveTakeAwayIndex));
            String favoriteDish = cursor.getString(favoriteDishIndex);
            String maritalStatus = cursor.getString(maritalStatusIndex);
            userDetails =new UserDetails(userName,mail,phoneNumber);
        }
        return userDetails;
    }


    public static void deleteUserDetails(SQLiteDatabase db, String userName) {
        db.delete(USER_DETAILS_TABLE, USER_NAME + " = '" + userName + "'", null);
    }


    public static void create(SQLiteDatabase db) {
        db.execSQL("create table " +
                USER_DETAILS_TABLE + " (" +
                USER_NAME + " TEXT," +
                PIC_PROFILE + " TEXT," +
                EARN_MONEY + " DOUBLE," +
                NUMBER_OF_STAR_AVG + " FLOAT," +
                PHONE_NUMBER + " DOUBLE," +
                ADDRESS + " TEXT," +
                AVG_PAY_FOR_MEAL + " DOUBLE," +
                MAIL + " TEXT," +
                FEEDBACKS + " TEXT," +
                LOVE_TAKEAWAY + " BOOLEAN," +
                FAVORITE_DISH + " TEXT," +
                MARITAL_STATUS + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + USER_DETAILS_TABLE);
    }


    public static String convertArrayToString(List<Feedback> feedbacks) {
        String str = "";
        for (int i = 0; i < feedbacks.size(); i++) {
            str = str + feedbacks.get(i).getId();
            // Do not append comma at the end of last element
            if (i < feedbacks.size() - 1) {
                str = str + strSeparator;
            }
        }
        return str;
    }

    public static int[] convertStringToArray(String str) {
        String[] arr = str.split(strSeparator);
        int[] ids;
        ids = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ids[i] = Integer.valueOf(arr[i]);
        }
        return ids;
    }
}
