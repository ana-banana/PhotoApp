package com.example.android.photo;
import android.graphics.Bitmap;
import android.util.Log;

public class PhotoModel {

    final static String TAG = "TEST";

    static PhotoModel mInstance;
    PhotoInfo def;
    int maxPhotos = 8;

    PhotoInfo[] myPhotos;
    PhotoInfo[] photosByRating;

    int uploadedPhotos = 0;

    int [] indexes; // maps PhotoInfo objects in myPhotos[] to photosByRating; indexes[] contains
                    // indexes of myPhotos[i] in photoByRating

    boolean byRating = false;

    public static PhotoModel getInstance() {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new PhotoModel();
            return mInstance;
        }
    }

    public PhotoModel () {
        myPhotos = new PhotoInfo[maxPhotos];
        photosByRating = new PhotoInfo[maxPhotos];
        indexes = new int[maxPhotos];
    }

    public void setDefaults (Bitmap defaultPic) {
        uploadedPhotos = 0;
        this.def = new PhotoInfo(defaultPic);
        for (int i = 0; i < maxPhotos; i++) {
            myPhotos[i] = new PhotoInfo(defaultPic);
            photosByRating[i] = new PhotoInfo(defaultPic);
            myPhotos[i].posIndexArray = i;
            indexes[i] = i;
        }
        for (int i = 0; i < maxPhotos; i++) {
            String debug5 = "default position in indexes of element: " + i + " is " + myPhotos[i].posIndexArray;
            Log.d("TAG", debug5);
        }

    }

    public void addNewPhoto (PhotoInfo newPhoto) {
        int indexesPos = myPhotos[uploadedPhotos].posIndexArray;

        myPhotos[uploadedPhotos] = newPhoto;
        myPhotos[uploadedPhotos].posIndexArray = indexesPos;
        photosByRating[uploadedPhotos] = newPhoto;
        uploadedPhotos++;
        String debug1 = "Uploaded photos: " + uploadedPhotos;
        Log.d("TAG", debug1);
    }

    public Bitmap[] getBitmaps() {
        if (byRating) {
            Bitmap [] images = new Bitmap[maxPhotos];
            for (int i = 0; i < maxPhotos; i++) {
                images[i] = photosByRating[i].getPictureBit();
            }
            return images;
        } else {
            Bitmap[] images = new Bitmap[maxPhotos];
            for (int i = 0; i < maxPhotos; i++) {
                images[i] = myPhotos[i].getPictureBit();
            }
            return images;
        }
    }

    public void resetPhotoModel() {
        setDefaults(def.getPictureBit());
    }

    // one goes on the place of two
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