package com.example.android.photo;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by andrey on 30/03/2016.
 */
public class OnePhotoActivity extends Activity {

    int photoNumber;
    String photoNumStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_photo);
        Bundle extras = getIntent().getExtras();
        photoNumStr = extras.getString("Which Photo");
        try {
            photoNumber = Integer.parseInt(photoNumStr);
        } catch (NumberFormatException nfe) {
        }

        

    }
}
