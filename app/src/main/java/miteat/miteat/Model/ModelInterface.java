package miteat.miteat.Model;

import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.User;

/**
 * Created by Itzik on 05/06/2016.
 */
public interface ModelInterface {

    public Boolean checkIfUserExist(User user);
    public void addGps(Gps gps);
    public int numberOfRow(String tableName);

}
