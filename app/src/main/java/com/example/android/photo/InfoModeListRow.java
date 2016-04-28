package com.example.android.photo;
import android.graphics.Bitmap;


public class InfoModeListRow {
    private Bitmap photoBitm;
    private float photoRating;
    private String photoName;

    public InfoModeListRow(Bitmap photoBitm, float photoRating, String photoName) {
        this.photoBitm = photoBitm;
        this.photoRating = photoRating;
        this.photoName = photoName;
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

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

}
