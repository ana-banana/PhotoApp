package com.example.android.photo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

// Class to store information about one uploaded photo

public class PhotoInfo {

// ********** PHOTO INFORMATION **********
    private Bitmap pictureBit; // picture saved as a bitmap
    private String imageName; // name of the picture
    float rating; // overall rating of the picture
    int givenRatings; // how many ratings were given to the image (how many times rated)
    int posIndexArray; // index in array sorted by ratings

// ********** CONSTRUCTORS **********
    public PhotoInfo () {
        rating = 0;
        givenRatings = 0;
    }

    public PhotoInfo (Bitmap btm, String imageName) {
        this.imageName = imageName;
        pictureBit = btm;
        rating = 0;
        givenRatings = 0;
    }

    public PhotoInfo (Bitmap btm) {
        pictureBit = btm;
        rating = 0;
        givenRatings = 0;
    }

// ********** GETTERS & SETTERS **********
    public Bitmap getPictureBit() {return pictureBit;}

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
