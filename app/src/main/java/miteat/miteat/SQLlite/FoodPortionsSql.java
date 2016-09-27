package miteat.miteat.SQLlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import miteat.miteat.Model.Entities.FoodPortions;
import miteat.miteat.Model.ModelCloudinary;

/**
 * Created by Itzik on 24/07/2016.
 */
public class FoodPortionsSql {


    public static String strSeparator = "#";
    private static final String FOOD_PORTIONS_TABLE = "food_portions_table";
    private static final String ID = "id";
    private static final String MEETING_ID = "meetingId";
    private static final String NAME = "name";
    private static final String IMAGES = "images";
    private static final String NUMBER_OF_FOOD_PORTIONS = "numberOfFoodPortions";
    private static final String COST = "cost";
    private static final String ALLERGENS = "allergens";


    public static void addFoodPortions(SQLiteDatabase db, FoodPortions foodPortions) {
        ContentValues values = new ContentValues();
        values.put(ID, foodPortions.getId());
        values.put(MEETING_ID, foodPortions.getMeetingId());
        values.put(NAME, foodPortions.getName());
        values.put(IMAGES, convertArrayToString(foodPortions.getImages()));
        values.put(NUMBER_OF_FOOD_PORTIONS, foodPortions.getNumberOfFoodPortions());
        values.put(COST, foodPortions.getCost());
        values.put(ALLERGENS, foodPortions.getAllergens());
        db.insert(FOOD_PORTIONS_TABLE, ID, values);
    }

    public static List<FoodPortions> getAllPortionsId(SQLiteDatabase db, int[] ids, int meetingId) {

        List<FoodPortions> foodPortionsId = new LinkedList<FoodPortions>();
        // for (int i = 0; i < ids.length; i++) {

        //String[] params = {String.valueOf(ids[i]),String.valueOf(meetingId)};
        String[] params = new String[1];
        params[0] = String.valueOf(meetingId);
        Cursor cursor = db.query(FOOD_PORTIONS_TABLE, null, MEETING_ID + " = ?", params, null, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            return foodPortionsId;
            // continue;
        }
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ID);
            int nameIndex = cursor.getColumnIndex(NAME);
            int imagesIndex = cursor.getColumnIndex(IMAGES);
            int numberOfFoodPortionsIndex = cursor.getColumnIndex(NUMBER_OF_FOOD_PORTIONS);
            int costIndex = cursor.getColumnIndex(COST);
            int allergensIndex = cursor.getColumnIndex(ALLERGENS);
            do {
                int id = Integer.parseInt(cursor.getString(idIndex));
                String name = cursor.getString(nameIndex);
                ArrayList<String> images = convertStringToArray(cursor.getString(imagesIndex));
                int numberOfFoodPortions = Integer.parseInt(cursor.getString(numberOfFoodPortionsIndex));
                int cost = Integer.parseInt(cursor.getString(costIndex));
                String allergens = cursor.getString(allergensIndex);
                FoodPortions foodPortions = new FoodPortions(id, name, images, numberOfFoodPortions, cost, allergens);
                foodPortions.setMeetingId(meetingId);
                foodPortionsId.add(foodPortions);
            } while (cursor.moveToNext());
        }
        return foodPortionsId;
    }

    public static void deleteFoodPortions(SQLiteDatabase db, int id) {

        deleteImages(db,id);
        db.delete(FOOD_PORTIONS_TABLE, MEETING_ID + " = '" + id + "'", null);

    }


    public static void create(SQLiteDatabase db) {
        db.execSQL("create table " +
                FOOD_PORTIONS_TABLE + " (" +
                ID + " INTEGER," +
                MEETING_ID + " INTEGER," +
                NAME + " TEXT," +
                IMAGES + " TEXT," +
                NUMBER_OF_FOOD_PORTIONS + " INTEGER," +
                COST + " INTEGER," +
                ALLERGENS + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + FOOD_PORTIONS_TABLE);
    }


    public static String convertArrayToString(ArrayList<String> images) {
        if (images.size() > 0) {
            String str = "";
            for (int i = 0; i < images.size(); i++) {
                str = str + images.get(i);
                // Do not append comma at the end of last element
                if (i < images.size() - 1) {
                    str = str + strSeparator;
                }
            }
            return str;
        } else {
            return null;
        }
    }

    public static void deleteImages(SQLiteDatabase db, int id){
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        Cursor cursor = db.query(FOOD_PORTIONS_TABLE, null, MEETING_ID + " = ?", params, null, null, null, null);
        if (cursor.moveToFirst()) {
            int imagesIndex = cursor.getColumnIndex(IMAGES);
            do {
                ModelCloudinary.getInstance().deleteImageFromCloudinary(convertStringToArray(cursor.getString(imagesIndex)));
            } while (cursor.moveToNext());
        }

    }

    public static ArrayList<String> convertStringToArray(String str) {
        ArrayList<String> images = new ArrayList<String>();
        if (str != null) {
            String[] arr = str.split(strSeparator);

            for (int i = 0; i < arr.length; i++) {
                images.add(arr[i]);
            }
            return images;
        } else {
            return images;
        }
    }
}

