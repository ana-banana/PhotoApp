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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;


public class ActivityUploadPhoto extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;

    ImageButton addNewPhotoButton; // Button to add new photos, launches ActivityUploadPhoto
    ImageButton galleryModeButton; // Button to switch to Gallery mode, launches ActivityModelGallery
    ImageButton infoModeButton; // Button to switch to Information mode, launches ActivityModelInfo
    ImageButton settingsButton; // Button to switch to ActivitySettings
    ImageButton toProfileButton; // Button to switch to user's profile

    ImageView imageToUpload;
    ImageButton uploadImage;
    EditText uploadImageName;

    private ProgressBar progressBar;
    Handler handler = new Handler(){
        public void handleMessage(Message m){
            Intent intent = new Intent(ActivityUploadPhoto.this, ActivityModeGallery.class);
            startActivity(intent);
        }
    };

    String uploasImageNameStr;
    boolean chose = false;

    PhotoInfo newPhoto;
    PhotoModel model;

    boolean cklicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

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

        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        uploadImage = (ImageButton) findViewById(R.id.uploadImage);
        uploadImageName = (EditText) findViewById(R.id.editTextUploadName);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        model = PhotoModel.getInstance();

        imageToUpload.setOnClickListener(this);
        uploadImage.setOnClickListener(this);

        // Button for adding new photos and its listener
        addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityUploadPhoto.this, ActivityUploadPhoto.class);
                        //intent.putExtra("Which View", "GalleryMode"); // gallery mode or rating mode
                        //intent.putExtra("Based on", "upload"); // which order to come back to
                        startActivity(intent);
                    }
                }
        );

/*
        // Button for switching to info mode and its listener
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

        galleryModeButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryModeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityUploadPhoto.this, ActivityModeGallery.class);
                        intent.putExtra("Based on", "upload"); // which order to come back
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
                uploadImageName.setVisibility(View.VISIBLE);
                uploadImage.setVisibility(View.VISIBLE);
                break;
            case R.id.uploadImage: // onClick for "Upload me!" button
                Log.d("Uploading", "I called the listener");
                if ((!cklicked) && chose){
                    cklicked = true;
                    uploadImageName.setVisibility(View.GONE);
                    uploadImage.setVisibility(View.GONE);
                    Thread myThread = new Thread(new UploadImageThread());
                    myThread.start();
                }
                break;
        }
    }

    public void uploadMyImage() {
        Log.d("Threads", "Called uploadMyImage");
        Bitmap myPictureUploading = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
        uploasImageNameStr = uploadImageName.getText().toString();
        newPhoto = new PhotoInfo(myPictureUploading, uploasImageNameStr);
        model.addNewPhoto(newPhoto);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE); // to hide progress bar when finished
            }
        });

    }

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
            handler.sendEmptyMessage(1);
        }
    }

    // get called when a user has selected a picture from the gallery:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // to make sure it's the image we want and somethis was actually selected:
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData(); // uniform resource identifier
            imageToUpload.setImageURI(selectedImage);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
