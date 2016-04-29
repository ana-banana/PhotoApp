package com.example.android.photo;
import android.graphics.Bitmap;

// Class to store information picture and name of one profile

public class ProfileInfo {

    static ProfileInfo mInstance;
    private Bitmap profPicture; // profile picture uploaded by user
    private String profName; // name set by user

    public static ProfileInfo getInstance(Bitmap defPicture) {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new ProfileInfo(defPicture);
            return mInstance;
        }
    }

    public ProfileInfo (Bitmap defPicture) {
        profPicture = defPicture;
        profName = "New User";
    }

    public void setProfPicture(Bitmap newPicture) {
        profPicture = newPicture;
    }

    public void setProfName(String newName) {
        profName = newName;
    }

    public String getProfName() {
        return profName;
    }

    public Bitmap getProfPicture() {
        return profPicture;
    }

}
