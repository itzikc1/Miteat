package miteat.miteat;

import android.app.AlertDialog;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        View view = inflater.inflate(R.layout.menu_list_fragment,
                container, false);
        list = (ListView) view.findViewById(R.id.listPortions);
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
//                MeetingListFragmentInterface meetingListFragmentInterface = (MeetingListFragmentInterface) getActivity();
//                meetingListFragmentInterface.editMeeting(meeting);

                Log.d("samll", "samll press!!!!!!");

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
                convertView = inflater.inflate(R.layout.menu_portions_fragment_in_list, null);
                Log.d("TAG", "create view:" + position);

            } else {
                Log.d("TAG", "use convert view:" + position);
            }


            TextView costt = (TextView) convertView.findViewById(R.id.numberOfDish);
            Meeting meeting = data.get(position);
            costt.setText(meeting.getTypeOfFood());

            return convertView;
        }
    }

}
