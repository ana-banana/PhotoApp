package com.example.android.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

// Activity where user can change user picture and user name
public class ActivityManageProfile extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1; // stores chosen photo

// ********** BUTTONS FOR the ACTION BAR **********
    ImageButton addNewPhotoButton; // Button to add new photos, launches ActivityUploadPhoto
    ImageButton galleryModeButton; // Button to switch to Gallery mode, launches ActivityModelGallery
    //ImageButton infoModeButton; // Button to switch to Information mode, launches ActivityModelInfo
    ImageButton settingsButton; // Button to switch to ActivitySettings
    ImageButton toProfileButton; // Button to switch to user's profile

// ********** VIEWS FROM LAYOUT & RELATED **********
    ImageButton save; // pressing this button saves the changes
    ImageView manageProfPic; // shows first the current user picture. By pressing it user can upload new picture
    EditText manageName; // shows first the current name as a hint. When text is changed saves it into newName
    TextView notification; // appears when changes have been saved
    boolean changedPicture = false; // sets to true if new picture was uploaded
    boolean changedName = false; // sets to true if name was changed
    Bitmap newPicture; // stores new uploaded picture
    String newName; // stores new profile name

// ********** REUSABLE **********
    ProfileModel profile; // an instance of a profile model

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profile = ProfileModel.getInstance();

        setContentView(R.layout.activity_manage_profile);

///////////////////////////////////////////
// ********** SETTING ACTION BAR **********
///////////////////////////////////////////

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_amp);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        // to set a custom action bar:
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        // to make it actually fit parent:
        //actionBar.setCustomView(R.layout.action_bar);
        View vAction = LayoutInflater
                .from(actionBar.getThemedContext())
                .inflate(R.layout.action_bar, null);
        android.support.v7.app.ActionBar.LayoutParams params = new android.support.v7.app.ActionBar.LayoutParams(
                android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(vAction, params);

///////////////////////////////////////////////////
// ********** BUTTONS FOR the ACTION BAR **********
///////////////////////////////////////////////////

        // Button for adding new photos and its listener
        addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityManageProfile.this, ActivityUploadPhoto.class);
                        finish();
                        startActivity(intent);
                    }
                }
        );

/* //this button and activity are currently disabled
        // Button for switching to ratings mode and its listener
        infoModeButton = (ImageButton) findViewById(R.id.imageButtonRating);
        infoModeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivitySettings.this, ActivityModeInfo.class);
                        intent.putExtra("Based on", "upload"); // which order to come back
                        startActivity(intent);
                    }
                }
        ); */

        // Button that switched to the gallery mode
        galleryModeButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryModeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityManageProfile.this, ActivityModeGallery.class);
                        finish();
                        startActivity(intent);
                    }
                }
        );

        // Button to switch to settings activity
        settingsButton = (ImageButton) findViewById(R.id.imageButtonSettings);
        settingsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityManageProfile.this, ActivitySettings.class);
                        finish();
                        startActivity(intent);
                    }
                }
        );

        // Button to go to user's profile
        toProfileButton = (ImageButton) findViewById(R.id.imageButtonProfile);
        toProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityManageProfile.this, ActivityProfile.class);
                        finish();
                        startActivity(intent);
                    }
                }
        );

//////////////////////////////////////////
// ********** VIEWS & LISTENERS **********
//////////////////////////////////////////

// Text view to notify that changes are saved
        notification = (TextView)findViewById(R.id.textSavedChanges);

//ImageView for the profile picture
        manageProfPic = (ImageView)findViewById(R.id.my_profile_photo_manage);
        manageProfPic.setImageBitmap(profile.myProfile.getProfPicture());
        manageProfPic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent managePicIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(managePicIntent, RESULT_LOAD_IMAGE);
                        changedPicture = true;
                        notification.setVisibility(View.GONE);
                        save.setVisibility(View.VISIBLE);
                    }
                }
        );

// EditText view for the profile name
        manageName = (EditText)findViewById(R.id.edit_profile_name_manage);
        manageName.setHint(profile.myProfile.getProfName());
        manageName.addTextChangedListener(
                new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        changedName = true;
                        newName = manageName.getText().toString();
                        notification.setVisibility(View.GONE);
                        save.setVisibility(View.VISIBLE);
            }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        }
        );


// Image button to save changes
        save = (ImageButton)findViewById(R.id.imageButtonSaveChanges);
        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (changedName) {
                            profile.myProfile.setProfName(newName);
                        }
                        if (changedPicture) {
                            newPicture = ((BitmapDrawable) manageProfPic.getDrawable()).getBitmap();
                            profile.myProfile.setProfPicture(newPicture);
                        }
                        save.setVisibility(View.GONE); // hide button for saving changes when done saving
                        notification.setVisibility(View.VISIBLE); // show notification that changes saved
                    }
                }
        );

    }

// get called when a user has selected a picture from the gallery:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // to make sure it's the image we want and something was actually selected:
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData(); // uniform resource identifier
            manageProfPic.setImageURI(selectedImage);
            save.setVisibility(View.VISIBLE); // make button for saving changes visible
        }
    }

// TO DISABLE A BACK BUTTON
    @Override
    public void onBackPressed() {
    }
}
