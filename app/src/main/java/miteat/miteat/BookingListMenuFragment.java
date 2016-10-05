package miteat.miteat;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import miteat.miteat.Model.Entities.FoodPortions;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.ModelCloudinary;

/**
 * Created by Itzik on 29/09/2016.
 */

public class BookingListMenuFragment extends Fragment {


    Meeting meeting;
    ListView list;
    List<FoodPortions> data;
    MyAddapter adapter;

    interface BookingListMenuFragmentInterface {
        public void backPressFromMenu();
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_list_menu_fragment,
                container, false);
        setHasOptionsMenu(true);
        list = (ListView) view.findViewById(R.id.menuList);
        data = meeting.getFoodPortionsId();
        adapter = new MyAddapter();
        list.setAdapter(adapter);

        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
        emptyText.setText("no menu");
        list.setEmptyView(emptyText);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                FoodPortions f = data.get(position);

            }
        });

        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.bookin_menu_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.back_to_main) {
            BookingListMenuFragmentInterface bookingListMenuFragmentInterface = (BookingListMenuFragmentInterface) getActivity();
            bookingListMenuFragmentInterface.backPressFromMenu();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                convertView = inflater.inflate(R.layout.booking_row_menu_list, null);
                Log.d("TAG", "create view:" + position);

            } else {

                Log.d("TAG", "use convert view:" + position);
            }

            FoodPortions st = data.get(position);
            LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.image_container);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView dishNumber = (TextView) convertView.findViewById(R.id.dishNumber);
            TextView cost = (TextView) convertView.findViewById(R.id.cost);
            TextView allergens = (TextView) convertView.findViewById(R.id.allergens);

            name.setText(st.getName());
            dishNumber.setText(String.valueOf(st.getDishNumber()));
            cost.setText(String.valueOf(st.getCost()));
            allergens.setText(st.getAllergens());
            ArrayList<String>  image = new ArrayList<String>();
            for (int i = 0; i < st.getImages().size(); i++) {
                    image.add(st.getImages().get(i));
            }
            if(image.size()!=0) {

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

            }else {
                final ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.drawable.chef);
                layout.addView(imageView);
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

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }


}
