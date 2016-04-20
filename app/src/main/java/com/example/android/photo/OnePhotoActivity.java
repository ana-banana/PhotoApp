package com.example.android.photo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
    ImageButton sbmRating;
    RatingBar ratePhoto;

    String text;

    PhotoModel model;
    final static String TAG = "TEST";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.one_photo);

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
