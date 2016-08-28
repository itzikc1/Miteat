package miteat.miteat;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import miteat.miteat.Model.Entities.FoodPortions;
import miteat.miteat.Model.Entities.Meeting;
import miteat.miteat.Model.ModelCloudinary;

/**
 * Created by Itzik on 19/07/2016.
 */
public class MenuPortionsFragment extends Fragment {

    private Meeting meeting;
    private FoodPortions editPortions = null;
    private MultiAutoCompleteTextView multiAutoComplete;
    private Button save;
    private Button cancel;
    private EditText numberId;
    private EditText costOfMoney;
    private EditText name;

    private ImageSwitcher imageSwitcher;
    private ArrayList<String> image = new ArrayList<String>();
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    int PLACE_PICKER_REQUEST = 1;

    int[] resources = {
            R.drawable.plus,
            R.drawable.map,
            R.drawable.chef
    };

    interface MenuFragmentInterface {

        public void saveInterface(Meeting meeting);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        final View view = inflater.inflate(R.layout.menu_portions_fragment,
                container, false);
        multiAutoComplete = (MultiAutoCompleteTextView) view.findViewById(R.id.allergensType);
        String[] countries = getResources().getStringArray(R.array.allergens_type);

        imageSwitcher = (ImageSwitcher) view.findViewById(R.id.imageSwitcher);

        Button takePic = (Button) view.findViewById(R.id.makePic);
        Button addpic = (Button) view.findViewById(R.id.getPic);
        save = (Button) view.findViewById(R.id.save);
        cancel = (Button) view.findViewById(R.id.cancel);

        TextView num = (TextView) view.findViewById(R.id.portionsNumber);
        TextView costView = (TextView) view.findViewById(R.id.cost);
        TextView nameDefault = (TextView) view.findViewById(R.id.name);

        //TextView nameView = (TextView) view.findViewById(R.id.name);
        if(editPortions==null){

        int numMeeting = 0;
        if (this.meeting.getFoodPortionsId().isEmpty()) {
            numMeeting++;
        } else {
            numMeeting = this.meeting.getFoodPortionsId().size() + 1;

        }
        num.setText(String.valueOf(numMeeting));
        costView.setText("0");
            nameDefault.setText("");
        }
        else{
           num.setText(String.valueOf(editPortions.getNumberOfFoodPortions()));
            costView.setText(String.valueOf(editPortions.getCost()));
            nameDefault.setText(editPortions.getName().toString());
            multiAutoComplete.setText(editPortions.getAllergens().toString());
        }
        // imageSwitcher.setImageResource(R.drawable.map);


        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, countries);
        multiAutoComplete.setAdapter(adapter);
        multiAutoComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editPortions!=null){
                    meeting.getFoodPortionsId().remove(editPortions);
                }

                numberId = (EditText) view.findViewById(R.id.portionsNumber);
                costOfMoney = (EditText) view.findViewById(R.id.cost);
                name = (EditText) view.findViewById(R.id.name);
                String allergens = multiAutoComplete.getText().toString();
                int numberIdText = Integer.parseInt(numberId.getText().toString());
                int money = Integer.parseInt(costOfMoney.getText().toString());
                FoodPortions foodPortions = new FoodPortions(numberIdText,name.getText().toString(), image, numberIdText, money, allergens);
                addFoodPortions(foodPortions);
                MenuFragmentInterface menuFragmentInterface = (MenuFragmentInterface) getActivity();
                menuFragmentInterface.saveInterface(meeting);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFragmentInterface menuFragmentInterface = (MenuFragmentInterface) getActivity();
                menuFragmentInterface.saveInterface(meeting);
            }
        });
        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);


            }
        });
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        return view;
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


    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public  void edit(FoodPortions f){
        this.editPortions=f;
    }

    public void addFoodPortions(FoodPortions foodPortions) {
        this.meeting.addFoodPortionsId(foodPortions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            String path = null;
            if (requestCode == REQUEST_CAMERA) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                String pic = System.currentTimeMillis() + ".jpg";
                Log.d("name-------------- ", pic);
                image.add(pic);
                ModelCloudinary.getInstance().saveImage(imageBitmap, pic);

            } else if (requestCode == SELECT_FILE) {

                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(getActivity(), selectedImageUri, projection, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                String filename = selectedImagePath.substring(selectedImagePath.lastIndexOf("/") + 1);
                Log.d("name-------------- ", filename);
                image.add(filename);
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImageUri));
                    ModelCloudinary.getInstance().saveImageOncloudinary(bitmap, filename);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}