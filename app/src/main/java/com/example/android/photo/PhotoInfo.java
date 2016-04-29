package com.example.android.photo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

// Class to store information about one uploaded photo

public class PhotoInfo {

// ********** PHOTO INFORMATION **********
    private String pictireStr; // picture saved as a string
    private Bitmap pictureBit; // picture saved as a bitmap
    private String imageName; // name of the picture
    float rating; // overall rating of the picture
    int givenRatings; // how many ratings were given to the image (how many times rated)
    int posIndexArray; // index in array sorted by ratings

// ********** HELPERS (STRINGS-BITMAPS) **********
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

// ********** CONSTRUCTORS **********
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

// ********** GETTERS & SETTERS **********
    public Bitmap getPictureBit() {return pictureBit;}

    public String getPictureStr() {return pictireStr;}

    public String getPictureName() {return imageName;}

    public float getPictureRating() {return rating;}

// To set new overall rating when new rate was given
    public void setRating(float newRating) {
        rating = rating * givenRatings;
        givenRatings ++;
        rating = rating + newRating;
        rating = rating / givenRatings;
    }

}
