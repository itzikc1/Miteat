package miteat.miteat.Model.Cloudinary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Itzik on 07/07/2016.
 */
public class CloudinaryManger {
    Cloudinary cloudinary;
    private final static Cloudinary instance = new Cloudinary();

    public CloudinaryManger() {
        cloudinary = new Cloudinary("cloudinary://351742125169825:y3l-NLJcziTM7xMCUrhQH7jjPL0@dqfossdgc");
    }

    public static Cloudinary getInstance() {
        return instance;
    }

    public void saveImage(final Bitmap imageBitmap, final String imageName) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);
                    byte[] bitmapdata = bos.toByteArray();
                    ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                    String name = imageName.substring(0, imageName.lastIndexOf("."));
                    cloudinary.url().transformation(new Transformation().quality("auto:eco")).imageTag(name);
                    Map res = cloudinary.uploader().upload(bs, ObjectUtils.asMap("public_id", name));
                    //Map res = cloudinary.uploader().upload(bs, ObjectUtils.asMap("public_id", name,"moderation", "webpurify"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
    public Bitmap loadImage(String imageName) {
        URL url = null;
        try {
            url = new URL(cloudinary.url().generate(imageName));
            //   Log.d("TAG", "load image from url" + url);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteImage(final String imageName) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String name = imageName.substring(0, imageName.lastIndexOf("."));
                    Map result = cloudinary.uploader().destroy(name, ObjectUtils.emptyMap());
                    //Log.d("delete",result.get("result").toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();

    }
}
