package miteat.miteat.Model.Entities;

/**
 * Created by Itzik on 27/09/2016.
 */
public class OrderToBooking {


    private String id;
    private Meeting meeting;
    private boolean confirmation;
    private int numberOfPartner;
    private String userIdOfBooking;

    public OrderToBooking(String id, Meeting meeting, String userIdOfBooking) {
        this.meeting = meeting;
        this.id = id;
        this.userIdOfBooking = userIdOfBooking;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserIdOfBooking() {
        return userIdOfBooking;
    }

    public void setUserIdOfBooking(String userIdOfBooking) {
        this.userIdOfBooking = userIdOfBooking;
    }
}
