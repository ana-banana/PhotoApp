package com.example.android.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    PhotoModel model;
    private Bitmap def;
    //private static int maxPhotos = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        def = BitmapFactory.decodeResource(getResources(), R.drawable.emptyphotofaded);
        model = PhotoModel.getInstance();
        model.setDefaults(def); // default bitmap

        ImageButton startButton = (ImageButton) findViewById(R.id.imageButtonToGallery);
        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MainActivityGallery.class);
                        startActivity(intent);
                    }
                }
        );

        ImageButton resetButton = (ImageButton) findViewById(R.id.imageButtonRestartGallery);
        resetButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.resetPhotoModel();
                        Intent intent = new Intent(MainActivity.this, MainActivityGallery.class);
                        startActivity(intent);
                    }
                }
        );
    }
}