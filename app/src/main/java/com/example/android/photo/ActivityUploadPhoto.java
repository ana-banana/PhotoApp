package com.example.android.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;


public class ActivityUploadPhoto extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1; // stores chosen photo

// ********** BUTTONS FOR the ACTION BAR **********
    ImageButton addNewPhotoButton; // Button to add new photos, launches ActivityUploadPhoto
    ImageButton galleryModeButton; // Button to switch to Gallery mode, launches ActivityModelGallery
    ImageButton infoModeButton; // Button to switch to Information mode, launches ActivityModelInfo
    ImageButton settingsButton; // Button to switch to ActivitySettings
    ImageButton toProfileButton; // Button to switch to user's profile

// ********** PROGRESS BAR RELATED **********
    private ProgressBar progressBar;
    Handler handler = new Handler(){
        public void handleMessage(Message m){
            Intent intent = new Intent(ActivityUploadPhoto.this, ActivityModeGallery.class);
            startActivity(intent); // starts gallery activity when done uploading on a backgroung thread
        }
    };

// ********** UPLOAD RELATED **********
    ImageView imageToUpload; // shows first a default image, then the chosen one
    ImageButton uploadImage; // appears when photo was chosen; pressing this button starts the upload process
    EditText uploadImageName; // appears when photo is chosen; here user can give a name to the image
    String uploadImageNameStr;// stores name given to the image as a string
    boolean chose = false; // sets to true if user chose a photo from the device gallery. Prevents overshooting uploads
    boolean cklicked = false; // sets to true when upload button was clicked and upload process started
    PhotoInfo newPhoto; // will store a new object created with uploaded information

// ********** REUSABLE **********
    PhotoModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        model = PhotoModel.getInstance();

///////////////////////////////////////////
// ********** SETTING ACTION BAR **********
///////////////////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_aup);
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

///////////////////////////////////////
// ********** UPLOAD RELATED **********
///////////////////////////////////////
        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        uploadImage = (ImageButton) findViewById(R.id.uploadImage);
        uploadImageName = (EditText) findViewById(R.id.editTextUploadName);
        imageToUpload.setOnClickListener(this);
        uploadImage.setOnClickListener(this);

///////////////////////////////////////////////////
// ********** BUTTONS FOR the ACTION BAR **********
///////////////////////////////////////////////////

// Button for adding new photos and its listener
        addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityUploadPhoto.this, ActivityUploadPhoto.class);
                        startActivity(intent);
                    }
                }
        );
// Button to switch to the gallery mode
        galleryModeButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryModeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityUploadPhoto.this, ActivityModeGallery.class);
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
                        Intent intent = new Intent(ActivityUploadPhoto.this, ActivitySettings.class);
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
                        Intent intent = new Intent(ActivityUploadPhoto.this, ActivityProfile.class);
                        startActivity(intent);
                    }
                }
        );

//////////////////////////////////////////////////////////
// ********** PROGRESS BAR & BACKGROUND THREAD **********
//////////////////////////////////////////////////////////
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }


    public void uploadMyImage() {
        Bitmap myPictureUploading = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
        uploadImageNameStr = uploadImageName.getText().toString(); // stores name that user gave to the picture
        newPhoto = new PhotoInfo(myPictureUploading, uploadImageNameStr); // creates a PhotoInfo object with given data
        model.addNewPhoto(newPhoto); // stores information in model
        this.runOnUiThread(new Runnable() { // stops running progress bar when done on the background
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE); // to hide progress bar when finished
            }
        });

    }

// BACKGROUND THREAD FOR UPLOADING
    public class UploadImageThread implements Runnable {
        @Override
        public void run() {
            ActivityUploadPhoto.this.runOnUiThread(new Runnable() {
                @Override
                public void run() { // runs on the main thread
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
            uploadMyImage();
            handler.sendEmptyMessage(1); // to notify that process is done
        }
    }

// Gets called when a user has selected a picture from the gallery:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // to make sure it's the image we want and something was actually selected:
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData(); // uniform resource identifier
            imageToUpload.setImageURI(selectedImage);
        }
    }

// TO DISABLE A BACK BUTTON
    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageToUpload: // onClick to chose an image to upload
                // allows a gallery to be open:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                // "forResult" part allows to get back after you select an image
                chose = true; // confirms that user chose a photo to upload
                uploadImageName.setVisibility(View.VISIBLE); // now can name the image
                uploadImage.setVisibility(View.VISIBLE); // now can save all the uploaded information
                break;
            case R.id.uploadImage: // onClick for "Upload me!" button
                if ((!cklicked) && chose){
                    cklicked = true;
                    uploadImageName.setVisibility(View.GONE); // hide naming option
                    uploadImage.setVisibility(View.GONE); // hide saving option
                    Thread myThread = new Thread(new UploadImageThread());
                    myThread.start(); // runs on a background thread
                }
                break;
        }
    }

}
