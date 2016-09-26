package miteat.miteat.Model;

import java.util.List;

import miteat.miteat.Model.Entities.Gps;
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
    public void bookingToMeeting();
    public void setUserDetails(UserDetails userDetails);
    public UserDetails getUserDetails();

}
