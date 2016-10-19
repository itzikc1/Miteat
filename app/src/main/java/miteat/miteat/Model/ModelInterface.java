package miteat.miteat.Model;

import java.util.List;

import miteat.miteat.Model.Entities.Booking;
import miteat.miteat.Model.Entities.Feedback;
import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.History;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.User;
import miteat.miteat.Model.Entities.UserDetails;

/**
 * Created by Itzik on 05/06/2016.
 */
public interface ModelInterface {

    public Boolean checkIfUserExist(User user);

    public void addGps(Gps gps);

    public int numberOfRow(String tableName);

    public void addMeeting(Meeting meeting);

    public List<Meeting> getAllMeeting();

    public Meeting getMeeting(int id);

    public void deleteMeeting(Meeting meeting);

    public Gps getGpsLocation();

    public UserDetails getUserDetails(String id);

    public void bookingToMeeting(Booking booking);//makeBooking

    public void setUserDetails(UserDetails userDetails);

    public UserDetails getUserDetails();

    public List<Booking> getMyBookingList();

    public Boolean checkIfBooking(Booking booking);

    public List<Booking> getOrderToBooking();

    public boolean makeAccept(Booking booking);

    public boolean makeRefuseFromMyMeeting(Booking booking);

    public boolean makeRefuseFromMyBooking(Booking booking);

    public List<Meeting> getAllMeetingToBooking();

    public List<History> getAllHistoryBookingAndMeeting();

    public void giveFeedBack(Feedback feedback);

    public void setUpdateToMeetingWithNumberOfPartner(List<Booking> bookings);

    public void updateMeetingToBookingWithTime();
}
