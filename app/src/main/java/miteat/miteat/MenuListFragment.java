package miteat.miteat;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import miteat.miteat.Model.Entities.FoodPortions;
import miteat.miteat.Model.Entities.Meeting;

/**
 * Created by Itzik on 19/07/2016.
 */
public class MenuListFragment extends Fragment {

    Meeting meeting;
    ListView list;
    List<FoodPortions> data;
    MyAddapter adapter;

    interface ListFragmentInterface {
        public void addPortions(Meeting meeting);
        public void editPortions(FoodPortions f,Meeting meeting);
        public void refresh(Meeting meeting);
        public void backButton(Meeting meeting);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_list_fragment,
                container, false);
        setHasOptionsMenu(true);
        list = (ListView) view.findViewById(R.id.listPortions);
        data = meeting.getFoodPortionsId();
        adapter = new MyAddapter();
        list.setAdapter(adapter);

        TextView emptyText = (TextView)view.findViewById(android.R.id.empty);
        emptyText.setText(R.string.emptyMyMeetingDish);
        list.setEmptyView(emptyText);


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final FoodPortions f = data.get(position);
                // meeting.getFoodPortionsId().remove(data.get(position));
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Log.d("log", "Yes button clicked ");
                                meeting.getFoodPortionsId().remove(f);
                                Toast.makeText(getActivity().getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();
                                ListFragmentInterface listFragmentInterface = (ListFragmentInterface) getActivity();
                                listFragmentInterface.refresh(meeting);

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                Log.d("log", "No button clicked ");
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("you sure delete this Portions ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                Log.d("Long", "Long press!!!!!!");


                return true;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                FoodPortions f = data.get(position);
                ListFragmentInterface listFragmentInterface = (ListFragmentInterface) getActivity();
                listFragmentInterface.editPortions(f,meeting);

                Log.d("samll", "samll press in menu list!!!!!!");

            }
        });

        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_list_portions, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_back) {
            meeting.setFoodPortionsId(data);
            ListFragmentInterface listFragmentInterface = (ListFragmentInterface) getActivity();
            listFragmentInterface.backButton(meeting);
            return true;
        }
        if (id == R.id.menu_plus) {
            Log.d("log", "plus!!!");

            ListFragmentInterface listFragmentInterface = (ListFragmentInterface) getActivity();
            listFragmentInterface.addPortions(this.meeting);

            return true;
        }

        return super.onOptionsItemSelected(item);
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
                    ListFragmentInterface listFragmentInterface = (ListFragmentInterface) getActivity();
                    listFragmentInterface.backButton(meeting);
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
                convertView = inflater.inflate(R.layout.menu_portions_fragment_in_list, null);
                Log.d("TAG", "create view:" + position);

            } else {

                Log.d("TAG", "use convert view:" + position);
            }

            TextView namee = (TextView) convertView.findViewById(R.id.name);
            TextView dish = (TextView) convertView.findViewById(R.id.numberOfDish);
            FoodPortions st = data.get(position);
            if(!st.getName().equals("")) {
                namee.setText(st.getName());
            }
            dish.setText(String.valueOf(st.getDishNumber()));

            return convertView;
        }
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
