package miteat.miteat.Model.Entities;

/**
 * Created by Itzik on 27/09/2016.
 */
public class Booking {



    private int id;
    private Meeting meeting;
    private boolean confirmation;
    private int numberOfPartner;
    private String userId;

    public Booking(Meeting meeting,int id,String userId){
        this.meeting=meeting;
        this.id=id;
        this.userId=userId;
    }
    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
    public int getNumberOfPartner() {
        return numberOfPartner;
    }

    public void setNumberOfPartner(int numberOfPartner) {
        this.numberOfPartner = numberOfPartner;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
