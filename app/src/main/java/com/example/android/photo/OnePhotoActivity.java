package com.example.android.photo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class OnePhotoActivity extends Activity {

    int photoNumber; //position starts from 0
    String photoNumStr;

    Bitmap myImage;
    String myName;

    String order;
    String backToView;

    ImageView imageToView;
    TextView imageName;
    TextView yourRating;
    TextView setRating;
    ImageButton backToGalleryButton;
    ImageButton nextPhoto, prevPhoto;
    ImageButton sbmRating;
    RatingBar ratePhoto;

    String text;

    PhotoModel model;
    final static String TAG = "TEST";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_one_photo);

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_opa);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        View vAction = LayoutInflater
                .from(actionBar.getThemedContext())
                .inflate(R.layout.action_bar_one_photo, null);
        android.support.v7.app.ActionBar.LayoutParams params = new android.support.v7.app.ActionBar.LayoutParams(
                android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(vAction, params);

        //actionBar.setCustomView(R.layout.action_bar_one_photo); */

        Bundle extras = getIntent().getExtras();
        backToView = extras.getString("Which View");
        model = PhotoModel.getInstance();
        photoNumStr = extras.getString("Which Photo");
       Log.d("TAG", photoNumStr);
        order = extras.getString("Based on");
        try {
            photoNumber = Integer.parseInt(photoNumStr);
        } catch (NumberFormatException nfe) {
        }
        if (Objects.equals(order, "upload")) {
            myImage = model.myPhotos[photoNumber].getPictureBit();
            myName = model.myPhotos[photoNumber].getPictureName();
        } else if (Objects.equals(order, "rating")) {
            myImage = model.photosByRating[photoNumber].getPictureBit();
            myName = model.photosByRating[photoNumber].getPictureName();
        }
        imageToView = (ImageView) findViewById(R.id.imageToView);
        imageToView.setImageBitmap(myImage);
        imageName = (TextView) findViewById(R.id.textViewImageDescription);
        imageName.setText(myName);

        setRating = (TextView) findViewById(R.id.textViewStScore);

        backToGalleryButton = (ImageButton) findViewById(R.id.buttonBackToGallery);
        backToGalleryButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Objects.equals(backToView, "GalleryMode")) {
                            String modelState = "Model byRating when backButton is " + model.byRating;
                            Log.d(TAG, modelState);
                            Intent intent = new Intent(OnePhotoActivity.this, MainActivityGalleryMode.class);
                            intent.putExtra("Based on", order);
                            startActivity(intent);
                        } else if (Objects.equals(backToView, "RatingsMode")) {
                            Intent intent = new Intent(OnePhotoActivity.this, MainActivityRatingMode.class);
                            intent.putExtra("Based on", order);
                            startActivity(intent);
                        }
                    }
                }
        );
        nextPhoto = (ImageButton) findViewById(R.id.imageButtonNextPhoto);
        nextPhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (photoNumber < (model.uploadedPhotos - 1)) {
                            Intent intent = getIntent();
                            // you need to inform the OnePhotoActivity that you want to see photo at this specific position user clicked
                            String pos = String.valueOf(photoNumber + 1);
                            intent.putExtra("Which Photo", pos);
                            intent.putExtra("Based on", order);
                            intent.putExtra("Which View", backToView);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(OnePhotoActivity.this, "Sorry, this is the last photo. Upload more", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        prevPhoto = (ImageButton) findViewById(R.id.imageButtonPrevPhoto);
        prevPhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (photoNumber > 0) {
                            Intent intent = getIntent();
                            // you need to inform the OnePhotoActivity that you want to see photo at this specific position user clicked
                            String pos = String.valueOf(photoNumber - 1);
                            intent.putExtra("Which Photo", pos);
                            intent.putExtra("Based on", order);
                            intent.putExtra("Which View", backToView);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(OnePhotoActivity.this, "This is the first photo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        yourRating = (TextView) findViewById(R.id.textViewYourRating);
        if (Objects.equals(order, "upload")) {
            text = "Rating: " + String.valueOf(model.myPhotos[photoNumber].getPictureRating());
        } else if (Objects.equals(order, "rating")) {
            text = "Rating: " + String.valueOf(model.photosByRating[photoNumber].getPictureRating());
        }
        yourRating.setText(text);

        ratePhoto = (RatingBar) findViewById(R.id.ratingBarImage);
        if (Objects.equals(order, "upload")) {
            ratePhoto.setRating(model.myPhotos[photoNumber].getPictureRating());
        } else if (Objects.equals(order, "rating")) {
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

        sbmRating = (ImageButton) findViewById(R.id.buttonSubmitRating);
        sbmRating.setOnClickListener(
                new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {

                        text = "Submitted rating " + String.valueOf(ratePhoto.getRating());
                        Toast.makeText(OnePhotoActivity.this, text, Toast.LENGTH_SHORT).show();
                        float oldRating = 0;
                        if (Objects.equals(order, "upload")) {
                            oldRating = model.myPhotos[photoNumber].getPictureRating();
                        } else if (Objects.equals(order, "rating")) {
                            oldRating = model.photosByRating[photoNumber].getPictureRating();
                        }
                        String debug2 = "Old rating of picture: " + photoNumber + " is " + oldRating;
                        Log.d("TAG", debug2);
                        String debug4 = "Old position in indexes of picture: " + photoNumber + " is " + model.myPhotos[photoNumber].posIndexArray;
                        Log.d("TAG", debug4);
                        float newRating = 0;
                        if (Objects.equals(order, "upload")) {
                            model.myPhotos[photoNumber].setRating(ratePhoto.getRating());
                            newRating = model.myPhotos[photoNumber].getPictureRating();
                            text = "Rating: " + String.valueOf(model.myPhotos[photoNumber].getPictureRating());
                        } else if (Objects.equals(order, "rating")) {
                            model.photosByRating[photoNumber].setRating(ratePhoto.getRating());
                            newRating = model.photosByRating[photoNumber].getPictureRating();
                            text = "Rating: " + String.valueOf(model.photosByRating[photoNumber].getPictureRating());
                        }
                        yourRating.setText(text);

                        String debug3 = "New rating of picture: " + photoNumber + " is " + newRating;
                        Log.d("TAG", debug3);

                        model.resetRatedPhotos(photoNumber, oldRating, newRating);

                        String debug5 = "New position in indexes of picture: " + photoNumber + " is " + model.myPhotos[photoNumber].posIndexArray;
                        Log.d("TAG", debug5);

                    }
                }

        );

    }

}
