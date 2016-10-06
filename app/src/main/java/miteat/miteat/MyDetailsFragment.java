package miteat.miteat;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import miteat.miteat.Model.Entities.UserDetails;
import miteat.miteat.Model.Model;
import miteat.miteat.Model.ModelCloudinary;

/**
 * Created by Itzik on 27/09/2016.
 */
public class MyDetailsFragment extends Fragment {


    private Button backView;
    private RatingBar numberOfStarAvgView;
    private RatingBar cleaningStarView;
    private RatingBar serviceStarView;
    private RatingBar atmosphereStarView;
    private RatingBar valueStarView;

    private TextView userNameView;
    private TextView earnMoneyView;
    private TextView avgPayForMealView;

    private EditText phoneNumberView;
    private EditText addressView;
    private EditText mailView;
    private EditText favoriteDishView;
    private UserDetails userDetails;
    private CheckBox loveTakeAwayView;

    private Spinner maritalStatusView;

    private DateEditText birthdayView;

    private ImageButton picProfileView;

    private ArrayAdapter<CharSequence> dataAdapterSpinner;

    interface MyDetailsFragmentInterface {
        public void backPressMyDetails();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_details_fragment,
                container, false);

        numberOfStarAvgView = (RatingBar) view.findViewById(R.id.numberOfStarAvg);
        cleaningStarView = (RatingBar) view.findViewById(R.id.cleaningStar);
        serviceStarView = (RatingBar) view.findViewById(R.id.serviceStar);
        atmosphereStarView = (RatingBar) view.findViewById(R.id.atmosphereStar);
        valueStarView = (RatingBar) view.findViewById(R.id.valueStar);
        phoneNumberView = (EditText) view.findViewById(R.id.phoneNumber);
        addressView = (EditText) view.findViewById(R.id.address);
        mailView = (EditText) view.findViewById(R.id.mail);
        favoriteDishView = (EditText) view.findViewById(R.id.favoriteDish);
        userNameView = (TextView) view.findViewById(R.id.userName);
        earnMoneyView = (TextView) view.findViewById(R.id.earnMoney);
        avgPayForMealView = (TextView) view.findViewById(R.id.avgPayForMeal);
        birthdayView = (DateEditText) view.findViewById(R.id.birthday);
        loveTakeAwayView = (CheckBox) view.findViewById(R.id.loveTakeAway);
        backView = (Button) view.findViewById(R.id.back);
        picProfileView = (ImageButton) view.findViewById(R.id.picProfile);
        maritalStatusView = (Spinner) view.findViewById(R.id.spinner);
        dataAdapterSpinner = ArrayAdapter.createFromResource(getActivity(), R.array.maritalStatus, android.R.layout.simple_spinner_item);
        dataAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maritalStatusView.setAdapter(dataAdapterSpinner);

//        final UserDetails userDetails = Model.instance().getUserDetails();
        userDetails = Model.instance().getUserDetails();

        numberOfStarAvgView.setRating(userDetails.getNumberOfStarAvg());
        cleaningStarView.setRating(userDetails.getCleaningStar());
        serviceStarView.setRating(userDetails.getServiceStar());
        atmosphereStarView.setRating(userDetails.getAtmosphereStar());
        valueStarView.setRating(userDetails.getValueStar());
        userNameView.setText(userDetails.getUserName());
        earnMoneyView.setText(String.valueOf(userDetails.getEarnMoney()));
        avgPayForMealView.setText(String.valueOf(userDetails.getAvgPayForMeal()));
        phoneNumberView.setText(userDetails.getPhoneNumber());
        addressView.setText(String.valueOf(userDetails.getAddress()));
        mailView.setText(String.valueOf(userDetails.getMail()));
        favoriteDishView.setText(String.valueOf(userDetails.getFavoriteDish()));
        loveTakeAwayView.setChecked(userDetails.getLoveTakeAway());
        Calendar cls = Calendar.getInstance();
        cls.setTimeInMillis(userDetails.getBirthday());
        birthdayView.setText(cls.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Integer.valueOf(cls.get(Calendar.MONTH)) + 1) + "/" + cls.get(Calendar.YEAR));
        maritalStatusView.setSelection(userDetails.getMaritalStatus());
        picProfileView = (ImageButton) view.findViewById(R.id.picProfile);
        if (userDetails.getPicProfile() == null) {
            picProfileView.setImageResource(R.drawable.chef);
        } else {

            ModelCloudinary.getInstance().loadImage(userDetails.getPicProfile(), new ModelCloudinary.LoadImageListener() {
                @Override
                public void onResult(Bitmap imageBmp) {

                    if (imageBmp == null) {
                        picProfileView.setImageResource(R.drawable.chef);
                    } else {
                        if (imageBmp.getWidth() > 1080 || imageBmp.getHeight() > 720) {
                            imageBmp = Bitmap.createScaledBitmap(imageBmp, 800, 1080, true);
                        }
                        picProfileView.setImageBitmap(imageBmp);
                    }
                }
            });
        }
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  String picProfile;
                String phoneNumber = phoneNumberView.getText().toString();
                String address = addressView.getText().toString();
                int maritalStatus = maritalStatusView.getSelectedItemPosition();
                Boolean loveTakeAway = loveTakeAwayView.isChecked();
                String favoriteDish = favoriteDishView.getText().toString();
                Long birthday = new Long(0);
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(birthdayView.getYear(), birthdayView.getMonth(), birthdayView.getDay());
                birthday = beginTime.getTimeInMillis();
                userDetails.setPhoneNumber(phoneNumber);
                userDetails.setAddress(address);
                userDetails.setMaritalStatus(maritalStatus);
                userDetails.setLoveTakeAway(loveTakeAway);
                userDetails.setFavoriteDish(favoriteDish);
                userDetails.setBirthday(birthday);
                Model.instance().setUserDetails(userDetails);

                MyDetailsFragmentInterface myDetailsFragmentInterface = (MyDetailsFragmentInterface) getActivity();
                myDetailsFragmentInterface.backPressMyDetails();

            }
        });

        return view;
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

                    String phoneNumber = phoneNumberView.getText().toString();
                    String address = addressView.getText().toString();
                    int maritalStatus = maritalStatusView.getSelectedItemPosition();
                    Boolean loveTakeAway = loveTakeAwayView.isChecked();
                    String favoriteDish = favoriteDishView.getText().toString();
                    Long birthday = new Long(0);
                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(birthdayView.getYear(), birthdayView.getMonth(), birthdayView.getDay());
                    birthday = beginTime.getTimeInMillis();
                    userDetails.setPhoneNumber(phoneNumber);
                    userDetails.setAddress(address);
                    userDetails.setMaritalStatus(maritalStatus);
                    userDetails.setLoveTakeAway(loveTakeAway);
                    userDetails.setFavoriteDish(favoriteDish);
                    userDetails.setBirthday(birthday);
                    Model.instance().setUserDetails(userDetails);
                    MyDetailsFragmentInterface myDetailsFragmentInterface = (MyDetailsFragmentInterface) getActivity();
                    myDetailsFragmentInterface.backPressMyDetails();

                    Log.d("back","back");

//                    MyDetailsFragmentInterface myDetailsFragmentInterface = (MyDetailsFragmentInterface) getActivity();
//                    myDetailsFragmentInterface.backPressMyDetails();
                    return true;
                }
                return false;
            }
        });
    }

}
