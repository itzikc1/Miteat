package miteat.miteat.Model;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import miteat.miteat.Model.Entities.Booking;
import miteat.miteat.Model.Entities.Feedback;
import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.History;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.User;
import miteat.miteat.Model.Entities.UserDetails;

/**
 * Created by Itzik on 07/07/2016.
 */
public class ModelFirebase {


    private static final String USER_TABLE = "user_table";
    private static final String GPS_TABLE = "gps_table";
    private static final String MEETING_TABLE = "meeting_table";
    private static final String FOOD_PORTIONS_TABLE = "food_portions_table";
    private static final String USER_DETAILS_TABLE = "user_details_table";
    private static final String FEED_BACK_TABLE = "feedBack_table";
    private static final String MY_BOOKING_TABLE = "my_booking_table";
    private static final String MY_BOOKING_MEETING_TABLE = "my_booking_meeting_table";
    private static final String MY_BOOKING_FOOD_PORTIONS_TABLE = "my_booking_food_portions_table";
    private static final String ALL_MEETING_TO_BOOKING_TABLE = "all_meeting_to_booking_table";

    Firebase myFirebaseRef;

    ModelFirebase(Context context) {
        Firebase.setAndroidContext(context);
        myFirebaseRef = new Firebase("https://miteat-74d63.firebaseio.com/");
    }


    public Boolean checkIfUserExist(User user) {
        return null;
    }

    public void addGps(Gps gps) {

    }

    public int numberOfRow(String tableName) {
        return 0;
    }

    public void addMeeting(Meeting meeting) {
        Firebase stRef = myFirebaseRef.child("Meeting").child(meeting.getUserId()).child(String.valueOf(meeting.getId()));
        stRef.setValue(meeting);

    }


//    public interface GetAllMeetingInterface {
//        public void onResult(List<Meeting> meetings);
//        public void onCancel();
//    }
//    public void getAllMeetingsAsynch(GetAllMeetingInterface listener) {
//       getAllMeetingAsynch(listener);
//    }

    public void getAllMeetingAsynch(final Model.GetAllMeetingInterface listener) {

        Firebase stRef = myFirebaseRef.child(MEETING_TABLE).child("itzik");
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final List<Meeting> meetings = new LinkedList<Meeting>();
                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
                    //   Meeting meeting = stSnapshot.getValue(Meeting.class);
                    //    meetings.add(meeting);
                }
                listener.onResult(meetings);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }


//    public void getAllMeetingToBooking(final Model.GetAllMeetingInterface listener) {
//
//        Firebase stRef = myFirebaseRef.child(ALL_MEETING_TO_BOOKING_TABLE);
//        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                final List<Meeting> meetings = new LinkedList<Meeting>();
//                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
//                    Meeting meeting = stSnapshot.getValue(Meeting.class);
//                    meetings.add(meeting);
//                }
//                listener.onResult(meetings);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//            }
//        });
//
//    }

    public synchronized void getAllMeetingToBooking(final Model.GetAllMeetingInterface listener) {
        Gps gps = Model.instance().getGpsLocation();
        Firebase stRef = myFirebaseRef.child(ALL_MEETING_TO_BOOKING_TABLE);
   //   Query quRef = stRef.orderByChild("latLocation").limitToLast(5).endAt(gps.getLatitude());

        Query quRef = stRef.orderByChild("latLocation").endAt(gps.getLatitude());

//        .orderByChild('latitude')
//                .startAt(whereAmI.latitude - 0.002)
//                .endAt(whereAmI.latitude + 0.002)
//                .orderByChild('longitude')
//                .startAt(whereAmI.longitude- 0.002)
//                .endAt(whereAmI.longitude+ 0.002)


      //  GeoLocation location =new GeoLocation(Double.valueOf(gps.getLatitude()),Double.valueOf(gps.getLongitude()));
     //   GeoFire geoFire = myFirebaseRef.child(ALL_MEETING_TO_BOOKING_TABLE);
    //    GeoQuery query = new GeoQuery(stRef,location,5);
   //     GeoQuery geoQuery = quRef.queryAtLocation(location, 1.6);

        quRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final List<Meeting> meetings = new LinkedList<Meeting>();
                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
                    Meeting meeting = stSnapshot.getValue(Meeting.class);
                    meetings.add(meeting);
                }
                listener.onResult(meetings);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }


    public List<Meeting> getAllMeeting() {
        final List<Meeting> meetings = new LinkedList<Meeting>();
        Firebase stRef = myFirebaseRef.child(MEETING_TABLE).child("itzik");
        Semaphore semaphore = new Semaphore(1);
        try {

            stRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot stSnapshot : snapshot.getChildren()) {
                        Meeting meeting = stSnapshot.getValue(Meeting.class);
                        meetings.add(meeting);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return meetings;

    }

    public void getMeetingAsync(Meeting meeting, final Model.GetMeetingInterface listener) {

        Firebase stRef = myFirebaseRef.child(ALL_MEETING_TO_BOOKING_TABLE).child(meeting.getUserId() + meeting.getId());
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Meeting meeting1 = snapshot.getValue(Meeting.class);
                listener.onResult(meeting1);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

    }

    public void deleteMeeting(Meeting meeting) {

    }

    public Gps getGpsLocation() {
        return null;
    }

    public UserDetails getUserDetails(String id) {
        return null;
    }

    public void bookingToMeeting(Booking booking) {

    }

    public void setUserDetails(UserDetails userDetails) {

    }

    public UserDetails getUserDetails() {
        return null;
    }


    public void getMyBookingList(final Model.GetAllBookingInterface listener) {

        Firebase stRef = myFirebaseRef.child(MY_BOOKING_TABLE).child(Model.instance().getUserDetails().getUserName());
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final List<Booking> bookings = new LinkedList<Booking>();
                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
                    Booking booking = stSnapshot.getValue(Booking.class);
                    bookings.add(booking);
                }
                listener.onResult(bookings);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public Boolean checkIfBooking(Booking booking) {
        return null;
    }

    public void getOrderToBooking(final Model.GetAllBookingInterface listener) {

        Firebase stRef = myFirebaseRef.child(MY_BOOKING_MEETING_TABLE).child(Model.instance().getUserDetails().getUserName());
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final List<Booking> bookings = new LinkedList<Booking>();
                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
                    Booking booking = stSnapshot.getValue(Booking.class);
                    bookings.add(booking);
                }
                listener.onResult(bookings);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public boolean makeAccept(Booking booking) {
        return false;
    }

    public boolean makeRefuseFromMyMeeting(Booking booking) {
        return false;
    }

    public boolean makeRefuseFromMyBooking(Booking booking) {
        return false;
    }

    public List<History> getAllHistoryBookingAndMeeting() {
        return null;
    }

    public void giveFeedBack(Feedback feedback) {

    }

}
