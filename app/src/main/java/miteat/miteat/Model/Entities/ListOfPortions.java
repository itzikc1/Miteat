package miteat.miteat.Model.Entities;

import java.util.ArrayList;

/**
 * Created by Itzik on 24/07/2016.
 */
public class ListOfPortions {

    private int id;
    private int meetingId;
    private ArrayList<Integer> portionsIds;

    public ListOfPortions(){


    }

    public ArrayList<Integer> getPortionsIds() {
        return portionsIds;
    }

    public void setPortionsIds(ArrayList<Integer> portionsIds) {
        this.portionsIds = portionsIds;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getPortionsIdsString(){
//        String s = "";
//
//        for(int i = 0;i<this.portionsIds.size();i++){
//            if(i+)
//           s += portionsIds.get(i)+",";
//        }
//      return s;
//
//    }

}
