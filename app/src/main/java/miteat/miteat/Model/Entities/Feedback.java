package miteat.miteat.Model.Entities;

/**
 * Created by Itzik on 11/09/2016.
 */
public class Feedback {

    private int id;
    private int idMeeting;
    private int avgStar;
    private String feedBackText;
    private String replyText;
    private int cleaningStar;
    private int serviceStar;
    private int atmosphereStar;
    private int valueStar;
    private boolean takeAwy;

    public int getAtmosphereStar() {
        return atmosphereStar;
    }

    public void setAtmosphereStar(int atmosphereStar) {
        this.atmosphereStar = atmosphereStar;
    }

    public int getServiceStar() {
        return serviceStar;
    }

    public void setServiceStar(int serviceStar) {
        this.serviceStar = serviceStar;
    }

    public int getCleaningStar() {
        return cleaningStar;
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

    public int getAvgStar() {
        return avgStar;
    }

    public void setAvgStar(int avgStar) {
        this.avgStar = avgStar;
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
        return takeAwy;
    }

    public void setTakeAwy(boolean takeAwy) {
        this.takeAwy = takeAwy;
    }

    public int getValueStar() {
        return valueStar;
    }

    public void setValueStar(int valueStar) {
        this.valueStar = valueStar;
    }


}
