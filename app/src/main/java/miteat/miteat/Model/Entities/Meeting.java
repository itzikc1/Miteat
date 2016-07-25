package miteat.miteat.Model.Entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Itzik on 14/07/2016.
 */
public class Meeting {

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


     List<FoodPortions> foodPortionsId = new LinkedList<FoodPortions>();

    public Meeting(int id, int numberOfPartner, String typeOfFood, int money, Long dateAndTime, String location, Boolean insurance) {
        this.id=id;
        this.numberOfPartner=numberOfPartner;
        this.typeOfFood=typeOfFood;
        this.money=money;
        this.dateAndTime=dateAndTime;
        this.location=location;
        this.insurance=insurance;

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





}
