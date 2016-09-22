package miteat.miteat.Model.Entities;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Itzik on 14/07/2016.
 */
public class Meeting {


    private String userId;
    private int id;
    private String personCreate;
    private int numberOfPartner;
    private String typeOfFood;
    private int money;
    private Long dateAndTime;
    private String location;
    private Boolean insurance;
    private int foodPortionsIds;
    private String image;
    private Double latLocation;
    private Double lonLocation;
    private Double distance;
    private int takeAway;
    private Long dateAndEndTime;
    List<FoodPortions> foodPortionsId = new LinkedList<FoodPortions>();

    public Meeting(int id, int numberOfPartner, String typeOfFood, int money, Long dateAndTime, Long dateAndEndTime, String location, Double latLocation, Double lonLocation, Boolean insurance, int takeAway) {
        this.id = id;
        this.numberOfPartner = numberOfPartner;
        this.typeOfFood = typeOfFood;
        this.money = money;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.insurance = insurance;
        this.latLocation = latLocation;
        this.lonLocation = lonLocation;
        this.takeAway = takeAway;
        this.dateAndEndTime = dateAndEndTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonCreate() {
        return personCreate;
    }

    public void setPersonCreate(String personCreate) {
        this.personCreate = personCreate;
    }

    public int getNumberOfPartner() {
        return numberOfPartner;
    }

    public void setNumberOfPartner(int numberOfPartner) {
        this.numberOfPartner = numberOfPartner;
    }

    public String getTypeOfFood() {
        return typeOfFood;
    }

    public void setTypeOfFood(String typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Long getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Long dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getInsurance() {
        return insurance;
    }

    public void setInsurance(Boolean insurance) {
        this.insurance = insurance;
    }

    public int getFoodPortionsIds() {
        return foodPortionsIds;
    }

    public void setFoodPortionsIds(int foodPortionsIds) {
        this.foodPortionsIds = foodPortionsIds;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<FoodPortions> getFoodPortionsId() {
        return foodPortionsId;
    }

    public void setFoodPortionsId(List<FoodPortions> foodPortionsId) {
        this.foodPortionsId = foodPortionsId;
    }

    public void addFoodPortionsId(FoodPortions foodPortionsId) {
        this.foodPortionsId.add(foodPortionsId);
    }

    public Double getLatLocation() {
        return latLocation;
    }

    public void setLatLocation(Double latLocation) {
        this.latLocation = latLocation;
    }

    public Double getLonLocation() {
        return lonLocation;
    }

    public void setLonLocation(Double lonLocation) {
        this.lonLocation = lonLocation;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTakeAway() {
        return takeAway;
    }

    public void setTakeAway(int takeAway) {
        this.takeAway = takeAway;
    }

    public Long getDateAndEndTime() {
        return dateAndEndTime;
    }

    public void setDateAndEndTime(Long dateAndEndTime) {
        this.dateAndEndTime = dateAndEndTime;
    }

}
