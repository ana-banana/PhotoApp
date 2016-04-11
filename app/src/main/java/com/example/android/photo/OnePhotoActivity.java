package com.example.android.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class OnePhotoActivity extends Activity {

    int photoNumber; //position starts from 0
    String photoNumStr;

    Bitmap myImage;

    ImageView imageToView;
    TextView imageName;

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
        // also need to get name and rating
        imageToView = (ImageView) findViewById(R.id.imageToView);
        imageToView.setImageBitmap(myImage);


        Button backToGalleryButton = (Button) findViewById(R.id.buttonBackToGallery);
        backToGalleryButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OnePhotoActivity.this, MainActivityGallery.class);
                        startActivity(intent);
                    }
                }
        );

    }
}
