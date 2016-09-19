package miteat.miteat.Model;

import android.location.Location;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.User;
import miteat.miteat.Model.Entities.UserDetails;
import miteat.miteat.SQLlite.ModelSql;

/**
 * Created by Itzik on 05/06/2016.
 */

public class Model implements ModelInterface {

    private final static Model instance = new Model();

    ModelSql sqlModel;

    //ModelFirebase modelFirebase;

    List<Meeting> meetings = new LinkedList<Meeting>();
    HashMap<String, UserDetails> usersDetails = new HashMap<String, UserDetails>();
    // List<UserDetails> usersDetails = new LinkedList<UserDetails>();

    private Model() {

        //  modelFirebase = new ModelFirebase(MyApplication.getAppContext());
        //make fake meeting
        for (int i = 0; i < 10; i++) {
            UserDetails userDetails = new UserDetails("itzik" + i, "12345", 55224);
            //usersDetails.add(userDetails);
            usersDetails.put(userDetails.getUserName(), userDetails);
            long dateAndTime = 0;
            dateAndTime = 44321373 + i;
            Meeting meeting = new Meeting(i, i + 1, "isreali", 50, dateAndTime, dateAndTime, "rishon lezion" + i, 31.959487799999998, 34.8019533, false, 2);
            meeting.setUserId("itzik" + i);
            meetings.add(meeting);

        }

        sqlModel = new ModelSql();
    }


    public static Model instance() {
        return instance;
    }

    public Boolean checkIfUserExist(User user) {
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
        meetings.add(meeting);
        sqlModel.addMeeting(meeting);
    }

    @Override
    public List<Meeting> getAllMeeting() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public Gps getGpsLocation() {
        return sqlModel.getGpsLocation();
    }

    @Override
    public UserDetails getUserDetails(String id) {
        usersDetails.get(id);
        return null;
    }

    @Override
    public void bookingToMeeting() {

    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {

        Location locationA = new Location("point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lon1);
        Location locationB = new Location("point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lon2);
        float distance = locationA.distanceTo(locationB) / 1000; //in KM
        return (double) distance;

    }

//        double theta = lon1 - lon2;
//        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//        return (dist);
//    }
//
//    private double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//    private double rad2deg(double rad) {
//        return (rad * 180.0 / Math.PI);
//    }

    public List<Meeting> sortByDistance(List<Meeting> list) {
        for (int i = 0; i < list.size(); i++) {
            Double dis = distance(Double.parseDouble(getGpsLocation().getLatitude()), Double.parseDouble(getGpsLocation().getLongitude()), list.get(i).getLatLocation(), list.get(i).getLonLocation());
            list.get(i).setDistance(dis);
        }
        Collections.sort(list, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting lhs, Meeting rhs) {
                if (lhs.getDistance() < rhs.getDistance()) {
                    return -1;
                }
                if (lhs.getDistance() > rhs.getDistance()) {
                    return 1;
                }
                return 0;
            }
        });
        return list;
    }
}
