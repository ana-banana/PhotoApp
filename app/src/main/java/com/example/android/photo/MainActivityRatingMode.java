package com.example.android.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivityRatingMode extends AppCompatActivity implements AdapterView.OnItemClickListener {

    final static String TAG = "TEST";

    ListView photosListView;
    PhotoModel model;
    ImageRatingsAdapter currentState;
    private static int maxPhotos = 8;
    Bitmap[] update;
    float[] updateRatings;

    public class RatedPhotosRow {
        private Bitmap photoBitm;
        private float photoRating;


        public RatedPhotosRow(Bitmap photoBitm, float photoRating) {
            this.photoBitm = photoBitm;
            this.photoRating = photoRating;
        }

        public float getPhotoRating() {
            return photoRating;
        }

        public void setPhotoRating(float photoRating) {
            this.photoRating = photoRating;
        }

        public Bitmap getPhotoBitm() {
            return photoBitm;
        }

        public void setPhotoBitm(Bitmap photoBitm) {
            this.photoBitm = photoBitm;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Created MainActivityRatingMode");

        setContentView(R.layout.main_ratings_view);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_mar);
        setSupportActionBar(myToolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar);

        photosListView = (ListView) findViewById(R.id.activity_main_ratings_listView);
        model = PhotoModel.getInstance();

        ArrayList<RatedPhotosRow> picturesList = new ArrayList<RatedPhotosRow>();
        update = new Bitmap[maxPhotos];
        update = model.getBitmaps();
        Log.d(TAG, "Updated Bitmaps");
        updateRatings = new float[maxPhotos];
        updateRatings = model.getPictureRatings();
        Log.d(TAG, "Updated Ratings");
        for (int i = 0; i < maxPhotos; i++) {
            picturesList.add(new RatedPhotosRow(update[i], updateRatings[i]));
        }

        currentState = ImageRatingsAdapter.getInstance(this, picturesList);

        //currentState.updateBitmaps(model);

        photosListView.setAdapter(currentState);
        photosListView.setOnItemClickListener(this);

        ImageButton backToMain = (ImageButton) findViewById(R.id.imageButtonReset);
        backToMain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        ImageButton addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityRatingMode.this, UploadPhotoActivity.class);
                        intent.putExtra("Which View", "RatingMode");
                        startActivity(intent);
                    }
                }
        );

        /*ImageButton ratedPhotoButton = (ImageButton) findViewById(R.id.imageButtonRating);
        ratedPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivityRatingMode.class);
                        startActivity(intent);
                    }
                }
        ); */

        ImageButton galleryViewButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryViewButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivityGalleryMode.class);
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
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivityRatingMode.class);
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
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivityRatingMode.class);
                        startActivity(intent);
                    }
                }
        );
    }
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        if (position >= model.uploadedPhotos) {
            Toast.makeText(MainActivityRatingMode.this, "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
        } else {
            RatedPhotosRow ratedPhotosRow = (RatedPhotosRow) adapter.getItemAtPosition(position);
            Intent intent = new Intent(MainActivityRatingMode.this, OnePhotoActivity.class);
            // you need to inform the OnePhotoActivity that you want to see photo at this specific position user clicked
            String pos = String.valueOf(position);
            intent.putExtra("Which Photo", pos);
            startActivity(intent);
        }
    }
}
/*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.activity_main_ratings_listView) {
            if (position >= model.uploadedPhotos) {
                Toast.makeText(MainActivityRatingMode.this, "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
            } else {
                // you need to inform the OnePhotoActivity that you want to see photo at this specific position user clicked
                Intent intent = new Intent(MainActivityRatingMode.this, OnePhotoActivity.class);
                String pos = String.valueOf(position);
                intent.putExtra("Which Photo", pos);
                startActivity(intent);
            }
        }
    }



} */

