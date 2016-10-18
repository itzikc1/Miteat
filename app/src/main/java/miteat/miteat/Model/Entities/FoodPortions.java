package miteat.miteat.Model.Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Itzik on 14/07/2016.
 */
public class FoodPortions  {


    private int meetingId;
    private int id;
    private String name;
    private int dishNumber;
    private ArrayList<String> images;
    private int numberOfFoodPortions;
    private int cost;
    private String allergens;

    public FoodPortions(int id,String name,int dishNumber, ArrayList<String> images, int numberOfFoodPortions, int cost, String allergens) {

        this.id = id;
        this.name=name;
        this.dishNumber = dishNumber;
        this.images = images;
        this.numberOfFoodPortions = numberOfFoodPortions;
        this.cost = cost;
        this.allergens = allergens;
    }

    public FoodPortions(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public int getNumberOfFoodPortions() {
        return numberOfFoodPortions;
    }

    public void setNumberOfFoodPortions(int numberOfFoodPortions) {
        this.numberOfFoodPortions = numberOfFoodPortions;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getDishNumber() {
        return dishNumber;
    }

    public void setDishNumber(int dishNumber) {
        this.dishNumber = dishNumber;
    }
}
