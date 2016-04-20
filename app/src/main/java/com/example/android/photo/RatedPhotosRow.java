package com.example.android.photo;

import android.graphics.Bitmap;

/**
 * Created by andrey on 19/04/2016.
 */
public class RatedPhotosRow {
    private Bitmap photoBitm;
    private float photoRating;

    public RatedPhotosRow(Bitmap photoBitm, float photoRating) {
        this.photoBitm = photoBitm;
        this.photoRating = photoRating;
    }

    public float getPhotoRating() {
        return photoRating;
    }

    public void setPhotoRating(float photoRating) {
        this.photoRating = photoRating;
    }

    public Bitmap getPhotoBitm() {
        return photoBitm;
    }

    public void setPhotoBitm(Bitmap photoBitm) {
        this.photoBitm = photoBitm;
    }

}
