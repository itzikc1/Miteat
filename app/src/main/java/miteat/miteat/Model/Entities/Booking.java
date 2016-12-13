package miteat.miteat.Model.Entities;

/**
 * Created by Itzik on 27/09/2016.
 */
public class Booking {


    private String id;//the user i want to eat with him
    private Meeting meeting;
    private int confirmation;//if 1- accept, if 0- waiting,if 2-refuse,if-3 booked cancel
    private int numberOfPartner;
    private String userIdOfBooking;//my user booking
    private int meetingOrBooking;//if 1- booking, if 0- meeting

    public Booking(String id, Meeting meeting, String userIdOfBooking, int meetingOrBooking) {
        this.meeting = meeting;
        this.id = id;
        this.userIdOfBooking = userIdOfBooking;
        this.meetingOrBooking = meetingOrBooking;
    }

    public Booking() {
    }

    public int getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(int confirmation) {
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

    public int getMeetingOrBooking() {
        return meetingOrBooking;
    }

    public void setMeetingOrBooking(int meetingOrBooking) {
        this.meetingOrBooking = meetingOrBooking;
    }


}
