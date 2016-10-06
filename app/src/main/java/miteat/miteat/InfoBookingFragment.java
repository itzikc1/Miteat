package miteat.miteat;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.ModelCloudinary;

/**
 * Created by Itzik on 11/09/2016.
 */
public class InfoBookingFragment extends Fragment {

    private Meeting meeting;
    private Button back;
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
    private ArrayList<String> image;


    interface InfoBookingFragmentInterface {
        public void backToListBooking();

        public void bookingGoTOhMenu(Meeting meeting);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_booking_fragment,
                container, false);
        setHasOptionsMenu(true);
        location = (TextView) view.findViewById(R.id.location);
        date = (TextView) view.findViewById(R.id.date);
        startTime = (TextView) view.findViewById(R.id.startTime);
        endTime = (TextView) view.findViewById(R.id.endTime);
        userNameView = (TextView) view.findViewById(R.id.userIdMeeting);
        numPartners = (TextView) view.findViewById(R.id.numUser);

        layout = (LinearLayout) view.findViewById(R.id.image_container);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        image = new ArrayList<String>();
        for (int i = 0; i < meeting.getFoodPortionsId().size(); i++) {
            for (int j = 0; j < meeting.getFoodPortionsId().get(i).getImages().size(); j++) {
                image.add(meeting.getFoodPortionsId().get(i).getImages().get(j));
            }
        }

        refreshPic();

        back = (Button) view.findViewById(R.id.back);
        menu = (Button) view.findViewById(R.id.menu);

        // num.setText(meeting.getNumberOfPartner());
        location.setText(meeting.getLocation());
        userNameView.setText(meeting.getUserId());
        Calendar cls = Calendar.getInstance();
        Calendar endCls = Calendar.getInstance();
        endCls.setTimeInMillis(meeting.getDateAndEndTime());
        numPartners.setText(String.valueOf(meeting.getNumberOfPartner()));
        //       numPartners.setText("1");


        cls.setTimeInMillis(meeting.getDateAndTime());
        date.setText(cls.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Integer.valueOf(cls.get(Calendar.MONTH)) + 1) + "/" + cls.get(Calendar.YEAR));

        if (cls.get(Calendar.MINUTE) > 10) {
            startTime.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + cls.get(Calendar.MINUTE));
            endTime.setText(endCls.get(Calendar.HOUR_OF_DAY) + ":" + endCls.get(Calendar.MINUTE));
        } else {
            startTime.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + "0" + cls.get(Calendar.MINUTE));
            endTime.setText(endCls.get(Calendar.HOUR_OF_DAY) + ":" + endCls.get(Calendar.MINUTE));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoBookingFragmentInterface bookingFragmentInterface = (InfoBookingFragmentInterface) getActivity();
                bookingFragmentInterface.backToListBooking();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoBookingFragmentInterface bookingFragmentInterface = (InfoBookingFragmentInterface) getActivity();
                bookingFragmentInterface.bookingGoTOhMenu(meeting);
            }
        });


        return view;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
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
                    InfoBookingFragmentInterface bookingFragmentInterface = (InfoBookingFragmentInterface) getActivity();
                    bookingFragmentInterface.backToListBooking();
                    return true;
                }
                return false;
            }
        });
    }


    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    private void refreshPic() {
        if (image.size() != 0) {
            layout.removeAllViews();//clean all pic before

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
}
