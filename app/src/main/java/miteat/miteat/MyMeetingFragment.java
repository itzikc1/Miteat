package miteat.miteat;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import miteat.miteat.Model.Entities.Booking;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Model;

/**
 * Created by Itzik on 09/10/2016.
 */
public class MyMeetingFragment extends Fragment{

    Meeting meeting;
    ListView list;
    List<Booking> data;
    MyAddapter adapter;

    interface MyMeetingFragmentInterface {
        public void detailsLoad(String userId, Boolean confirmation);

        public void meetingMenu(Meeting meeting);

    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_booking_fragment,
                container, false);
        //    setHasOptionsMenu(true);
        list = (ListView) view.findViewById(R.id.myBookingList);
        data = Model.instance().getOrderToBooking();
        adapter = new MyAddapter();
        list.setAdapter(adapter);

        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
        emptyText.setText("no menu");
        list.setEmptyView(emptyText);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // FoodPortions f = data.get(position);

            }
        });

        return view;
    }
    class MyAddapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.my_meeting_row, null);
                Log.d("TAG", "create view:" + position);

            } else {

                Log.d("TAG", "use convert view:" + position);
            }

            final Booking booking  = data.get(position);

            TextView userName = (TextView) convertView.findViewById(R.id.userName);
            TextView moreOnUser = (TextView) convertView.findViewById(R.id.moreOnUser);
            TextView moreOnMeeting = (TextView) convertView.findViewById(R.id.moreOnMeeting);
            TextView address = (TextView) convertView.findViewById(R.id.address);
            TextView numberOfVoters = (TextView) convertView.findViewById(R.id.numOfVoters);
            RatingBar numberOfStarAvg = (RatingBar) convertView.findViewById(R.id.ratingBar);
            Button refuse = (Button) convertView.findViewById(R.id.refuse);
            Button accept = (Button) convertView.findViewById(R.id.accept);

            if(booking.getConfirmation()==1){
                accept.setVisibility(View.GONE);
            }

            userName.setText(booking.getUserIdOfBooking());
            address.setText(booking.getMeeting().getLocation());
            //change when we have firebase
            numberOfVoters.setText(String.valueOf(Model.instance().getUserDetails().getFeedbacks().size()));
            numberOfStarAvg.setRating(Model.instance().getUserDetails().getNumberOfStarAvg());

            moreOnUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyMeetingFragmentInterface myMeetingFragmentInterface = (MyMeetingFragmentInterface) getActivity();
                    myMeetingFragmentInterface.detailsLoad(booking.getUserIdOfBooking(), true);

                }
            });

            moreOnMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyMeetingFragmentInterface  myMeetingFragmentInterface = (MyMeetingFragmentInterface) getActivity();
                    myMeetingFragmentInterface.meetingMenu(booking.getMeeting());

                }
            });

            refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    booking.setConfirmation(2);

                    Model.instance().makeAccept(booking);
                }
            });
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    booking.setConfirmation(1);
                    booking.getMeeting().setNumberOfPartner(booking.getMeeting().getNumberOfPartner()-booking.getNumberOfPartner());
                    Model.instance().makeAccept(booking);
                }
            });

            return convertView;
        }

    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }



}
