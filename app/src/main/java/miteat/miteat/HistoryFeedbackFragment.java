package miteat.miteat;

import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
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

import java.util.Calendar;

import miteat.miteat.Model.Entities.Feedback;
import miteat.miteat.Model.Entities.History;
import miteat.miteat.Model.Entities.UserDetails;
import miteat.miteat.Model.Model;

/**
 * Created by Itzik on 13/10/2016.
 */
public class HistoryFeedbackFragment extends Fragment {
    private History history;
    private TextView userName;
    private TextView address;
    private Button giveFeedBack;
    private RatingBar cleaningStarView;
    private RatingBar serviceStarView;
    private RatingBar atmosphereStarView;
    private RatingBar valueStarView;
    private TextView dateView;
    private TextView startTimeView;
    private TextView endTimeView;
    private EditText textFeedbackView;


    interface HistoryFeedbackFragmentInterface {
        public void backFromFeedbackPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_feedback_fragment,
                container, false);

        userName = (TextView) view.findViewById(R.id.userName);
        address = (TextView) view.findViewById(R.id.location);
        giveFeedBack = (Button) view.findViewById(R.id.giveFeedBack);
        cleaningStarView = (RatingBar) view.findViewById(R.id.cleaningStar);
        serviceStarView = (RatingBar) view.findViewById(R.id.serviceStar);
        atmosphereStarView = (RatingBar) view.findViewById(R.id.atmosphereStar);
        valueStarView = (RatingBar) view.findViewById(R.id.valueStar);
        dateView = (TextView) view.findViewById(R.id.date);
        startTimeView = (TextView) view.findViewById(R.id.startTime);
        endTimeView = (TextView) view.findViewById(R.id.endTime);
        textFeedbackView = (EditText) view.findViewById(R.id.textFeedBack);

        address.setText(history.getBooking().getMeeting().getLocation());

        Calendar startCls = Calendar.getInstance();
        Calendar endCls = Calendar.getInstance();
        startCls.setTimeInMillis(history.getBooking().getMeeting().getDateAndTime());
        endCls.setTimeInMillis(history.getBooking().getMeeting().getDateAndEndTime());

        dateView.setText(startCls.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Integer.valueOf(startCls.get(Calendar.MONTH)) + 1) + "/" + startCls.get(Calendar.YEAR));

        if (startCls.get(Calendar.MINUTE) > 10) {
            startTimeView.setText(startCls.get(Calendar.HOUR_OF_DAY) + ":" + startCls.get(Calendar.MINUTE));
            endTimeView.setText(endCls.get(Calendar.HOUR_OF_DAY) + ":" + endCls.get(Calendar.MINUTE));
        } else {
            startTimeView.setText(startCls.get(Calendar.HOUR_OF_DAY) + ":" + "0" + startCls.get(Calendar.MINUTE));
            endTimeView.setText(endCls.get(Calendar.HOUR_OF_DAY) + ":" + endCls.get(Calendar.MINUTE));
        }


        if (history.getFeedback() != null) {
            cleaningStarView.setRating(history.getFeedback().getCleaningStar());
            serviceStarView.setRating(history.getFeedback().getServiceStar());
            atmosphereStarView.setRating(history.getFeedback().getAtmosphereStar());
            valueStarView.setRating(history.getFeedback().getValueStar());
            userName.setText(history.getFeedback().getFromUserId());
            textFeedbackView.setText(history.getFeedback().getFeedBackText());
            giveFeedBack.setText("Back");

        } else {
            if (history.getBooking().getMeetingOrBooking() == 1) {
                userName.setText(history.getHostId());
            } else {
                userName.setText(history.getBooking().getUserIdOfBooking());
            }
        }


        giveFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (history.getFeedback() == null) {

                    float cleaningStar = cleaningStarView.getRating();
                    float serviceStar = serviceStarView.getRating();
                    float atmosphereStar = atmosphereStarView.getRating();
                    float valueStar = valueStarView.getRating();
                    String textFeedback = textFeedbackView.getText().toString();
                    Feedback feedback = new Feedback(1, history.getBooking().getMeeting().getId(), history.getBooking().getId(), Model.instance().getUserDetails().getUserName(), history.getBooking().getUserIdOfBooking(), textFeedback, null, cleaningStar, serviceStar, atmosphereStar, valueStar, true,history.getBooking().getMeeting().getDateAndTime());
                    Model.instance().giveFeedBack(feedback);
                }
                HistoryFeedbackFragmentInterface historyFeedbackFragmentInterface = (HistoryFeedbackFragmentInterface) getActivity();
                historyFeedbackFragmentInterface.backFromFeedbackPage();
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


                    return true;
                }
                return false;
            }
        });
    }

    public void setHistory(History history) {
        this.history = history;
    }

}
