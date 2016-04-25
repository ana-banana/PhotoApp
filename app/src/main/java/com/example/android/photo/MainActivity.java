package com.example.android.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends Activity {

    PhotoModel model;
    private Bitmap def;
    //private static int maxPhotos = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_main);

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);


       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       // fab.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View view) {
       //         Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
       //                 .setAction("Action", null).show();
       //     }
       // });

        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //if (actionBar != null) {
        //actionBar.setDisplayShowHomeEnabled(false);
        //actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setDisplayShowCustomEnabled(true);
        //    actionBar.setCustomView(R.layout.action_bar);
        //}

        def = BitmapFactory.decodeResource(getResources(), R.drawable.emptyphotofaded);
        model = PhotoModel.getInstance();
        model.setDefaults(def); // default bitmap

        ImageButton startButton = (ImageButton) findViewById(R.id.imageButtonToGallery);
        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MainActivityGalleryMode.class);
                        intent.putExtra("Based on", "upload");
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
                        Intent intent = new Intent(MainActivity.this, MainActivityGalleryMode.class);
                        startActivity(intent);
                    }
                }
        );
    }
}