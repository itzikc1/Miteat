package miteat.miteat;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Itzik on 27/09/2016.
 */
public class MyDetailsFragment extends Fragment {

    //    interface UserDetailsInterface {
//        public void backPressUserDetails();
//    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_details_fragment,
                container, false);


//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UserDetailsInterface userDetailsFragment = (UserDetailsInterface) getActivity();
//                userDetailsFragment.backPressUserDetails();
//            }
//        });
        return view;
    }


}
