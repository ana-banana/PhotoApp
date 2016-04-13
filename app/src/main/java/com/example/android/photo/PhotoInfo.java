package com.example.android.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;

/**
 * Created by andrey on 09/04/2016.
 */
public class PhotoInfo {
    private String pictireStr;
    private Bitmap pictureBit;
    private String imageName;
    float rating;
    int givenRatings;
    int posIndexArray; // index in array sorted by ratings

    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public PhotoInfo () {
        rating = 0;
        givenRatings = 0;
    }

    public PhotoInfo (String str, String imageName) {
        this.imageName = imageName;
        pictireStr = str;
        pictureBit = base64ToBitmap(pictireStr);
        rating = 0;
        givenRatings = 0;
    }

    public PhotoInfo (Bitmap btm, String imageName) {
        this.imageName = imageName;
        pictureBit = btm;
        pictireStr = bitmapToBase64(pictureBit);
        rating = 0;
        givenRatings = 0;
    }

    public PhotoInfo (Bitmap btm) {
        pictureBit = btm;
        pictireStr = bitmapToBase64(pictureBit);
        rating = 0;
        givenRatings = 0;
    }

    public Bitmap getPictureBit() {
        return pictureBit;
    }

    public String getPictureStr() {
        return pictireStr;
    }

    public String getPictureName() {
        return imageName;
    }

    public float getPictureRating() {
        return rating;
    }

    public void setRating(float newRating) {
        rating = rating * givenRatings;
        givenRatings ++;
        rating = rating + newRating;
        rating = rating / givenRatings;
    }

}
