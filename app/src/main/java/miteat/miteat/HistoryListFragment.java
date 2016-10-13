package miteat.miteat;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import miteat.miteat.Model.Entities.History;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Model;

/**
 * Created by Itzik on 13/10/2016.
 */
public class HistoryListFragment extends Fragment {
    ListView list;
    List<History> data;
    MyAddapter adapter;

    interface HistoryListFragmentInterface {
        public void giveFeedBack(History history);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_list_fragment,
                container, false);
        list = (ListView) view.findViewById(R.id.historyList);
        data = Model.instance().getAllHistoryBookingAndMeeting();
        adapter = new MyAddapter();
        list.setAdapter(adapter);

        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
        emptyText.setText(R.string.emptyHistory);
        list.setEmptyView(emptyText);

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

                    return true;
                }
                return false;
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
                convertView = inflater.inflate(R.layout.history_row, null);

            } else {

                Log.d("TAG", "use convert view:" + position);
            }

            final History history = data.get(position);
            TextView userName = (TextView) convertView.findViewById(R.id.userName);
            TextView address = (TextView) convertView.findViewById(R.id.location);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView startTime = (TextView) convertView.findViewById(R.id.startTime);
            TextView endTime = (TextView) convertView.findViewById(R.id.endTime);
            final Button giveFeedBack = (Button) convertView.findViewById(R.id.giveFeedBackOrShowFeedback);

            address.setText(String.valueOf( history.getBooking().getMeeting().getLocation()));
            Calendar startCls = Calendar.getInstance();
            Calendar endCls = Calendar.getInstance();
            startCls.setTimeInMillis(history.getBooking().getMeeting().getDateAndTime());
            endCls.setTimeInMillis(history.getBooking().getMeeting().getDateAndEndTime());

            date.setText(startCls.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Integer.valueOf(startCls.get(Calendar.MONTH)) + 1) + "/" + startCls.get(Calendar.YEAR));

            if (startCls.get(Calendar.MINUTE) > 10) {
                startTime.setText(startCls.get(Calendar.HOUR_OF_DAY) + ":" + startCls.get(Calendar.MINUTE));
                endTime.setText(endCls.get(Calendar.HOUR_OF_DAY) + ":" + endCls.get(Calendar.MINUTE));
            } else {
                startTime.setText(startCls.get(Calendar.HOUR_OF_DAY) + ":" + "0" + startCls.get(Calendar.MINUTE));
                endTime.setText(endCls.get(Calendar.HOUR_OF_DAY) + ":" + endCls.get(Calendar.MINUTE));
            }

            if (history.getBooking().getMeetingOrBooking() == 1)//booking
            {
                convertView.setBackgroundColor(0x6E00C8FF);
                userName.setText(history.getBooking().getMeeting().getUserId()+"1");
                if(history.getFeedback()!=null){
                    giveFeedBack.setText("Feedback details");
                }

            } else {
                userName.setText(history.getBooking().getUserIdOfBooking());
            }

            giveFeedBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                        HistoryListFragmentInterface historyListFragmentInterface   = (HistoryListFragmentInterface) getActivity();
                        historyListFragmentInterface.giveFeedBack(history);


                }
            });

            return convertView;
        }
    }
}
