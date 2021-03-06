package miteat.miteat.Model;

import android.location.Location;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import miteat.miteat.Model.Entities.Booking;
import miteat.miteat.Model.Entities.Feedback;
import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.History;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.User;
import miteat.miteat.Model.Entities.UserDetails;
import miteat.miteat.MyApplication;
import miteat.miteat.SQLlite.ModelSql;

/**
 * Created by Itzik on 05/06/2016.
 */

public class Model implements ModelInterface {

    private final static Model instance = new Model();

    ModelSql sqlModel;
    ModelFirebase modelFirebase;
    Gps gps;

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }

    //  List<Meeting> meetings = new LinkedList<Meeting>();
    HashMap<String, UserDetails> usersDetails = new HashMap<String, UserDetails>();

    private Model() {
        sqlModel = new ModelSql();
        modelFirebase = new ModelFirebase(MyApplication.getAppContext());
        this.gps = getGpsLocation();
        //make fake meeting
//        for (int i = 0; i < 10; i++) {
//            UserDetails userDetails = new UserDetails("itzik" + i, "12345", 55224);
//            userDetails.setNumberOfStarAvg(3.6f);
//            usersDetails.put(userDetails.getUserName(), userDetails);
//            long dateAndTime = 0;
//            dateAndTime = 44321373 + i;
//            Meeting meeting = new Meeting(i, i + 1, "isreali", 50, dateAndTime, dateAndTime, "rishon lezion" + i, 31.959487799999998, 34.8019533, false, 1);
//            meeting.setUserId("itzik" + i);
//            meetings.add(meeting);
//        }


//        UserDetails userDetails = new UserDetails("itzik","mail","0525541676");
//        userDetails.setNumberOfStarAvg(3.6f);
//        setUserDetails(userDetails);

        //  usersDetails.put(userDetails.getUserName(), userDetails);//only for offline

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
        this.gps = gps;
    }

    @Override
    public int numberOfRow(String tableName) {
        return 0;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        //  meetings.add(meeting);
        sqlModel.addMeeting(meeting);
    }

//    public Boolean checkMeetingIfUse(final Meeting meeting) {
//
//        Model.instance().getMeetingAsync(meeting, new Model.GetMeetingInterface() {
//            @Override
//            public void onResult(Meeting meetings) {
//
//                if (meetings != null) {
//                    Boolean b = false;
//                    Meeting meeting1 = getMeeting(meetings.getId());
//                if(meetings.getNumberOfPartner()<meeting1.getNumberOfPartner()){
//                   b=true;
//                }
//
//                }else {
//
//                }
//
//            }
//            @Override
//            public void onCancel() {
//            }
//        });
//
//        return true;
//    }

    @Override
    public List<Meeting> getAllMeeting() {

        List<Meeting> meetings = sqlModel.getAllMeeting();
        return meetings;
        // return modelFirebase.getAllMeeting();

    }

    public interface GetAllMeetingInterface {
        public void onResult(List<Meeting> meetings);

        public void onCancel();
    }

    public interface GetMeetingInterface {
        public void onResult(Meeting meetings);

        public void onCancel();
    }

    public interface GetAllBookingInterface {
        public void onResult(List<Booking> bookings);

        public void onCancel();
    }

    public boolean getMeetingAsync(Meeting meeting, GetMeetingInterface listener) {
        modelFirebase.getMeetingAsync(meeting, listener);
        return false;
    }

    public void getAllMeetingsAsynch(GetAllMeetingInterface listener) {
        modelFirebase.getAllMeetingAsynch(listener);
    }

    public void getAllMeetingsToBookingAsynch(GetAllMeetingInterface listener) {
        sqlModel.getAllMeetingToBooking();
        modelFirebase.getAllMeetingToBooking(listener);
    }

    public void getOrderToBookingAsync(GetAllBookingInterface listener) {
        modelFirebase.getOrderToBooking(listener);
    }

    public void getMyBookingListAsync(GetAllBookingInterface listener) {
        modelFirebase.getMyBookingList(listener);
    }

//    public interface GetUserDetailsInterface {
//        public void onResult(UserDetails userDetails);
//
//        public void onCancel();
//    }
//    public void getUserDetailsAsynch(GetUserDetailsInterface listener){
//        modelFirebase.getAllStudentsAsynch(listener);
//    }

    @Override
    public Meeting getMeeting(int id) {
        return sqlModel.getMeeting(id);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        // meetings.remove(meeting);
        sqlModel.deleteMeeting(meeting);
    }

    @Override
    public Gps getGpsLocation() {
        return sqlModel.getGpsLocation();
    }

    @Override
    public UserDetails getUserDetails(String id) {
        // usersDetails.get(id);
        return usersDetails.get(id);
    }

    @Override
    public void bookingToMeeting(Booking booking) {
        sqlModel.bookingToMeeting(booking);

    }

    @Override
    public void setUserDetails(UserDetails userDetails) {
        sqlModel.setUserDetails(userDetails);
    }

    @Override
    public UserDetails getUserDetails() {
        return sqlModel.getUserDetails();
    }

    @Override
    public List<Booking> getMyBookingList() {
        return sqlModel.getMyBookingList();
    }

    @Override
    public Boolean checkIfBooking(Booking booking) {
        return sqlModel.checkIfBooking(booking);
    }

    @Override
    public List<Booking> getOrderToBooking() {
        return sqlModel.getOrderToBooking();
    }

    @Override
    public boolean makeAccept(Booking booking) {
        return sqlModel.makeAccept(booking);
    }

    @Override
    public boolean makeRefuseFromMyMeeting(Booking booking) {
        return sqlModel.makeRefuseFromMyMeeting(booking);
    }

    @Override
    public boolean makeRefuseFromMyBooking(Booking booking) {
        return sqlModel.makeRefuseFromMyBooking(booking);
    }

    @Override
    public List<Meeting> getAllMeetingToBooking() {
        List<Meeting> meetings = sqlModel.getAllMeetingToBooking();
        return meetings;
    }

    @Override
    public List<History> getAllHistoryBookingAndMeeting() {
        return sqlModel.getAllHistoryBookingAndMeeting();
    }

    @Override
    public void giveFeedBack(Feedback feedback) {
        sqlModel.giveFeedBack(feedback);
    }

    @Override
    public void setUpdateToMeetingWithNumberOfPartner(List<Booking> bookings) {

    }

    @Override
    public void updateMeetingToBookingWithTime() {
        sqlModel.updateMeetingToBookingWithTime();

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
        Gps gps = getGpsLocation();
        if (gps == null) {

            Log.d("sort", "no gps");
            return list;
        }
        for (int i = 0; i < list.size(); i++) {
            Double dis = distance(Double.parseDouble(gps.getLatitude()), Double.parseDouble(gps.getLongitude()), list.get(i).getLatLocation(), list.get(i).getLonLocation());
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
