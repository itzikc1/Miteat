package miteat.miteat;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import miteat.miteat.Model.Entities.FoodPortions;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Entities.UserDetails;
import miteat.miteat.Model.Model;
import miteat.miteat.Model.ModelCloudinary;

/**
 * Created by Itzik on 27/09/2016.
 */
public class UserDetailsFragment extends Fragment {

    private UserDetails userDetails;
    private TextView numberOfUserStarAvg;
    private TextView userName;
    private TextView phoneNumber;
    private TextView address;
    private TextView mail;
    private TextView maritalStatus;
    private TextView favoriteDish;
    private Button back;
    private RatingBar numberOfStarAvgView;
    private RatingBar cleaningStarView;
    private RatingBar serviceStarView;
    private RatingBar atmosphereStarView;
    private RatingBar valueStarView;
    private ListView list;
    private ImageView picProfile;
    private MyAddapter adapter;
    private Boolean confirmation;

    interface UserDetailsInterface {
        public void backPressUserDetails();
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_details_fragment,
                container, false);

        numberOfUserStarAvg = (TextView) view.findViewById(R.id.numberOfStarAvgUsers);
        userName = (TextView) view.findViewById(R.id.userName);
        phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        address = (TextView) view.findViewById(R.id.address);
        mail = (TextView) view.findViewById(R.id.mail);
        maritalStatus = (TextView) view.findViewById(R.id.maritalStatus);
        favoriteDish = (TextView) view.findViewById(R.id.favoriteDish);
        picProfile = (ImageView) view.findViewById(R.id.picProfile);
        list = (ListView) view.findViewById(R.id.feedbacks);
        back = (Button) view.findViewById(R.id.back);
        numberOfStarAvgView = (RatingBar) view.findViewById(R.id.numberOfStarAvg);
        cleaningStarView = (RatingBar) view.findViewById(R.id.cleaningStar);
        serviceStarView = (RatingBar) view.findViewById(R.id.serviceStar);
        atmosphereStarView = (RatingBar) view.findViewById(R.id.atmosphereStar);
        valueStarView = (RatingBar) view.findViewById(R.id.valueStar);

        adapter = new MyAddapter();
        list.setAdapter(adapter);

        numberOfUserStarAvg.setText(String.valueOf("Number of users: "+userDetails.getFeedbacks().size()));
        numberOfStarAvgView.setRating(userDetails.getNumberOfStarAvg());
        cleaningStarView.setRating(userDetails.getCleaningStar());
        serviceStarView.setRating(userDetails.getServiceStar());
        atmosphereStarView.setRating(userDetails.getAtmosphereStar());
        valueStarView.setRating(userDetails.getValueStar());

        userName.setText("User name: "+userDetails.getUserName());
        phoneNumber.setText(String.valueOf("Phone number: "+String.valueOf(userDetails.getPhoneNumber())));
        address.setText(String.valueOf("Address: "+userDetails.getAddress()));
        mail.setText(String.valueOf("Mail "+userDetails.getMail()));


        if(confirmation==false){
            phoneNumber.setVisibility(View.GONE);
            mail.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
        }

        String[] some_array = getResources().getStringArray(R.array.maritalStatus);
        maritalStatus.setText(String.valueOf("Marital status "+some_array[userDetails.getMaritalStatus()]));
        favoriteDish.setText(String.valueOf("Favorite dish "+userDetails.getFavoriteDish()));
        numberOfStarAvgView.setRating(userDetails.getNumberOfStarAvg());

//        ModelCloudinary.getInstance().loadImage(userDetails.getPicProfile() , new ModelCloudinary.LoadImageListener() {
//            @Override
//            public void onResult(Bitmap imageBmp) {
//                    picProfile.setImageBitmap(imageBmp);
//            }
//        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetailsInterface userDetailsFragment = (UserDetailsInterface) getActivity();
                userDetailsFragment.backPressUserDetails();
            }
        });
        return view;
    }
    class MyAddapter extends BaseAdapter {

        @Override
        public int getCount() {
            return userDetails.getFeedbacks().size();
        }

        @Override
        public Object getItem(int position) {
            return userDetails.getFeedbacks().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
//                LayoutInflater inflater = getActivity().getLayoutInflater();
//                convertView = inflater.inflate(R.layout.menu_portions_fragment_in_list, null);
//                Log.d("TAG", "create view:" + position);

            } else {

                //Log.d("TAG", "use convert view:" + position);
            }

//            TextView namee = (TextView) convertView.findViewById(R.id.name);
//            TextView dish = (TextView) convertView.findViewById(R.id.numberOfDish);
//            FoodPortions st = data.get(position);
//            if(!st.getName().equals("")) {
//                namee.setText(st.getName());
//            }
//            dish.setText(String.valueOf(st.getNumberOfFoodPortions()));

            return convertView;
        }
    }

    public void setUserId(String userId){
        //with fierbase:
        //this.userDetails = Model.instance().getUserDetails(userId);
        this.userDetails = Model.instance().getUserDetails();
    }
    public void setConfirmation(Boolean confirmation){
        this.confirmation=confirmation;
    }


}
