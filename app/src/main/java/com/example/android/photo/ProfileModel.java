package com.example.android.photo;
import android.util.Log;

import java.util.ArrayList;

public class ProfileModel {

    static ProfileModel mInstance;
    ProfileInfo myProfile; // holds user's own profile information
    ArrayList<ProfileInfo> friends; // holds user's friends profile information
    int numberOfFriends;


    public static ProfileModel getInstance() {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new ProfileModel();
            Log.d("Profile", "I've created profile");
            return mInstance;
        }
    }

    public ProfileModel() {
        friends = new ArrayList<ProfileInfo>();
        numberOfFriends = 0;
    }

    public void setMyProfile (ProfileInfo mine) {
        myProfile = mine;
    }

// only for test purposes
    public void addTestFriend (ProfileInfo testFriend) {
        friends.add(testFriend);
        numberOfFriends ++;
    }

    public String getStringNumberOfFriends() {
        return String.valueOf(numberOfFriends);
    }

    public int getNumberOfFriends() {
        return numberOfFriends;
    }
}
