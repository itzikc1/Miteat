package miteat.miteat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import miteat.miteat.Model.Entities.History;

public class HistoryMeetingAndBookingActivity extends AppCompatActivity implements HistoryListFragment.HistoryListFragmentInterface,HistoryFeedbackFragment.HistoryFeedbackFragmentInterface {

    FragmentManager manager;
    HistoryListFragment historyListFragment;
    HistoryFeedbackFragment historyFeedbackFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_meeting_and_booking);
        historyListFragment = new HistoryListFragment();
        manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.history_list, historyListFragment);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bookin_menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.back_to_main) {

            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void giveFeedBack(History history) {
        historyFeedbackFragment = new HistoryFeedbackFragment();
        historyFeedbackFragment.setHistory(history);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.history_list, historyFeedbackFragment);
        invalidateOptionsMenu();
        transaction.commit();
    }

    @Override
    public void backFromFeedbackPage() {
        historyListFragment = new HistoryListFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.history_list, historyListFragment);
        invalidateOptionsMenu();
        transaction.commit();


    }
}

