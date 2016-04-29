package com.example.android.photo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

// Activity to display a photo, to rate it and to submit a rating

public class ActivityOnePhoto extends Activity {

// ********** PHOTO INFORMATION **********
    int photoNumber; //position starts from 0
    String photoNumStr; // for the extra purposes (to communicate between activities
    Bitmap myImage; // temporary stores an image to display
    String myName; // temporary stores the name name of the image
    ImageView imageToView; // image user is looking at
    TextView imageName; // name of this image
    TextView overallRating; // overall rating that this image has
    TextView setRating; // rating that was just set on the rating bar

// ********** NAVIGATION **********
    ImageButton backToGalleryButton; // pressing this button returns user to the gallery
    ImageButton nextPhoto, prevPhoto; // pressing these buttons allows to navigate between photos

// ********** RATING **********
    ImageButton sbmRating; // pressing this button submits the rating set on the rating bar
    RatingBar ratePhoto; // rating bar to rate the photo
    String text; // text of notifications about submitted rating

// ********** REUSABLE **********
    PhotoModel model; // instance of the photo model that stores all the data about images
    final static String TAG = "TEST"; // debugging/logs

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_one_photo);

        Bundle extras = getIntent().getExtras();
        model = PhotoModel.getInstance();
        photoNumStr = extras.getString("Which Photo"); // shows which photo was clicked on the gridView/listView from the gallery

        try {
            photoNumber = Integer.parseInt(photoNumStr);
        } catch (NumberFormatException nfe) {
        }
        if (model.getOrder()) { // based on upload
            myImage = model.myPhotos[photoNumber].getPictureBit();
            myName = model.myPhotos[photoNumber].getPictureName();
        } else { // based on rating
            myImage = model.photosByRating[photoNumber].getPictureBit();
            myName = model.photosByRating[photoNumber].getPictureName();
        }

//////////////////////////////////////////
// ********** PHOTO INFO **********
//////////////////////////////////////////

        imageToView = (ImageView) findViewById(R.id.imageToView);
        imageToView.setImageBitmap(myImage);
        imageName = (TextView) findViewById(R.id.textViewImageDescription);
        imageName.setText(myName);

        setRating = (TextView) findViewById(R.id.textViewStScore);

///////////////////////////////////
// ********** NAVIGATION **********
///////////////////////////////////

// ********** BACK TO THE GALLERY ********** Button sends user back to the gallery activity
        backToGalleryButton = (ImageButton) findViewById(R.id.buttonBackToGallery);
        backToGalleryButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityOnePhoto.this, ActivityModeGallery.class);
                        startActivity(intent);
                    }
                }
        );

// ********** NEXT BUTTON ********** Button that leads to the next uploaded/rated photo
        nextPhoto = (ImageButton) findViewById(R.id.imageButtonNextPhoto);
        nextPhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (photoNumber < (model.uploadedPhotos - 1)) { // if it is not the last photo
                            Intent intent = getIntent();
                            // to inform the ActivityOnePhoto that you want to see photo at this specific position user clicked
                            String pos = String.valueOf(photoNumber + 1);
                            intent.putExtra("Which Photo", pos);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(ActivityOnePhoto.this, "Sorry, this is the last photo. Upload more", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

// ********** PREVIOUS BUTTON ********** Button that leads to the previous uploaded/rated photo
        prevPhoto = (ImageButton) findViewById(R.id.imageButtonPrevPhoto);
        prevPhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (photoNumber > 0) { // if it is not the first image
                            Intent intent = getIntent();
                            // to inform the ActivityOnePhoto that you want to see photo at this specific position user clicked
                            String pos = String.valueOf(photoNumber - 1);
                            intent.putExtra("Which Photo", pos);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(ActivityOnePhoto.this, "This is the first photo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

///////////////////////////////////
// ********** RATING **********
///////////////////////////////////

// ********** OVERALL RATING OF THE IMAGE ********** accumulative(average) rating of the photo for all previous rating events
        overallRating = (TextView) findViewById(R.id.textViewYourRating);
        if (model.getOrder()) { // based on upload
            text = "Rating: " + String.valueOf(model.myPhotos[photoNumber].getPictureRating());
        } else { // based on rating
            text = "Rating: " + String.valueOf(model.photosByRating[photoNumber].getPictureRating());
        }
        overallRating.setText(text);

// ********** RATING BAR AND LISTENERS **********
        ratePhoto = (RatingBar) findViewById(R.id.ratingBarImage);
        if (model.getOrder()) { // based on upload
            ratePhoto.setRating(model.myPhotos[photoNumber].getPictureRating());
        } else { // based on rating
            ratePhoto.setRating(model.photosByRating[photoNumber].getPictureRating());
        }
        ratePhoto.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        setRating.setText(String.valueOf(rating));
                    }
                }
        );

// ********** SUBMIT BUTTON ********** Button to submit chosen rating to count it towards average rating of the picture
        sbmRating = (ImageButton) findViewById(R.id.buttonSubmitRating);
        sbmRating.setOnClickListener(
                new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        text = "Submitted rating " + String.valueOf(ratePhoto.getRating()); // reads information from the rating bar
                        Toast.makeText(ActivityOnePhoto.this, text, Toast.LENGTH_SHORT).show();
                        float oldRating = 0; // stores previous accumulative rating. Need it to know later if rating grows or drops
                        if (model.getOrder()) { // based on upload
                            oldRating = model.myPhotos[photoNumber].getPictureRating();
                        } else { // based on rating
                            oldRating = model.photosByRating[photoNumber].getPictureRating();
                        }
                        float newRating = 0; // stores new accumulative rating
                        if (model.getOrder()) { // based on upload
                            model.myPhotos[photoNumber].setRating(ratePhoto.getRating()); // stores new rating in the model
                            newRating = model.myPhotos[photoNumber].getPictureRating();
                            text = "Rating: " + String.valueOf(model.myPhotos[photoNumber].getPictureRating());
                        } else { // based on rating
                            model.photosByRating[photoNumber].setRating(ratePhoto.getRating());
                            newRating = model.photosByRating[photoNumber].getPictureRating();
                            text = "Rating: " + String.valueOf(model.photosByRating[photoNumber].getPictureRating());
                        }
                        overallRating.setText(text);
                        model.resetRatedPhotos(photoNumber, oldRating, newRating); // recounts order based on rating
                    }
                }

        );
    }

// TO DISABLE A BACK BUTTON
    @Override
    public void onBackPressed() {
    }

}
