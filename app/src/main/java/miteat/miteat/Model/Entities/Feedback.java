package miteat.miteat.Model.Entities;

import android.util.Log;

/**
 * Created by Itzik on 11/09/2016.
 */
public class Feedback {

    private int id;
    private int idMeeting;
    private String idBooking;
    private String fromUserId;
    private String toUserId;
    private float avgStar;
    private String feedBackText;
    private String replyText;
    private float cleaningStar;
    private float serviceStar;
    private float atmosphereStar;
    private float valueStar;
    private boolean takeAway;
    private Long dateAndTime;


    public Feedback(int id, int idMeeting, String idBooking, String fromUserId, String toUserId, String feedBackText, String replyText, float cleaningStar, float serviceStar, float atmosphereStar, float valueStar, boolean takeAway, Long dateAndTime) {

        this.id = id;
        this.idMeeting = idMeeting;
        this.idBooking = idBooking;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.feedBackText = feedBackText;
        this.replyText = replyText;
        this.cleaningStar = cleaningStar;
        this.serviceStar = serviceStar;
        this.atmosphereStar = atmosphereStar;
        this.valueStar = valueStar;
        this.takeAway = takeAway;
        this.dateAndTime= dateAndTime;


    }

    public Feedback(){}
    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public void setCleaningStar(int cleaningStar) {
        this.cleaningStar = cleaningStar;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getFeedBackText() {
        return feedBackText;
    }

    public void setFeedBackText(String feedBackText) {
        this.feedBackText = feedBackText;
    }

    public float getValueStar() {
        return valueStar;
    }

    public void setValueStar(float valueStar) {
        this.valueStar = valueStar;
    }

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTakeAwy() {
        return takeAway;
    }

    public void setTakeAway(boolean takeAwy) {
        this.takeAway = takeAwy;
    }

    public String getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(String idBooking) {
        this.idBooking = idBooking;
    }

    public float getAtmosphereStar() {
        return atmosphereStar;
    }

    public void setAtmosphereStar(float atmosphereStar) {
        this.atmosphereStar = atmosphereStar;
    }

    public float getServiceStar() {
        return serviceStar;
    }

    public void setServiceStar(float serviceStar) {
        this.serviceStar = serviceStar;
    }

    public float getCleaningStar() {
        return cleaningStar;
    }

    public void setCleaningStar(float cleaningStar) {
        this.cleaningStar = cleaningStar;
    }

    public float getAvgStar() {
        return avgStar;
    }

    public void setAvgStar(float avgStar) {
        this.avgStar = avgStar;
    }

    public Long getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Long dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
