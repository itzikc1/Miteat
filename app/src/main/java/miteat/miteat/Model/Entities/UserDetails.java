package miteat.miteat.Model.Entities;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Itzik on 11/09/2016.
 */
public class UserDetails {

    private float numberOfStarAvg;
    private double earnMoney;
    private String picProfile;
    private String userName;
    private double phoneNumber;
    private String address;
    private double avgPayForMeal;
    private String mail;
    private String maritalStatus;
    List<Feedback> feedbacks = new LinkedList<Feedback>();
    private Boolean loveTakeAwy;
    private String favoriteDish;

    public UserDetails(String userName,String mail,double phoneNumber){

        this.userName=userName;
        this.mail=mail;
        this.phoneNumber=phoneNumber;
        this.numberOfStarAvg=0;

    }

    public float getNumberOfStarAvg() {
        return numberOfStarAvg;
    }

    public void setNumberOfStarAvg(float numberOfStarAvg) {
        this.numberOfStarAvg = numberOfStarAvg;
    }

    public double getEarnMoney() {
        return earnMoney;
    }

    public void setEarnMoney(double earnMoney) {
        this.earnMoney = earnMoney;
    }

    public String getPicProfile() {
        return picProfile;
    }

    public void setPicProfile(String picProfile) {
        this.picProfile = picProfile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(double phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAvgPayForMeal() {
        return avgPayForMeal;
    }

    public void setAvgPayForMeal(double avgPayForMeal) {
        this.avgPayForMeal = avgPayForMeal;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Boolean getLoveTakeAwy() {
        return loveTakeAwy;
    }

    public void setLoveTakeAwy(Boolean loveTakeAwy) {
        this.loveTakeAwy = loveTakeAwy;
    }

    public String getFavoriteDish() {
        return favoriteDish;
    }

    public void setFavoriteDish(String favoriteDish) {
        this.favoriteDish = favoriteDish;
    }




}
