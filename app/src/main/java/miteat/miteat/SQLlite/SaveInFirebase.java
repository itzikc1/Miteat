package miteat.miteat.SQLlite;

import android.content.Context;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.LinkedList;
import java.util.List;

import miteat.miteat.Model.Entities.Booking;
import miteat.miteat.Model.Entities.Feedback;
import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.History;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.User;
import miteat.miteat.Model.Entities.UserDetails;

/**
 * Created by Itzik on 15/10/2016.
 */
public class SaveInFirebase {


    private static final String USER_TABLE = "user_table";
    private static final String GPS_TABLE = "gps_table";
    private static final String MEETING_TABLE = "meeting_table";
    private static final String FOOD_PORTIONS_TABLE = "food_portions_table";
    private static final String USER_DETAILS_TABLE = "user_details_table";
    private static final String ID_MEETING_MAKER = "id_meeting_maker";
    private static final String FEED_BACK_TABLE = "feedBack_table";
    private static final String MY_BOOKING_TABLE = "my_booking_table";
    private static final String MY_BOOKING_MEETING_TABLE = "my_booking_meeting_table";
    private static final String MY_BOOKING_FOOD_PORTIONS_TABLE = "my_booking_food_portions_table";
    private static final String ALL_MEETING_TO_BOOKING_TABLE = "all_meeting_to_booking_table";
    private static final String GEO_FIRE = "geofire";


    Firebase myFirebaseRef;

    SaveInFirebase(Context context) {
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
        Firebase stRef = myFirebaseRef.child(MEETING_TABLE).child(meeting.getUserId()).child(String.valueOf(meeting.getId()));
        stRef.setValue(meeting);

    }

    public List<Meeting> getAllMeeting() {
        final List<Meeting> meetings = new LinkedList<Meeting>();
        Firebase stRef = myFirebaseRef.child(MEETING_TABLE).child("itzik");
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
                    Meeting meeting = stSnapshot.getValue(Meeting.class);
                    meetings.add(meeting);
                    //  meetingss.add(meeting);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        return meetings;

    }

    public Meeting getMeeting(int id) {
        return null;
    }

    public void deleteMeeting(Meeting meeting) {
        myFirebaseRef.child(MEETING_TABLE).child(meeting.getUserId()).child(String.valueOf(meeting.getId())).removeValue();

    }

    public Gps getGpsLocation() {
        return null;
    }

    public UserDetails getUserDetails(String id) {
        return null;
    }

    public void bookingToMeeting(Booking booking) {

        Firebase stRef = myFirebaseRef.child(MY_BOOKING_TABLE).child(booking.getUserIdOfBooking()).child(booking.getMeeting().getUserId() + booking.getMeeting().getId());

        stRef.setValue(booking);
        Firebase stReff = myFirebaseRef.child(MY_BOOKING_MEETING_TABLE).child(booking.getMeeting().getUserId()).child(booking.getUserIdOfBooking() + booking.getMeeting().getId());
        stReff.setValue(booking);
        int num = booking.getMeeting().getNumberOfPartner() - booking.getNumberOfPartner();
        if (num == 0) {
            myFirebaseRef.child(ALL_MEETING_TO_BOOKING_TABLE).child(booking.getMeeting().getUserId() + booking.getMeeting().getId()).removeValue();
            myFirebaseRef.child(GEO_FIRE).child(booking.getMeeting().getUserId() + booking.getMeeting().getId()).removeValue();

        } else {
            booking.getMeeting().setNumberOfPartner(booking.getMeeting().getNumberOfPartner() - booking.getNumberOfPartner());
            Firebase stRefff = myFirebaseRef.child(ALL_MEETING_TO_BOOKING_TABLE).child(booking.getMeeting().getUserId() + booking.getMeeting().getId());
            stRefff.setValue(booking.getMeeting());
        }
    }

    public void setUserDetails(UserDetails userDetails) {

        Firebase stRef = myFirebaseRef.child(USER_DETAILS_TABLE).child(userDetails.getUserName());
        stRef.setValue(userDetails);
    }

    public UserDetails getUserDetails() {
        return null;
    }

    public List<Booking> getMyBookingList() {
        return null;
    }

    public Boolean checkIfBooking(Booking booking) {
        return null;
    }

    public List<Booking> getOrderToBooking() {
        return null;
    }

