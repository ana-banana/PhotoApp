package com.example.android.photo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

// Class to store information about one uploaded photo

public class PhotoInfo {

// ********** PHOTO INFORMATION **********
    private Bitmap pictureBit; // picture saved as a bitmap
    private Bitmap smallPictureBit; // downsized bitmap for the picture
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
        pictureBit = scaleBitmap(btm, 500, 500);;
        rating = 0;
        givenRatings = 0;
        smallPictureBit = scaleBitmap(btm, 380, 380);
    }

    public PhotoInfo (Bitmap btm) {
        pictureBit = scaleBitmap(btm, 500, 500);
        rating = 0;
        givenRatings = 0;
        smallPictureBit = scaleBitmap(btm, 380, 380);
    }

// ********** GETTERS & SETTERS **********
    public Bitmap getPictureBit() {return pictureBit;}

    public Bitmap getSmallPictureBit() {return smallPictureBit;}

    public String getPictureName() {return imageName;}

    public float getPictureRating() {return rating;}

// To set new overall rating when new rate was given
    public void setRating(float newRating) {
        rating = rating * givenRatings;
        givenRatings ++;
        rating = rating + newRating;
        rating = rating / givenRatings;
    }

    private Bitmap scaleBitmap(Bitmap bm, int maxHeight, int maxWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();


        if (width > height) {
            // landscape
            float ratio = (float) width / maxWidth;
            width = maxWidth;
            height = (int)(height / ratio);
        } else if (height > width) {
            // portrait
            float ratio = (float) height / maxHeight;
            height = maxHeight;
            width = (int)(width / ratio);
        } else {
            // square
            height = maxHeight;
            width = maxWidth;
        }

        bm = Bitmap.createScaledBitmap(bm, width, height, true);
        return bm;
    }

}
