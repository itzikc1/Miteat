package miteat.miteat;

import android.app.Dialog;
import android.app.Fragment;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Model;

/**
 * Created by Itzik on 05/06/2016.
 */
public class MainFragment extends Fragment {

    ListView list;
    List<Meeting> data = new LinkedList<Meeting>();
    MyAddapter adapter;
    ProgressBar progressBar;
    TextView emptyText;

    interface MainListFragmentInterface {
        public void booking(Meeting meeting);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_list_fragment,
                container, false);
        list = (ListView) view.findViewById(R.id.listMeeting);
//        data = Model.instance().sortByDistance(Model.instance().getAllMeeting());

        // data = Model.instance().sortByDistance(Model.instance().getAllMeetingToBooking());
        progressBar = (ProgressBar) view.findViewById(R.id.mainListProgressBar);

        adapter = new MyAddapter();
        list.setAdapter(adapter);
        emptyText = (TextView) view.findViewById(android.R.id.empty);
        loadDataMeetingToBooking();
        //  emptyText.setText(R.string.emptyMeeting);
        //  list.setEmptyView(emptyText);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                final Meeting meeting = data.get(position);
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_information_meeting);
                dialog.setTitle("More Details");

                // set the custom dialog components - text, image and button
                TextView typeOfFood = (TextView) dialog.findViewById(R.id.typeOfFood);
                typeOfFood.setText(meeting.getTypeOfFood());

                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.drawable.chef);

                Button invite = (Button) dialog.findViewById(R.id.invite);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);

                invite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        MainListFragmentInterface mainListFragmentInterface = (MainListFragmentInterface) getActivity();
                        mainListFragmentInterface.booking(meeting);


                    }
                });
                // if button is clicked, close the custom dialog

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();


                    }
                });

                dialog.show();


                // Log.d("samll", "samll press in main !!!!!!");

            }
        });

        return view;
    }

    void loadDataMeetingToBooking() {
        progressBar.setVisibility(View.VISIBLE);
        //   Model.instance().updateMeetingToBookingWithTime();//update my meeting list
        Model.instance().getAllMeetingsToBookingAsynch(new Model.GetAllMeetingInterface() {
            @Override
            public void onResult(List<Meeting> meetings) {
                data = Model.instance().sortByDistance(meetings);
                if (data.size() == 0) {
                    emptyText.setText(R.string.emptyMeeting);
                    list.setEmptyView(emptyText);
                }
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {

            }
        });
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
                //   Log.d("TAG", "create view:" + position);

            } else {
                //  Log.d("TAG", "use convert view:" + position);
            }


            Meeting meeting = data.get(position);

            TextView locationn = (TextView) convertView.findViewById(R.id.location);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView user = (TextView) convertView.findViewById(R.id.user_name);


            Calendar cls = Calendar.getInstance();
            cls.setTimeInMillis(meeting.getDateAndTime());
            date.setText(cls.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Integer.valueOf(cls.get(Calendar.MONTH)) + 1) + "/" + cls.get(Calendar.YEAR));
            if (cls.get(Calendar.MINUTE) > 10)
                time.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + cls.get(Calendar.MINUTE));

            else
                time.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + "0" + cls.get(Calendar.MINUTE));
            if (meeting.getDistance() != null) {
                locationn.setText(new DecimalFormat("##.##").format(meeting.getDistance()) + " KM from you");//only two digit
            }

            user.setText("Itzik");


            return convertView;
        }
    }

}
