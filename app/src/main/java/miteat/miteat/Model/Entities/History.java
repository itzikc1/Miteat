package miteat.miteat.Model.Entities;

import java.util.List;

/**
 * Created by Itzik on 10/10/2016.
 */
public class History {

    private String hostId;
    private Booking booking;
    private Feedback feedback;

    public History(String hostId, Booking booking, Feedback feedback) {
        this.hostId = hostId;
        this.booking = booking;
        this.feedback = feedback;
    }
    public History(){}



    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }


    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