    public boolean makeAccept(Booking booking) {
        Firebase stRef = myFirebaseRef.child(MY_BOOKING_TABLE).child(booking.getUserIdOfBooking()).child(booking.getMeeting().getUserId() + booking.getMeeting().getId());
        stRef.setValue(booking);

        Firebase stReff = myFirebaseRef.child(MY_BOOKING_MEETING_TABLE).child(booking.getMeeting().getUserId()).child(booking.getUserIdOfBooking() + booking.getMeeting().getId());
        stReff.setValue(booking);
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(GEO_FIRE);
//        GeoFire geoFire = new GeoFire(ref);
//        geoFire.setLocation(booking.getUserIdOfBooking() + booking.getMeeting().getId(), new GeoLocation(booking.getMeeting().getLatLocation(), booking.getMeeting().getLonLocation()), new GeoFire.CompletionListener() {
//            @Override
//            public void onComplete(String key, DatabaseError error) {
//
//            }
//
//        });
        return false;
    }

    public boolean makeRefuseFromMyMeeting(Booking booking) {

        if (booking.getConfirmation() == 3) {//canceled the booking
            myFirebaseRef.child(MY_BOOKING_MEETING_TABLE).child(booking.getMeeting().getUserId()).child(booking.getUserIdOfBooking() + booking.getMeeting().getId()).removeValue();
        } else {
            myFirebaseRef.child(MY_BOOKING_MEETING_TABLE).child(booking.getMeeting().getUserId()).child(booking.getUserIdOfBooking() + booking.getMeeting().getId()).removeValue();
            Firebase stReff = myFirebaseRef.child(MY_BOOKING_TABLE).child(booking.getUserIdOfBooking()).child(booking.getMeeting().getUserId() + booking.getMeeting().getId());
            stReff.setValue(booking);

            addNumberOfPartners(booking);
        }
        return false;
    }

    public boolean makeRefuseFromMyBooking(Booking booking) {


        if (booking.getConfirmation() == 3) {
            myFirebaseRef.child(MY_BOOKING_TABLE).child(booking.getUserIdOfBooking()).child(booking.getMeeting().getUserId() + booking.getMeeting().getId()).removeValue();
        } else {
            myFirebaseRef.child(MY_BOOKING_TABLE).child(booking.getUserIdOfBooking()).child(booking.getMeeting().getUserId() + booking.getMeeting().getId()).removeValue();
            Firebase stReff = myFirebaseRef.child(MY_BOOKING_MEETING_TABLE).child(booking.getMeeting().getUserId()).child(booking.getUserIdOfBooking() + booking.getMeeting().getId());
            stReff.setValue(booking);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(GEO_FIRE);
            GeoFire geoFire = new GeoFire(ref);
            geoFire.setLocation(booking.getMeeting().getUserId() + booking.getMeeting().getId(), new GeoLocation(booking.getMeeting().getLatLocation(), booking.getMeeting().getLonLocation()), new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {

                }

            });
            addNumberOfPartners(booking);
        }

        return false;
    }

    public List<Meeting> getAllMeetingToBooking() {
        return null;
    }

    public List<History> getAllHistoryBookingAndMeeting() {
        return null;
    }

    public void giveFeedBack(Feedback feedback) {

    }

    public void setUpdateToMeetingWithNumberOfPartner(List<Booking> bookings) {

    }

    public void uploadMeetingToBooking(Meeting meetings) {

         Firebase stRef = myFirebaseRef.child(ALL_MEETING_TO_BOOKING_TABLE).child(meetings.getUserId() + meetings.getId());
          stRef.setValue(meetings);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(GEO_FIRE);
        GeoFire geoFire = new GeoFire(ref);
        geoFire.setLocation(meetings.getUserId() + meetings.getId(), new GeoLocation(meetings.getLatLocation(), meetings.getLonLocation()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {

            }

        });



       // Firebase stRef = myFirebaseRef.child(ALL_MEETING_TO_BOOKING_TABLE).child(meetings.getUserId() + meetings.getId());
      //  stRef.setValue(meetings);

    }

    public void deleteMeetingToBooking(Meeting meetings) {
        myFirebaseRef.child(ALL_MEETING_TO_BOOKING_TABLE).child(meetings.getUserId() + meetings.getId()).removeValue();
        myFirebaseRef.child(GEO_FIRE).child(meetings.getUserId() + meetings.getId()).removeValue();
    }

    public void addNumberOfPartners(final Booking booking) {
        Firebase stRef = myFirebaseRef.child(ALL_MEETING_TO_BOOKING_TABLE).child(booking.getMeeting().getUserId() + booking.getMeeting().getId());
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Meeting meeting = snapshot.getValue(Meeting.class);
                Firebase stReff = myFirebaseRef.child(ALL_MEETING_TO_BOOKING_TABLE).child(booking.getMeeting().getUserId() + booking.getMeeting().getId());
                if (meeting == null) {
                    booking.getMeeting().setNumberOfPartner(booking.getNumberOfPartner());
                } else {
                    booking.getMeeting().setNumberOfPartner(meeting.getNumberOfPartner() + booking.getNumberOfPartner());
                }
                stReff.setValue(booking.getMeeting());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}