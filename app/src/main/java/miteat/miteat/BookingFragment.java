package miteat.miteat;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import miteat.miteat.Model.Entities.Booking;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.UserDetails;
import miteat.miteat.Model.Model;
import miteat.miteat.Model.ModelCloudinary;

/**
 * Created by Itzik on 11/09/2016.
 */
public class BookingFragment extends Fragment {

    private Meeting meeting;
    private Button booking;
    private Button cancel;
    private Button details;
    private TextView numPartners;
    private LinearLayout layout;
    private LinearLayout.LayoutParams layoutParams;
    private ProgressBar mProgress;
    private ArrayList<String> image;
    interface BookingFragmentInterface {
        public void finishBooking();

        public void detailsLoad(String userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_fragment,
                container, false);

        TextView location = (TextView) view.findViewById(R.id.location);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView time = (TextView) view.findViewById(R.id.time);
        numPartners = (TextView) view.findViewById(R.id.numUser);
        RatingBar star = (RatingBar) view.findViewById(R.id.ratingBar);
        UserDetails userDetails = Model.instance().getUserDetails(meeting.getUserId());

        layout = (LinearLayout) view.findViewById(R.id.image_container);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        image = new ArrayList<String>();
        for (int i = 0; i < meeting.getFoodPortionsId().size(); i++) {
            for (int j =0;j<meeting.getFoodPortionsId().get(i).getImages().size();j++){
                image.add(meeting.getFoodPortionsId().get(i).getImages().get(j));
            }
        }
        star.setRating(userDetails.getNumberOfStarAvg());
        refreshPic();
        // star.setRating(4);
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
            time.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + "0" + cls.get(Calendar.MINUTE));


        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int numberPlace = Integer.parseInt(numPartners.getText().toString());
                if (numberPlace > meeting.getNumberOfPartner()) {
                    Toast.makeText(getActivity().getApplicationContext(), "We have place only for " + meeting.getNumberOfPartner(), Toast.LENGTH_LONG).show();
                } else if (numberPlace < 1) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter number bigger than 0", Toast.LENGTH_LONG).show();

                } else {

                    Booking booking = new Booking(meeting, -1, Model.instance().getUserDetails().getUserName());
                    booking.setNumberOfPartner(numberPlace);
                    booking.setConfirmation(false);
                    Model.instance().bookingToMeeting(booking);
                    Toast.makeText(getActivity().getApplicationContext(), "Thanks for booking with us", Toast.LENGTH_LONG).show();
                    BookingFragmentInterface bookingFragmentInterface = (BookingFragmentInterface) getActivity();
                    bookingFragmentInterface.finishBooking();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingFragmentInterface bookingFragmentInterface = (BookingFragmentInterface) getActivity();
                bookingFragmentInterface.finishBooking();
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingFragmentInterface bookingFragmentInterface = (BookingFragmentInterface) getActivity();
                bookingFragmentInterface.detailsLoad(meeting.getUserId());
            }
        });
        return view;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    private void refreshPic() {
        layout.removeAllViews();//clean all pic before
        //mProgress.setVisibility(layout.getVisibility());
       // mProgress.setVisibility(layout.getVisibility());
        for (int i = 0; i < image.size(); i++) {
            layoutParams.setMargins(1, 1, 1, 1);
            layoutParams.gravity = Gravity.CENTER;
            final ImageView imageView = new ImageView(getActivity());
            final String srt = image.get(i);

            ModelCloudinary.getInstance().loadImage(image.get(i), new ModelCloudinary.LoadImageListener() {
                @Override
                public void onResult(Bitmap imageBmp) {

                    if(imageBmp==null){
                        //mProgress.setVisibility(layout.getVisibility());
                        refreshPic();
                        // Log.d("null image","null image load");
                    }
                    else{
                        if (imageBmp.getWidth() > 1080 || imageBmp.getHeight() > 720) {
                            imageBmp = Bitmap.createScaledBitmap(imageBmp, 800, 1080, true);
                        }
                       // mProgress.setVisibility(layout.GONE);
                        imageView.setImageBitmap(imageBmp);


                    }
                }
            });

            imageView.setLayoutParams(layoutParams);
            layout.addView(imageView);
        }


    }
}
