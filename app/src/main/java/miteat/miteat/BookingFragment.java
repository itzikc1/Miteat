package miteat.miteat;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
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
    private Button menu;
    private TextView numPartners;
    private TextView userNameView;
    private TextView location;
    private TextView date;
    private TextView startTime;
    private TextView endTime;
    private RatingBar star;
    private LinearLayout layout;
    private LinearLayout.LayoutParams layoutParams;
    private ProgressBar mProgress;
    private ArrayList<String> image;

    interface BookingFragmentInterface {
        public void finishBooking();

        public void detailsLoad(String userId, Boolean confirmation);

        public void bookingMenu(Meeting meeting);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_fragment,
                container, false);

        location = (TextView) view.findViewById(R.id.location);
        date = (TextView) view.findViewById(R.id.date);
        startTime = (TextView) view.findViewById(R.id.startTime);
        endTime = (TextView) view.findViewById(R.id.endTime);
        userNameView = (TextView) view.findViewById(R.id.userIdMeeting);
        numPartners = (TextView) view.findViewById(R.id.numUser);
        star = (RatingBar) view.findViewById(R.id.ratingBar);
        //with ferbase:
        //UserDetails userDetails = Model.instance().getUserDetails(meeting.getUserId());
        UserDetails userDetails = Model.instance().getUserDetails();
        layout = (LinearLayout) view.findViewById(R.id.image_container);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        image = new ArrayList<String>();
        for (int i = 0; i < meeting.getFoodPortionsId().size(); i++) {
            for (int j = 0; j < meeting.getFoodPortionsId().get(i).getImages().size(); j++) {
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
        menu = (Button) view.findViewById(R.id.menu);

        // num.setText(meeting.getNumberOfPartner());
        location.setText(meeting.getLocation());
        userNameView.setText(meeting.getUserId());
        Calendar cls = Calendar.getInstance();
        Calendar endCls = Calendar.getInstance();
        endCls.setTimeInMillis(meeting.getDateAndEndTime());

        cls.setTimeInMillis(meeting.getDateAndTime());
        date.setText(cls.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Integer.valueOf(cls.get(Calendar.MONTH)) + 1) + "/" + cls.get(Calendar.YEAR));

        if (cls.get(Calendar.MINUTE) > 10) {
            startTime.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + cls.get(Calendar.MINUTE));
            endTime.setText(endCls.get(Calendar.HOUR_OF_DAY) + ":" + endCls.get(Calendar.MINUTE));
        } else {
            startTime.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + "0" + cls.get(Calendar.MINUTE));
            endTime.setText(endCls.get(Calendar.HOUR_OF_DAY) + ":" + endCls.get(Calendar.MINUTE));
        }
        userNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingFragmentInterface bookingFragmentInterface = (BookingFragmentInterface) getActivity();
                bookingFragmentInterface.detailsLoad(meeting.getUserId(), false);
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booking booking = new Booking(meeting.getUserId(), meeting, Model.instance().getUserDetails().getUserName());
                int numberPlace = Integer.parseInt(numPartners.getText().toString());
                if (Model.instance().checkIfBooking(booking)) {
                    Toast.makeText(getActivity().getApplicationContext(), "You all ready booking to this meeting!", Toast.LENGTH_LONG).show();
                } else {


                    if (numberPlace > meeting.getNumberOfPartner()) {
                        Toast.makeText(getActivity().getApplicationContext(), "We have place only for " + meeting.getNumberOfPartner(), Toast.LENGTH_LONG).show();
                    } else if (numberPlace < 1) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter number bigger than 0", Toast.LENGTH_LONG).show();

                    } else {


                        booking.setNumberOfPartner(numberPlace);
                        booking.setConfirmation(false);
                        Model.instance().bookingToMeeting(booking);
                        Toast.makeText(getActivity().getApplicationContext(), "Thanks for booking with us", Toast.LENGTH_LONG).show();
                        BookingFragmentInterface bookingFragmentInterface = (BookingFragmentInterface) getActivity();
                        bookingFragmentInterface.finishBooking();
                    }
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
                bookingFragmentInterface.detailsLoad(meeting.getUserId(), false);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingFragmentInterface bookingFragmentInterface = (BookingFragmentInterface) getActivity();
                bookingFragmentInterface.bookingMenu(meeting);
            }
        });
        return view;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    private void refreshPic() {
        if (image.size() != 0) {
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
                        if (imageBmp == null) {
                            //mProgress.setVisibility(layout.getVisibility());
                            refreshPic();
                            // Log.d("null image","null image load");
                        } else {
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

        } else {
            final ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(R.drawable.chef);
            layout.addView(imageView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    BookingFragmentInterface bookingFragmentInterface = (BookingFragmentInterface) getActivity();
                    bookingFragmentInterface.finishBooking();
                    return true;
                }
                return false;
            }
        });
    }
}