package com.example.android.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView photosGridView;

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

        Button addNewPhotoButton = (Button)findViewById(R.id.add_new_photo);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, UploadPhotoActivity.class);
                        startActivity(intent);
                    }
                }
        );

        photosGridView = (GridView)findViewById(R.id.activity_main_gridView);
        photosGridView.setAdapter(new ImageAdapter(this));
        photosGridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.activity_main_gridView) {

            // you need to inform the OnePhotoActivity that you want to see photo at this specific position user clicked
            Intent intent = new Intent(MainActivity.this, OnePhotoActivity.class);
            intent.putExtra("Which Photo", position);
            startActivity(intent);
        }
    }

}
