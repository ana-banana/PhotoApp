package com.example.android.photo;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class ActivityMain extends Activity {

    private PhotoModel model; // stores all the information about uploaded photos
    private Bitmap defImage; // default bitmap for empty image
    private Bitmap defUserPic; // default bitmap for user picture
    private ProfileInfo myInfo; // users information
    private ProfileModel profile; // stores all the information relevant to the account

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        defImage = BitmapFactory.decodeResource(getResources(), R.drawable.emptyphotofaded);
        model = PhotoModel.getInstance();
        model.setDefaults(defImage);
        defUserPic = BitmapFactory.decodeResource(getResources(), R.drawable.profile_picture_def);
        myInfo = ProfileInfo.getInstance(defUserPic);
        profile = ProfileModel.getInstance();
        profile.setMyProfile(myInfo); // setting information about this account

// Button leading to the main gallery
        ImageButton startButton = (ImageButton) findViewById(R.id.imageButtonToGallery);
        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityMain.this, ActivityModeGallery.class);
                        startActivity(intent);
                    }
                }
        );

// Button that leads you to the profile activity
        ImageButton profileButton = (ImageButton) findViewById(R.id.imageButtonProfile);
        profileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // model.resetPhotoModel();
                        Intent intent = new Intent(ActivityMain.this, ActivityProfile.class);
                        startActivity(intent);
                    }
                }
        );
    }
}