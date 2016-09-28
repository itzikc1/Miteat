package miteat.miteat.Model.Entities;

/**
 * Created by Itzik on 27/09/2016.
 */
public class Booking {



    private int id;
    private Meeting meeting;
    private boolean confirmation;
    private int numberOfPartner;
    private String userIdOfBooking;

    public Booking(Meeting meeting,int id,String userIdOfBooking){
        this.meeting=meeting;
        this.id=id;
        this.userIdOfBooking=userIdOfBooking;
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

    public String getUserIdOfBooking() {
        return userIdOfBooking;
    }

    public void setUserIdOfBooking(String userIdOfBooking) {
        this.userIdOfBooking = userIdOfBooking;
    }
}
