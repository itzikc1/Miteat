package miteat.miteat.Model;

import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.User;
import miteat.miteat.MyApplication;
import miteat.miteat.SQLlite.ModelSql;

/**
 * Created by Itzik on 05/06/2016.
 */

public class Model  implements ModelInterface{

    private final static Model instance = new Model();

    ModelSql sqlModel;
    //ModelFirebase modelFirebase;

    private Model() {
      //  modelFirebase = new ModelFirebase(MyApplication.getAppContext());
        sqlModel = new ModelSql();
    }


    public static Model instance() {
        return instance;
    }

    public Boolean checkIfUserExist(User user){
        return sqlModel.checkIfUserExist(user);
    }

    @Override
    public void addGps(Gps gps) {
        sqlModel.addGps(gps);

    }

    @Override
    public int numberOfRow(String tableName) {
        return 0;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        sqlModel.addMeeting(meeting);
    }

}
