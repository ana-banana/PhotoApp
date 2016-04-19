package com.example.android.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class OnePhotoActivity extends Activity {

    int photoNumber; //position starts from 0
    String photoNumStr;

    Bitmap myImage;
    String myName;

    ImageView imageToView;
    TextView imageName;
    TextView yourRating;
    Button backToGalleryButton;
    Button sbmRating;
    RatingBar ratePhoto;

    String text;

    PhotoModel model;
    final static String TAG = "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.one_photo);

        Bundle extras = getIntent().getExtras();
        model = PhotoModel.getInstance();
        photoNumStr = extras.getString("Which Photo");
       Log.d("TAG", photoNumStr);

        try {
            photoNumber = Integer.parseInt(photoNumStr);
        } catch (NumberFormatException nfe) {
        }
        myImage = model.myPhotos[photoNumber].getPictureBit();
        myName = model.myPhotos[photoNumber].getPictureName();
        imageToView = (ImageView) findViewById(R.id.imageToView);
        imageToView.setImageBitmap(myImage);
        imageName = (TextView) findViewById(R.id.textViewImageDescription);
        imageName.setText(myName);



        backToGalleryButton = (Button) findViewById(R.id.buttonBackToGallery);
        backToGalleryButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OnePhotoActivity.this, MainActivityGalleryMode.class);
                        startActivity(intent);
                    }
                }
        );

        yourRating = (TextView) findViewById(R.id.textViewYourRating);
        text = "Rating: " + String.valueOf(model.myPhotos[photoNumber].getPictureRating());
        yourRating.setText(text);

        ratePhoto = (RatingBar) findViewById(R.id.ratingBarImage);
        ratePhoto.setRating(model.myPhotos[photoNumber].getPictureRating());
        ratePhoto.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        text = "Rating: " + String.valueOf(rating);
                        yourRating.setText(text);
                    }
                }
        );

        sbmRating = (Button) findViewById(R.id.buttonSubmitRating);
        sbmRating.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text = "Submitted rating " + String.valueOf(ratePhoto.getRating());
                        Toast.makeText(OnePhotoActivity.this, text, Toast.LENGTH_SHORT).show();
                        float oldRating = model.myPhotos[photoNumber].getPictureRating();

                        String debug2 = "Old rating of picture: " + photoNumber + " is " + oldRating;
                        Log.d("TAG", debug2);
                        String debug4 = "Old position in indexes of picture: " + photoNumber + " is " + model.myPhotos[photoNumber].posIndexArray;
                        Log.d("TAG", debug4);

                        model.myPhotos[photoNumber].setRating(ratePhoto.getRating());
                        float newRating = model.myPhotos[photoNumber].getPictureRating();

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
