package miteat.miteat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<String> image;
    private ArrayList<String> deleteImage;
    private LinearLayout layout;
    private LinearLayout.LayoutParams layoutParams;
    private ProgressBar mProgress;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    int PLACE_PICKER_REQUEST = 1;

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

        layout = (LinearLayout) view.findViewById(R.id.image_container);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button takePic = (Button) view.findViewById(R.id.makePic);
        Button addpic = (Button) view.findViewById(R.id.getPic);
        save = (Button) view.findViewById(R.id.save);
        cancel = (Button) view.findViewById(R.id.cancel);

        mProgress = (ProgressBar) view.findViewById(R.id.rowImageProgressBar);

        TextView num = (TextView) view.findViewById(R.id.portionsNumber);
        TextView costView = (TextView) view.findViewById(R.id.cost);
        TextView nameDefault = (TextView) view.findViewById(R.id.name);
        image = new ArrayList<String>();
        deleteImage = new ArrayList<String>();
        if (editPortions == null) {
            int numMeeting = 0;
            num.setText(String.valueOf(numMeeting));
            costView.setText("0");
            nameDefault.setText("");
            multiAutoComplete.setText("");
        } else {
            num.setText(String.valueOf(editPortions.getNumberOfFoodPortions()));
            costView.setText(String.valueOf(editPortions.getCost()));
            nameDefault.setText(editPortions.getName().toString());
            multiAutoComplete.setText(editPortions.getAllergens().toString());

            for (int i = 0; i < editPortions.getImages().size(); i++) {
                image.add(editPortions.getImages().get(i));
            }
            //   image = editPortions.getImages();
            // newImage=image;

           refreshPic();
        }


        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, countries);
        multiAutoComplete.setAdapter(adapter);
        multiAutoComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // int id = meeting.getFoodPortionsId().size() + 1;
                if (editPortions != null) {
                    //   id = editPortions.getId();
                    meeting.getFoodPortionsId().remove(editPortions);
                }
                numberId = (EditText) view.findViewById(R.id.portionsNumber);
                costOfMoney = (EditText) view.findViewById(R.id.cost);
                name = (EditText) view.findViewById(R.id.name);
                String allergens = multiAutoComplete.getText().toString();
                int numberIdText = Integer.parseInt(numberId.getText().toString());
                int money = Integer.parseInt(costOfMoney.getText().toString());
                FoodPortions foodPortions = new FoodPortions(numberIdText, name.getText().toString(),numberIdText, image, numberIdText, money, allergens);
                addFoodPortions(foodPortions);
                if (deleteImage.size() > 0) {
                    ModelCloudinary.getInstance().deleteImageFromCloudinary(deleteImage);
                }
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


    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    MenuFragmentInterface menuFragmentInterface = (MenuFragmentInterface) getActivity();
                    menuFragmentInterface.saveInterface(meeting);
                    return true;
                }
                return false;
            }
        });
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public void edit(FoodPortions f) {
        this.editPortions = f;
    }

    public void addFoodPortions(FoodPortions foodPortions) {
        foodPortions.setMeetingId(this.meeting.getId());
        this.meeting.addFoodPortionsId(foodPortions);
    }

    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
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
                //  Log.d("name-------------- ", pic);
                image.add(pic);
                ModelCloudinary.getInstance().saveImage(imageBitmap, pic);
                refreshPic();

            } else if (requestCode == SELECT_FILE) {

                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(getActivity(), selectedImageUri, projection, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                String filename = selectedImagePath.substring(selectedImagePath.lastIndexOf("/") + 1);
                //Log.d("name-------------- ", filename);
                image.add(filename);
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImageUri));
                    ModelCloudinary.getInstance().saveImageOncloudinary(bitmap, filename);
                    refreshPic();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void refreshPic() {
        layout.removeAllViews();//clean all pic before

        //mProgress.setVisibility(layout.getVisibility());

        mProgress.setVisibility(layout.getVisibility());
        for (int i = 0; i < image.size(); i++) {
            layoutParams.setMargins(1, 1, 1, 1);
            layoutParams.gravity = Gravity.CENTER;

            final ImageView imageView = new ImageView(getActivity());
            final ImageView imageViewBigger = new ImageView(getActivity());
            final String srt = image.get(i);
            ModelCloudinary.getInstance().loadImage(image.get(i), new ModelCloudinary.LoadImageListener() {
                @Override
                public void onResult(Bitmap imageBmp) {

                    if(imageBmp==null){
                        mProgress.setVisibility(layout.getVisibility());
                        refreshPic();
                       // Log.d("null image","null image load");
                    }
                    else{
                        imageViewBigger.setImageBitmap(imageBmp);
                    if (imageBmp.getWidth() > 1080 || imageBmp.getHeight() > 720) {
                        imageBmp = Bitmap.createScaledBitmap(imageBmp, 800, 1080, true);
                    }
                        mProgress.setVisibility(layout.GONE);
                    imageView.setImageBitmap(imageBmp);

                    }
                }
            });

            imageView.setLayoutParams(layoutParams);
           // imageViewBigger.setLayoutParams(layoutParams);
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   // layout.setBackground(imageView.getDrawable());
                  //  imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    int c = image.size();
                                    if(image.size()==1){
                                        image.clear();
                                    }
                                    else{
                                        image.remove(srt);
                                    }

                                    deleteImage.add(srt);
                                    Toast.makeText(getActivity().getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();
                                    refreshPic();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    Log.d("log", "No button clicked ");
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("you sure delete this picture ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                    return true;
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setPositiveButton("Get Pro", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    }).setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    });
                    final AlertDialog dialog = builder.create();
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.full_screen_image, null);
                    dialog.setView(dialogLayout);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface d) {
                             ImageView imagee = (ImageView) dialog.findViewById(R.id.fullImage);
                            imagee.setImageDrawable(imageView.getDrawable());
                        }
                    });
                    dialog.show();
                }
            });
            layout.addView(imageView);
        }


    }

}
