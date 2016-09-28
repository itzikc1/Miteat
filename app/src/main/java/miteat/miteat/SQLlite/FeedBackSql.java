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
        private static final String CLEANING_STAR = "cleaningStar";
        private static final String SERVICE_STAR = "serviceStar";
        private static final String ATMOSPHERE_STAR = "atmosphereStar";
        private static final String VALUE_STAR = "valueStar";
        private static final String BIRTHDAY = "birthday";

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
            values.put(LOVE_TAKEAWAY, userDetails.getLoveTakeAway());
            values.put(FAVORITE_DISH, userDetails.getFavoriteDish());
            values.put(MARITAL_STATUS, userDetails.getMaritalStatus());
            values.put(CLEANING_STAR, userDetails.getCleaningStar());
            values.put(SERVICE_STAR, userDetails.getServiceStar());
            values.put(ATMOSPHERE_STAR, userDetails.getAtmosphereStar());
            values.put(VALUE_STAR, userDetails.getValueStar());
            values.put(BIRTHDAY, userDetails.getBirthday());
            db.insert(FEED_BACK_TABLE, USER_NAME, values);

        }


        public static UserDetails getUserDetails(SQLiteDatabase db) {

            Cursor cursor = db.query(FEED_BACK_TABLE, null, null, null, null, null, null);
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

                int cleaningStarIndex = cursor.getColumnIndex(CLEANING_STAR);
                int serviceStarIndex = cursor.getColumnIndex(SERVICE_STAR);
                int atmosphereStarIndex = cursor.getColumnIndex(ATMOSPHERE_STAR);
                int valueStarIndex = cursor.getColumnIndex(VALUE_STAR);
                int birthdayIndex = cursor.getColumnIndex(BIRTHDAY);


                String userName = cursor.getString(userNameIndex);
                String picProfile = cursor.getString(picProfileIndex);
                double earnMoney = Double.parseDouble(cursor.getString(earnMoneyIndex));
                float numberOfStarAvg = Float.parseFloat(cursor.getString(numberOfStarAvgIndex));
                float cleaningStar = Float.parseFloat(cursor.getString(cleaningStarIndex));
                float serviceStar = Float.parseFloat(cursor.getString(serviceStarIndex));
                float atmosphereStar = Float.parseFloat(cursor.getString(atmosphereStarIndex));
                float valueStar = Float.parseFloat(cursor.getString(valueStarIndex));
                Long birthday = Long.parseLong(cursor.getString(birthdayIndex));
                String phoneNumber = cursor.getString(phoneNumberIndex);
                String address = cursor.getString(addressIndex);
                double avgPayForMeal = Double.parseDouble(cursor.getString(avgPayForMealIndex));
                String mail = cursor.getString(mailIndex);
                //  List<Feedback> feedbacks = cursor.getString(feedbacksIndex);
                Boolean loveTakeAway = Boolean.parseBoolean(cursor.getString(loveTakeAwayIndex));
                String favoriteDish = cursor.getString(favoriteDishIndex);
                int maritalStatus = Integer.parseInt(cursor.getString(maritalStatusIndex));
                userDetails =new UserDetails(userName,mail,phoneNumber);
                userDetails.setAllParm(numberOfStarAvg,cleaningStar,serviceStar,atmosphereStar,valueStar,earnMoney,picProfile,address,avgPayForMeal,maritalStatus,loveTakeAway,favoriteDish,birthday);
            }
            return userDetails;
        }


        public static void deleteUserDetails(SQLiteDatabase db, String userName) {
            db.delete(FEED_BACK_TABLE, USER_NAME + " = '" + userName + "'", null);
        }


        public static void create(SQLiteDatabase db) {
            db.execSQL("create table " +
                    FEED_BACK_TABLE + " (" +
                    USER_NAME + " TEXT," +
                    PIC_PROFILE + " TEXT," +
                    EARN_MONEY + " DOUBLE," +
                    NUMBER_OF_STAR_AVG + " FLOAT," +
                    CLEANING_STAR + " FLOAT," +
                    SERVICE_STAR + " FLOAT," +
                    ATMOSPHERE_STAR + " FLOAT," +
                    VALUE_STAR + " FLOAT," +
                    BIRTHDAY + " TEXT," +
                    PHONE_NUMBER + " TEXT," +
                    ADDRESS + " TEXT," +
                    AVG_PAY_FOR_MEAL + " DOUBLE," +
                    MAIL + " TEXT," +
                    FEEDBACKS + " TEXT," +
                    LOVE_TAKEAWAY + " BOOLEAN," +
                    FAVORITE_DISH + " TEXT," +
                    MARITAL_STATUS + " INTEGER);");
        }

        public static void drop(SQLiteDatabase db) {
            db.execSQL("drop table " + FEED_BACK_TABLE);
        }

    }



