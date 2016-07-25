package miteat.miteat;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;

import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.Model;

/**
 * Created by Itzik on 19/07/2016.
 */
public class MainMeetingFragment extends Fragment {


    private MultiAutoCompleteTextView multiAutoComplete;
    private Button save;
    private Button cancel;
    private EditText numberParticipants;
    private EditText numberOfMoney;
    private DateEditText date;
    private TimeEditText time;
    private CheckBox safeCheckBox;
    private ImageButton menu;
    private ImageButton location;
    private String address;

    int PLACE_PICKER_REQUEST = 1;

    interface MeetingFragmentInterface {
        public void menuStart(Meeting meeting);

        public void saveOrCencel();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.main_meeting_fragment, container, false);
        multiAutoComplete = (MultiAutoCompleteTextView) view.findViewById(R.id.foodPortions);
        String[] countries = getResources().getStringArray(R.array.type);
        save = (Button) view.findViewById(R.id.save);
        cancel = (Button) view.findViewById(R.id.cancel);
        numberParticipants = (EditText) view.findViewById(R.id.numberParticipants);
        numberOfMoney = (EditText) view.findViewById(R.id.numberOfMoney);
        date = (DateEditText) view.findViewById(R.id.startDateEditText);
        time = (TimeEditText) view.findViewById(R.id.startTimeEditText);
        safeCheckBox = (CheckBox) view.findViewById(R.id.safe);
        menu = (ImageButton) view.findViewById(R.id.menuButton);
        location = (ImageButton) view.findViewById(R.id.location);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, countries);

        multiAutoComplete.setAdapter(adapter);
        multiAutoComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long startMillis = 0;
                String typeFood = multiAutoComplete.getText().toString();
                int numberPlace = Integer.parseInt(numberParticipants.getText().toString());
                int money = Integer.parseInt(numberOfMoney.getText().toString());
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(date.getYear(), date.getMonth(), date.getDay(), time.getHour(), time.getMinutes());
                startMillis = beginTime.getTimeInMillis();
                Boolean bool = safeCheckBox.isChecked();
                Meeting meeting = new Meeting(1, numberPlace, typeFood, money, startMillis, address, bool);
                Model.instance().addMeeting(meeting);
                MeetingFragmentInterface meetingFragmentInterface = (MeetingFragmentInterface) getActivity();
                meetingFragmentInterface.saveOrCencel();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeetingFragmentInterface meetingFragmentInterface = (MeetingFragmentInterface) getActivity();
                meetingFragmentInterface.saveOrCencel();

            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long startMillis = 0;
                String typeFood = multiAutoComplete.getText().toString();
                int numberPlace = Integer.parseInt(numberParticipants.getText().toString());
                int money = Integer.parseInt(numberOfMoney.getText().toString());
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(date.getYear(), date.getMonth(), date.getDay(), time.getHour(), time.getMinutes());
                startMillis = beginTime.getTimeInMillis();
                Boolean bool = safeCheckBox.isChecked();
                Meeting meeting = new Meeting(1, numberPlace, typeFood, money, startMillis, address, bool);
                MeetingFragmentInterface meetingFragmentInterface = (MeetingFragmentInterface) getActivity();
                meetingFragmentInterface.menuStart(meeting);


            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker();
            }
        });
        return view;
    }


    public void PlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent = builder.build(getActivity().getApplicationContext());
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Place place = PlacePicker.getPlace(data, getActivity());
        String a = String.format("Place: %s", place.getAddress());
        address = a;
        Log.d("address", a);
    }
}
