package miteat.miteat.Model;

import java.util.List;

import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.User;

/**
 * Created by Itzik on 05/06/2016.
 */
public interface ModelInterface {

    public Boolean checkIfUserExist(User user);
    public void addGps(Gps gps);
    public int numberOfRow(String tableName);
    public void addMeeting(Meeting meeting);
    public List<Meeting> getAllMeeting();
    public void deleteMeeting(Meeting meeting);
    public Gps getGpsLocation();

}
