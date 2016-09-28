package miteat.miteat.Model.Entities;

import android.util.Log;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Itzik on 11/09/2016.
 */
public class UserDetails {

    private float numberOfStarAvg;
    private float cleaningStar;
    private float serviceStar;
    private float atmosphereStar;
    private float valueStar;
    private double earnMoney;
    private String picProfile;
    private String userName;
    private String phoneNumber;
    private String address;
    private double avgPayForMeal;
    private String mail;
    private int maritalStatus;
    List<Feedback> feedbacks = new LinkedList<Feedback>();
    private Boolean loveTakeAway;
    private String favoriteDish;
    private Long birthday;


    public UserDetails(String userName, String mail, String phoneNumber) {

        this.userName = userName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.numberOfStarAvg = 0;
        this.loveTakeAway = true;
        Calendar cal = Calendar.getInstance();
        cal.set(1989, 8, 7);
        this.birthday = cal.getTimeInMillis();

    }

    public void setAllParm(float numberOfStarAvg, float cleaningStar, float serviceStar, float atmosphereStar, float valueStar, double earnMoney, String picProfile, String address, double avgPayForMeal, int maritalStatus, Boolean loveTakeAway, String favoriteDish, Long birthday) {

        this.numberOfStarAvg = numberOfStarAvg;
        this.cleaningStar = cleaningStar;
        this.serviceStar = serviceStar;
        this.valueStar = valueStar;
        this.atmosphereStar = atmosphereStar;
        this.earnMoney = earnMoney;
        this.picProfile = picProfile;
        this.address = address;
        this.avgPayForMeal = avgPayForMeal;
        this.maritalStatus = maritalStatus;
        this.loveTakeAway = loveTakeAway;
        this.favoriteDish = favoriteDish;
        this.birthday = birthday;


    }

    public float getNumberOfStarAvg() {
        return numberOfStarAvg;
    }

    public void setNumberOfStarAvg(float numberOfStarAvg) {
        this.numberOfStarAvg = numberOfStarAvg;
    }

    public int getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(int maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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


    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Boolean getLoveTakeAway() {
        return loveTakeAway;
    }

    public void setLoveTakeAway(Boolean loveTakeAway) {
        this.loveTakeAway = loveTakeAway;
    }

    public String getFavoriteDish() {
        return favoriteDish;
    }

    public void setFavoriteDish(String favoriteDish) {
        this.favoriteDish = favoriteDish;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public float getCleaningStar() {
        return cleaningStar;
    }

    public void setCleaningStar(float cleaningStar) {
        this.cleaningStar = cleaningStar;
    }

    public float getServiceStar() {
        return serviceStar;
    }

    public void setServiceStar(float serviceStar) {
        this.serviceStar = serviceStar;
    }

    public float getAtmosphereStar() {
        return atmosphereStar;
    }

    public void setAtmosphereStar(float atmosphereStar) {
        this.atmosphereStar = atmosphereStar;
    }

    public float getValueStar() {
        return valueStar;
    }

    public void setValueStar(float valueStar) {
        this.valueStar = valueStar;
    }
}
