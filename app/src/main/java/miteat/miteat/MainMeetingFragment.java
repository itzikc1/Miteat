package miteat.miteat;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.LinkedList;

import miteat.miteat.Model.Entities.FoodPortions;
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
    private TimeEditText endTime;
    private CheckBox safeCheckBox;
    private ImageButton menu;
    private ImageButton location;
    private String address;
    private Meeting meeting;
    private Double lat;
    private Double lon;
    private Spinner spinner;
    private int numberOfBooked;
    ArrayAdapter<CharSequence> dataAdapterSpinner;
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
        TextView numberParticipantsView = (TextView) view.findViewById(R.id.numberParticipants);
        TextView numberOfMoneyView = (TextView) view.findViewById(R.id.numberOfMoney);
        DateEditText dateView = (DateEditText) view.findViewById(R.id.startDateEditText);
        TimeEditText timeView = (TimeEditText) view.findViewById(R.id.startTimeEditText);
        TimeEditText endTimeView = (TimeEditText) view.findViewById(R.id.endTimeEditText);
        Spinner spinnerView = (Spinner) view.findViewById(R.id.spinner);
        numberParticipants = (EditText) view.findViewById(R.id.numberParticipants);
        numberOfMoney = (EditText) view.findViewById(R.id.numberOfMoney);
        date = (DateEditText) view.findViewById(R.id.startDateEditText);
        time = (TimeEditText) view.findViewById(R.id.startTimeEditText);
        endTime = (TimeEditText) view.findViewById(R.id.endTimeEditText);
        safeCheckBox = (CheckBox) view.findViewById(R.id.safe);
        menu = (ImageButton) view.findViewById(R.id.menuButton);
        location = (ImageButton) view.findViewById(R.id.location);
        spinner = (Spinner) view.findViewById(R.id.spinner);

        dataAdapterSpinner = ArrayAdapter.createFromResource(getActivity(), R.array.takeAwayOption, android.R.layout.simple_spinner_item);
        dataAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapterSpinner);

        if (meeting != null) {
            numberParticipantsView.setText(String.valueOf(meeting.getNumberOfPartner()));
            numberOfMoneyView.setText(String.valueOf(meeting.getMoney()));
            lat = meeting.getLatLocation();
            lon = meeting.getLonLocation();
            address = meeting.getLocation();
            Calendar cls = Calendar.getInstance();
            Calendar endCls = Calendar.getInstance();
            cls.setTimeInMillis(meeting.getDateAndTime());
            endCls.setTimeInMillis(meeting.getDateAndEndTime());
            dateView.setText(cls.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Integer.valueOf(cls.get(Calendar.MONTH)) + 1) + "/" + cls.get(Calendar.YEAR));
            endTimeView.setText(endCls.get(Calendar.HOUR_OF_DAY) + ":" + endCls.get(Calendar.MINUTE));
            timeView.setText(cls.get(Calendar.HOUR_OF_DAY) + ":" + cls.get(Calendar.MINUTE));
            dateView.set(cls.get(Calendar.YEAR), Integer.valueOf(cls.get(Calendar.MONTH)), cls.get(Calendar.DAY_OF_MONTH));
            timeView.set(cls.get(Calendar.HOUR_OF_DAY), cls.get(Calendar.MINUTE));
            endTimeView.set(endCls.get(Calendar.HOUR_OF_DAY), endCls.get(Calendar.MINUTE));
            spinnerView.setSelection(meeting.getTakeAway());
            multiAutoComplete.setText(meeting.getTypeOfFood());

            safeCheckBox.setChecked(meeting.getInsurance());
        } else {
            numberParticipantsView.setText("1");
            numberOfMoneyView.setText("0");
            multiAutoComplete.setText("");
            safeCheckBox.setChecked(false);
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, countries);

        multiAutoComplete.setAdapter(adapter);
        multiAutoComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long startMillis = new Long(0);
                Long endMillis = new Long(0);
                String typeFood = multiAutoComplete.getText().toString();
                int numberPlace = Integer.parseInt(numberParticipants.getText().toString());
                int money = Integer.parseInt(numberOfMoney.getText().toString());
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(date.getYear(), date.getMonth(), date.getDay(), time.getHour(), time.getMinutes());
                startMillis = beginTime.getTimeInMillis();
                Calendar enddTime = Calendar.getInstance();
                enddTime.set(date.getYear(), date.getMonth(), date.getDay(), endTime.getHour(), endTime.getMinutes());
                endMillis = enddTime.getTimeInMillis();
                Boolean bool = safeCheckBox.isChecked();
                int takeAwaySelection = spinner.getSelectedItemPosition();
                Meeting newMeeting = new Meeting(-1, Model.instance().getUserDetails().getUserName(), numberPlace, typeFood, money, startMillis, endMillis, address, lat, lon, bool, takeAwaySelection);

                if (address == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter a Address", Toast.LENGTH_LONG).show();
                } else if (numberPlace < 1) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter number bigger than 0", Toast.LENGTH_LONG).show();
                }
                else if (meeting != null && numberOfBooked!=-1) {
                    if((meeting.getNumberOfPartner()-numberOfBooked)<numberPlace){
                        Toast.makeText(getActivity().getApplicationContext(), "minimum for partners: "+(meeting.getNumberOfPartner()-numberOfBooked), Toast.LENGTH_LONG).show();
                    }
                }
                else if (endMillis.compareTo(startMillis) == -1) {
                    Toast.makeText(getActivity().getApplicationContext(), "The start time before  end time", Toast.LENGTH_LONG).show();
                } else {

                    if (meeting == null) {
                        newMeeting.setFoodPortionsId(new LinkedList<FoodPortions>());
                        Model.instance().addMeeting(newMeeting);
                    } else {

                        newMeeting.setFoodPortionsId(meeting.getFoodPortionsId());
                        Log.d("delete meeting ", String.valueOf(meeting.getId()));
                        newMeeting.setId(meeting.getId());
                        Model.instance().deleteMeeting(meeting);
                        Model.instance().addMeeting(newMeeting);
                    }
                    MeetingFragmentInterface meetingFragmentInterface = (MeetingFragmentInterface) getActivity();
                    meetingFragmentInterface.saveOrCencel();
                }
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
                long endMillis = 0;
                long startMillis = 0;
                String typeFood = multiAutoComplete.getText().toString();
                int numberPlace = Integer.parseInt(numberParticipants.getText().toString());
                int money = Integer.parseInt(numberOfMoney.getText().toString());
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(date.getYear(), date.getMonth(), date.getDay(), time.getHour(), time.getMinutes());
                startMillis = beginTime.getTimeInMillis();
                Calendar enddTime = Calendar.getInstance();
                enddTime.set(date.getYear(), date.getMonth(), date.getDay(), endTime.getHour(), endTime.getMinutes());
                endMillis = enddTime.getTimeInMillis();
                Boolean bool = safeCheckBox.isChecked();
                int takeAwaySelection = spinner.getSelectedItemPosition();
                Meeting newMeeting = new Meeting(-1, Model.instance().getUserDetails().getUserName(), numberPlace, typeFood, money, startMillis, endMillis, address, lat, lon, bool, takeAwaySelection);
                newMeeting.setLatLocation(lat);
                newMeeting.setLonLocation(lon);
                if (meeting == null) {
                    newMeeting.setFoodPortionsId(new LinkedList<FoodPortions>());
                } else {
                    newMeeting.setFoodPortionsId(meeting.getFoodPortionsId());
                    newMeeting.setId(meeting.getId());
                }
                MeetingFragmentInterface meetingFragmentInterface = (MeetingFragmentInterface) getActivity();
                meetingFragmentInterface.menuStart(newMeeting);
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

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    MeetingFragmentInterface meetingFragmentInterface = (MeetingFragmentInterface) getActivity();
                    meetingFragmentInterface.saveOrCencel();
                    return true;
                }
                return false;
            }
        });
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    public void PlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent = builder.build(getActivity());

          //  intent = builder.build(getActivity().getApplicationContext());
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Place place = PlacePicker.getPlace(data, getActivity());
            LatLng latLng = place.getLatLng();
            lat = latLng.latitude;
            lon = latLng.longitude;

            String a = String.format("Place: %s", place.getAddress());
            address = a;
        //    Log.d("address", a);
        }
    }

    public void setMenuList(Meeting meeting) {
        this.meeting = meeting;
        numberOfBooked=-1;
        checkIfEdit();
    }

    public void checkIfEdit() {
        Model.instance().getMeetingAsync(meeting, new Model.GetMeetingInterface() {
            @Override
            public void onResult(Meeting meetings) {
                if (meetings != null) {
                    numberOfBooked = meetings.getNumberOfPartner();
//                    if (meetings.getNumberOfPartner() < meeting.getNumberOfPartner()) {
//                        save.setVisibility(View.GONE);
//                    }
                }
            }

            @Override
            public void onCancel() {
            }
        });
    }


}
