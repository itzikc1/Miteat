package miteat.miteat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;

public class MeetingActivity extends AppCompatActivity {
    private AutoCompleteTextView foodPortions;
    private MultiAutoCompleteTextView completeTextView;
    private  Button save;
    private  Button cancel;
    private EditText numberParticipants;
    private EditText numberOfMoney;
    private DateEditText date;
    private TimeEditText time;
    private  CheckBox  safeCheckBox;
    private  ImageButton menu;
    String[] menuItems={"Android ","java","IOS","SQL","JDBC","Web services"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        foodPortions = (AutoCompleteTextView) findViewById(R.id.foodPortions);
      //  completeTextView=(MultiAutoCompleteTextView)findViewById(R.id.completeTextView);

         save = (Button) findViewById(R.id.save);
         cancel = (Button) findViewById(R.id.cancel);
         numberParticipants = (EditText) findViewById(R.id.numberParticipants);
         numberOfMoney = (EditText) findViewById(R.id.numberOfMoney);
          date = (DateEditText) findViewById(R.id.startDateEditText);
          time = (TimeEditText) findViewById(R.id.startTimeEditText);
          safeCheckBox = (CheckBox) findViewById(R.id.safe);
          menu = (ImageButton) findViewById(R.id.menuButton);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,menuItems);
        foodPortions.setAdapter(adapter);
        foodPortions.setThreshold(1);

//        completeTextView.setAdapter(adapter);
//        completeTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


    }

}
