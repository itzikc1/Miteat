package miteat.miteat.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import miteat.miteat.Model.Cloudinary.CloudinaryManger;
import miteat.miteat.MyApplication;

/**
 * Created by Itzik on 07/07/2016.
 */
public class ModelCloudinary {
    private final static ModelCloudinary instance = new ModelCloudinary();
    CloudinaryManger cloudinaryManger;
    Context context;

    private ModelCloudinary() {
        cloudinaryManger = new CloudinaryManger();
        context = MyApplication.getAppContext();

    }

    public static ModelCloudinary getInstance() {
        return instance;
    }

    public void saveImage(final Bitmap imageBitmap, final String imageName) {
        saveImageToFile(imageBitmap, imageName); // synchronously save image locally
        Thread d = new Thread(new Runnable() {  // asynchronously save image to parse
            @Override
            public void run() {
                cloudinaryManger.saveImage(imageBitmap, imageName);
            }
        });
        d.start();

    }

    public void saveImageOncloudinary(final Bitmap imageBitmap, final String imageName) {
        Thread d = new Thread(new Runnable() {  // asynchronously save image to parse
            @Override
            public void run() {
                cloudinaryManger.saveImage(imageBitmap, imageName);
            }
        });
        d.start();

    }

    //convert Bitmap to image and save with name
    private void saveImageToFile(Bitmap imageBitmap, String imageFileName) {
        FileOutputStream fos;
        OutputStream out = null;
        try {
            //File dir = context.getExternalFilesDir(null);
            File dir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(dir, imageFileName);
            imageFile.createNewFile();

            out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            //add the picture to the gallery so we dont need to manage the cache size
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(imageFile);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
            Log.d("tag", "add image to cache: " + imageFileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface LoadImageListener {
        public void onResult(Bitmap imageBmp);
    }

    //load Image from storage or cloudinary
    public void loadImage(final String imageName, final LoadImageListener listener) {
        AsyncTask<String, String, Bitmap> task = new AsyncTask<String, String, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bmp = loadImageFromFile(imageName);              //first try to fin the image on the device
                if (bmp == null) {                                      //if image not found - try downloading it from parse
                    bmp = cloudinaryManger.loadImage(imageName);
//                    if (bmp != null)
//                        saveImageToFile(bmp, imageName);    //save the image locally for next time
                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                listener.onResult(result);
            }
        };
        task.execute();
    }


    //get Image from storage
    private Bitmap loadImageFromFile(String imageFileName) {
        String str = null;
        Bitmap bitmap = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dir, imageFileName);

            //File dir = context.getExternalFilesDir(null);
            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
          //  Log.d("tag", "got image from cache: " + imageFileName);

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void deleteImageFromCloudinary(final ArrayList<String> imageName) {
        Thread d = new Thread(new Runnable() {  // asynchronously save image to parse
            @Override
            public void run() {
                for (int i = 0; i < imageName.size(); i++) {
                    cloudinaryManger.deleteImage(imageName.get(i));
                }

            }
        });
        d.start();
    }

}
