package com.example.android.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Objects;


public class UploadPhotoActivity extends Activity implements View.OnClickListener {

    final static String TAG = "TEST";

    private static final int RESULT_LOAD_IMAGE = 1;

    ImageView imageToUpload;
    ImageButton uploadImage;
    EditText uploadImageName;
    String uploasImageNameStr;

    String backToView;
    String order;

    PhotoInfo newPhoto;
    PhotoModel model;

    boolean cklicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_photo);

        Bundle extras = getIntent().getExtras();
        backToView = extras.getString("Which View");
        order = extras.getString("Based on");
        Log.d(TAG, backToView);
        Log.d("TEST", order);

        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        imageToUpload.setImageResource(R.drawable.emptyphoto);
        uploadImage = (ImageButton) findViewById(R.id.uploadImage);
        uploadImageName = (EditText) findViewById(R.id.editTextUploadName);

        model = PhotoModel.getInstance();

        imageToUpload.setOnClickListener(this);
        uploadImage.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageToUpload:
                // allows a gallery to be open:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                // "forResult" part allows to get back after you select an image
                break;
            case R.id.uploadImage:
                if (!cklicked) {
                    cklicked = true;
                    Bitmap myPictureUploading = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
                    uploasImageNameStr = uploadImageName.getText().toString();
                    newPhoto = new PhotoInfo(myPictureUploading, uploasImageNameStr);
                    model.addNewPhoto(newPhoto);
                    String status = "View is " + backToView;
                    Log.d(TAG, status);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if (Objects.equals(backToView, "GalleryMode")) {
                            Intent intent = new Intent(UploadPhotoActivity.this, MainActivityGalleryMode.class);
                            intent.putExtra("Based on", order);
                            startActivity(intent);
                        } else {
                            if (Objects.equals(backToView, "RatingMode")) {
                                Intent intent = new Intent(UploadPhotoActivity.this, MainActivityRatingMode.class);
                                intent.putExtra("Based on", order);
                                startActivity(intent);
                            }
                        }
                    }
                }
                break;
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
}
