package com.example.android.photo;
import android.graphics.Bitmap;

// Model that keeps the information about all uploaded photos. Should be created only once

public class PhotoModel {

    static PhotoModel mInstance;
    PhotoInfo def; // default information for empty photos
    int maxPhotos = 16; // how many photos can be uploaded
    PhotoInfo[] myPhotos; // stores photos and related information based on upload time
    PhotoInfo[] photosByRating; // stores photos and information based on ratings (high to low)
    boolean uploadOrder = true; // sets to false when required order is based on rating
    int uploadedPhotos = 0; // how many photos were uploaded by user
    int [] indexes; // maps PhotoInfo objects in myPhotos[] to photosByRating;
                    // indexes[] contains indexes of myPhotos[i] in photoByRating
    boolean byRating = false;

    public static PhotoModel getInstance() {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new PhotoModel();
            return mInstance;
        }
    }

// need to have a constructor with no parameters
    public PhotoModel () {
        myPhotos = new PhotoInfo[maxPhotos];
        photosByRating = new PhotoInfo[maxPhotos];
        indexes = new int[maxPhotos];
    }

// since need to have a constructor with no parameters we need to have a method to set defaults
    public void setDefaults (Bitmap defaultPic) {
        if (uploadedPhotos == 0) {
            this.def = new PhotoInfo(defaultPic);
            for (int i = 0; i < maxPhotos; i++) {
                myPhotos[i] = new PhotoInfo(defaultPic);
                photosByRating[i] = new PhotoInfo(defaultPic);
                myPhotos[i].posIndexArray = i;
                indexes[i] = i;
            }
        }
    }

    public boolean getOrder() {
        return uploadOrder;
    }

    public void setOrder(boolean ord) {
        uploadOrder = ord;
    }

// getting called from outside when new photo need to be uploaded
    public void addNewPhoto (PhotoInfo newPhoto) {
            int indexesPos = myPhotos[uploadedPhotos].posIndexArray;
            myPhotos[uploadedPhotos] = newPhoto;
            myPhotos[uploadedPhotos].posIndexArray = indexesPos;
            photosByRating[uploadedPhotos] = newPhoto;
            uploadedPhotos++;
    }

// ********** GETTERS FOR RATING BASED **********

// returns ratings of all uploaded pictures in order based on rating
    public float[] getPictureRatingsRating() {
    for (int i = 0; i < maxPhotos; i++) {photosByRating[i] = myPhotos[indexes[i]];}
    float [] ratings = new float[maxPhotos];
    for (int i = 0; i < maxPhotos; i++) {ratings[i] = photosByRating[i].getPictureRating();}
    return ratings;
}
// returns names of all uploaded pictures in order based on rating
    public String[] getPictureNamesRatings() {
        for (int i = 0; i < maxPhotos; i++) {photosByRating[i] = myPhotos[indexes[i]];}
        String [] names = new String[maxPhotos];
        for (int i = 0; i < maxPhotos; i++) {names[i] = photosByRating[i].getPictureName();}
        return names;
    }

// returns bitmaps of all uploaded pictures in order based on rating
    public Bitmap[] getBitmapsRating() {
        for (int i = 0; i < maxPhotos; i++) {photosByRating[i] = myPhotos[indexes[i]];}
        Bitmap [] images = new Bitmap[maxPhotos];
        for (int i = 0; i < maxPhotos; i++) {images[i] = photosByRating[i].getPictureBit();}
        return images;
    }

// returns downsized bitmaps of all uploaded pictures in order based on rating
    public Bitmap[] getSmallBitmapsRating() {
        for (int i = 0; i < maxPhotos; i++) {photosByRating[i] = myPhotos[indexes[i]];}
        Bitmap [] images = new Bitmap[maxPhotos];
        for (int i = 0; i < maxPhotos; i++) {images[i] = photosByRating[i].getSmallPictureBit();}
        return images;
    }

// ********** GETTERS FOR UPLOAD TIME BASED **********

// returns ratings of all uploaded pictures in order based on upload time
    public float[] getPictureRatingsUpload() {
            float[] ratings = new float[maxPhotos];
            for (int i = 0; i < maxPhotos; i++) {ratings[i] = myPhotos[i].getPictureRating();}
            return ratings;
    }

// returns names of all uploaded pictures in order based on upload time
    public String[] getPictureNamesUpload() {
        String[] names = new String[maxPhotos];
        for (int i = 0; i < maxPhotos; i++) {names[i] = myPhotos[i].getPictureName();}
        return names;
    }

// returns bitmaps of all uploaded pictures in order based on upload time
    public Bitmap[] getBitmapsUpload() {
            Bitmap[] images = new Bitmap[maxPhotos];
            for (int i = 0; i < maxPhotos; i++) {images[i] = myPhotos[i].getPictureBit();}
            return images;
    }

// returns downsized bitmaps of all uploaded pictures in order based on upload time
    public Bitmap[] getSmallBitmapsUpload() {
        Bitmap[] images = new Bitmap[maxPhotos];
        for (int i = 0; i < maxPhotos; i++) {images[i] = myPhotos[i].getSmallPictureBit();}
        return images;
    }

// ********** RESET **********

// resets all the defaults, deletes all the uploaded photos
    public void resetPhotoModel() {
        if (uploadedPhotos != 0) {uploadedPhotos = 0;}
        uploadOrder = true;
        setDefaults(def.getPictureBit());
    }

// ********** RECOUNTING RATING ORDER AND HELPERS **********

// positionOne goes on the place of positionTwo
    private void swapRatingPositions(int positionOne, int positionTwo) {
        int buf = indexes[positionOne];
        indexes[positionOne] = indexes[positionTwo];
        indexes[positionTwo] = buf;
    }

    private void parseLeft(int position) {
        if (position > 0) {
            if (myPhotos[indexes[position]].getPictureRating() > myPhotos[indexes[position - 1]].getPictureRating()) {
                myPhotos[indexes[position]].posIndexArray = position-1;
                myPhotos[indexes[position-1]].posIndexArray = position;
                swapRatingPositions(position, position-1);
                parseLeft(position - 1);
            }
        }
    }

    private void parseRight(int position) {
        if (position < (uploadedPhotos - 1)) {
            if (myPhotos[indexes[position]].getPictureRating() < myPhotos[indexes[position + 1]].getPictureRating()) {
                myPhotos[indexes[position]].posIndexArray = position+1;
                myPhotos[indexes[position+1]].posIndexArray = position;
                swapRatingPositions(position, position+1);
                parseRight(position + 1);
            }
        }
    }

    public void resetRatedPhotos(int position, float oldRating, float newRating) {
        int posInRating = myPhotos[position].posIndexArray;  // position/index in array sorted by rating
        if ((oldRating - newRating) < 0) { // rating grows
            parseLeft(posInRating);
        } else if ((oldRating - newRating) > 0) { //rating goes down
            parseRight(posInRating);
        }
    }

    public void showByRating() {
        byRating = true;
        for (int i = 0; i < maxPhotos; i++) {
            photosByRating[i] = myPhotos[indexes[i]];
        }
    }

    public void showByTime() {
        byRating = false;
    }

}
