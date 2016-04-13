package com.example.android.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivityGallery extends Activity  implements AdapterView.OnItemClickListener {
    GridView photosGridView;
    PhotoModel model;
    ImageAdapter currentState;
    //private static int maxPhotos = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_gallery);

        //setGridView(model.getImages());
        photosGridView = (GridView) findViewById(R.id.activity_main_gridView);
        model = PhotoModel.getInstance();

        currentState = ImageAdapter.getInstance(this, model);

        currentState.updateBitmaps(model);

        photosGridView.setAdapter(currentState);
        photosGridView.setOnItemClickListener(this);

        ImageButton backToMain = (ImageButton) findViewById(R.id.imageButtonBack);
        backToMain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityGallery.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        ImageButton addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityGallery.this, UploadPhotoActivity.class);
                        startActivity(intent);
                    }
                }
        );

        ImageButton ratedPhotoButton = (ImageButton) findViewById(R.id.imageButtonRating);
        ratedPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.showByRating();
                        Intent intent = new Intent(MainActivityGallery.this, MainActivityGallery.class);
                        startActivity(intent);
                    }
                }
        );

        ImageButton basedOnRating = (ImageButton) findViewById(R.id.imageButtonOrderRating);
        basedOnRating.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.showByRating();
                        Intent intent = new Intent(MainActivityGallery.this, MainActivityGallery.class);
                        startActivity(intent);
                    }
                }
        );

        ImageButton basedOnUpload = (ImageButton) findViewById(R.id.imageButtonOrderUpload);
        basedOnUpload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.showByTime();
                        Intent intent = new Intent(MainActivityGallery.this, MainActivityGallery.class);
                        startActivity(intent);
                    }
                }
        );

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.activity_main_gridView) {
            if (position >= model.uploadedPhotos) {
                Toast.makeText(MainActivityGallery.this, "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
            } else {
                // you need to inform the OnePhotoActivity that you want to see photo at this specific position user clicked
                Intent intent = new Intent(MainActivityGallery.this, OnePhotoActivity.class);
                String pos = String.valueOf(position);
                intent.putExtra("Which Photo", pos);
                startActivity(intent);
            }
        }
    }

}
