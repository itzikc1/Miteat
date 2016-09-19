package miteat.miteat;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Calendar;

import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.UserDetails;
import miteat.miteat.Model.Model;

/**
 * Created by Itzik on 11/09/2016.
 */
public class BookingFragment extends Fragment {

    private Meeting meeting;
    private Button booking;
    private Button cancel;
    private Button details;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_fragment,
                container, false);

        TextView location = (TextView) view.findViewById(R.id.location);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView num = (TextView) view.findViewById(R.id.numUser);
        RatingBar star = (RatingBar) view.findViewById(R.id.ratingBar);
        UserDetails userDetails = Model.instance().getUserDetails(meeting.getUserId());



        star.setRating(userDetails.getNumberOfStarAvg());
      //  star.setRating(Model.instance().getUserDetails(meeting.getUserId()).getNumberOfStarAvg());

        booking = (Button) view.findViewById(R.id.booking);
        cancel = (Button) view.findViewById(R.id.cancel);
        details = (Button) view.findViewById(R.id.details);

        // num.setText(meeting.getNumberOfPartner());
        location.setText(meeting.getLocation());
        Calendar cls = Calendar.getInstance();
        cls.setTimeInMillis(meeting.getDateAndTime());
        date.setText(cls.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Integer.valueOf(cls.get(Calendar.MONTH)) + 1) + "/" + cls.get(Calendar.YEAR));

        if (cls.get(Calendar.MINUTE) > 10)
            time.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + cls.get(Calendar.MINUTE));

        else
            time.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" +"0" + cls.get(Calendar.MINUTE));



        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(editPortions!=null){
//                    meeting.getFoodPortionsId().remove(editPortions);
//                }
//
//                numberId = (EditText) view.findViewById(R.id.portionsNumber);
//                costOfMoney = (EditText) view.findViewById(R.id.cost);
//                name = (EditText) view.findViewById(R.id.name);
//                String allergens = multiAutoComplete.getText().toString();
//                int numberIdText = Integer.parseInt(numberId.getText().toString());
//                int money = Integer.parseInt(costOfMoney.getText().toString());
//                FoodPortions foodPortions = new FoodPortions(numberIdText,name.getText().toString(), image, numberIdText, money, allergens);
//                addFoodPortions(foodPortions);
//                MenuFragmentInterface menuFragmentInterface = (MenuFragmentInterface) getActivity();
//                menuFragmentInterface.saveInterface(meeting);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MenuFragmentInterface menuFragmentInterface = (MenuFragmentInterface) getActivity();
//                menuFragmentInterface.saveInterface(meeting);
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return view;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
