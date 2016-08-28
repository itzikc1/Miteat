package miteat.miteat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import miteat.miteat.Model.Entities.Gps;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Model;
import miteat.miteat.R;

/**
 * Created by Itzik on 05/06/2016.
 */
public class MainFragment extends Fragment{

    ListView list;
    List<Meeting> data;
    MyAddapter adapter;

    interface MeetingListFragmentInterface {


    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_list_fragment,
                container, false);
        list = (ListView) view.findViewById(R.id.listMeeting);
        data = Model.instance().sortByDistance( Model.instance().getAllMeeting());

        adapter = new MyAddapter();
        list.setAdapter(adapter);
        TextView emptyText = (TextView)view.findViewById(android.R.id.empty);
        emptyText.setText("empty meeting!!!");
        list.setEmptyView(emptyText);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Meeting meeting = data.get(position);
                 final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_information_meeting);
                dialog.setTitle("More Details");

                // set the custom dialog components - text, image and button
                TextView typeOfFood = (TextView) dialog.findViewById(R.id.typeOfFood);
                typeOfFood.setText(meeting.getTypeOfFood());

                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.drawable.chef);

                Button dialogButton = (Button) dialog.findViewById(R.id.invite);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();






                Log.d("samll", "samll press in main !!!!!!");

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
                convertView = inflater.inflate(R.layout.main_row_list, null);
                Log.d("TAG", "create view:" + position);

            } else {
                Log.d("TAG", "use convert view:" + position);
            }


            Meeting meeting = data.get(position);

            TextView locationn = (TextView) convertView.findViewById(R.id.location);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView user = (TextView) convertView.findViewById(R.id.user_name);


            Calendar cls = Calendar.getInstance();
            cls.setTimeInMillis(meeting.getDateAndTime());
            date.setText(cls.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Integer.valueOf(cls.get(Calendar.MONTH)) + 1) + "/" + cls.get(Calendar.YEAR));
            time.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + cls.get(Calendar.MINUTE));
            locationn.setText(new DecimalFormat("##.##").format(meeting.getDistance()) + " KM from you");//only two digit
            user.setText("Itzik");


            return convertView;
        }
    }

}
