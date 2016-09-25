package miteat.miteat;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Model;

/**
 * Created by Itzik on 02/08/2016.
 */
public class MeetingListFragment extends Fragment {
    ListView list;
    List<Meeting> data;
    MyAddapter adapter;

    interface MeetingListFragmentInterface {
        public void refreshList();

        public void editMeeting(Meeting meeting);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_list_fragment,
                container, false);
        list = (ListView) view.findViewById(R.id.listPortions);
        data = Model.instance().getAllMeeting();
        adapter = new MyAddapter();
        list.setAdapter(adapter);

        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
        emptyText.setText("empty meeting please add new meeting by click on plus...");
        list.setEmptyView(emptyText);


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final Meeting meeting = data.get(position);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Log.d("log", "Yes button clicked ");
                                Model.instance().deleteMeeting(meeting);
                                Toast.makeText(getActivity().getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();
                                MeetingListFragmentInterface meetingListFragmentInterface = (MeetingListFragmentInterface) getActivity();
                                meetingListFragmentInterface.refreshList();

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                Log.d("log", "No button clicked ");
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("you sure delete this Meeting ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                Log.d("Long", "Long press!!!!!!");


                return true;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Meeting meeting = data.get(position);
                MeetingListFragmentInterface meetingListFragmentInterface = (MeetingListFragmentInterface) getActivity();
                meetingListFragmentInterface.editMeeting(meeting);

                Log.d("samll", "samll press in meeting list!!!!!!");

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
                convertView = inflater.inflate(R.layout.my_meeting_list_row, null);
                Log.d("TAG", "create view:" + position);



            } else {
                Log.d("TAG", "use convert view:" + position);
            }

            Meeting meeting = data.get(position);

            TextView locationn = (TextView) convertView.findViewById(R.id.location);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView time = (TextView) convertView.findViewById(R.id.time);

//            DateEditText dateView = (DateEditText) convertView.findViewById(R.id.startDateEditText);
//            TimeEditText timeView = (TimeEditText) convertView.findViewById(R.id.startTimeEditText);

            Calendar cls = Calendar.getInstance();
            cls.setTimeInMillis(meeting.getDateAndTime());
            date.setText(cls.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Integer.valueOf(cls.get(Calendar.MONTH)) + 1) + "/" + cls.get(Calendar.YEAR));
            if (cls.get(Calendar.MINUTE) > 10)
                time.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + cls.get(Calendar.MINUTE));

            else
                time.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" +"0" + cls.get(Calendar.MINUTE));

//            dateView.setText(cls.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Integer.valueOf(cls.get(Calendar.MONTH)) + 1) + "/" + cls.get(Calendar.YEAR));
//            timeView.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + cls.get(Calendar.MINUTE));
//                dateView.set(cls.get(Calendar.YEAR), Integer.valueOf(cls.get(Calendar.MONTH)), cls.get(Calendar.DAY_OF_MONTH));
//                timeView.set(cls.get(Calendar.HOUR_OF_DAY), cls.get(Calendar.MINUTE));
            locationn.setText(meeting.getLocation());




            return convertView;
        }
    }
}
