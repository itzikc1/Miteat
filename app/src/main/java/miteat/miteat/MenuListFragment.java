package miteat.miteat;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
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

    interface ListFragmentInterface{
        public void addPortions(Meeting meeting);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_list_fragment,
                container, false);
        setHasOptionsMenu(true);
        list = (ListView) view.findViewById(R.id.listPortions);
        data=meeting.getFoodPortionsId();
        adapter = new MyAddapter();
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Long","Long press!!!!!!");

                return false;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int idFromList = data.get(position).getNumberOfFoodPortions();
                Log.d("samll","samll press!!!!!!");

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

            return true;
        }
        if (id == R.id.menu_plus) {
            Log.d("log","plus!!!");

            ListFragmentInterface listFragmentInterface = (ListFragmentInterface) getActivity();
            listFragmentInterface.addPortions(this.meeting);

            return true;
        }

        return super.onOptionsItemSelected(item);
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

            TextView costt = (TextView) convertView.findViewById(R.id.costf);
            FoodPortions st = data.get(position);
            costt.setText(st.getAllergens());

            return convertView;
        }
    }
    public void setMeeting(Meeting meeting){
        this.meeting=meeting;
    }
}
