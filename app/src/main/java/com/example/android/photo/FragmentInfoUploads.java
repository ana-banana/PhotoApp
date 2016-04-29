package com.example.android.photo;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

// Fragment that controls gallery list type ("Details" tab) displaying based in order based on images upload time

public class FragmentInfoUploads extends Fragment {
    PhotoModel model;

// ********** UTILS **********
    ImageAdapterForInfoMode currentState;
    private int maxPhotos = 16;
    Bitmap[] update; // stores Bitmaps of previously uploaded photos in required order
    float[] updateRatings; // stores ratings of previously uploaded photos in required order
    String[] updateNames; // stores names of previously uploaded photos in required order
    InfoModeListRow item; // object of class representing one row of list view with photos

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        model = PhotoModel.getInstance();
        View rootView = inflater.inflate(R.layout.fragment_rates_uploads, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.fragment_rates_uploads_listView);
        ArrayList<InfoModeListRow> picturesList = new ArrayList<InfoModeListRow>();

//////////////////////////////
// ********** UTILS **********
//////////////////////////////
        update = new Bitmap[maxPhotos];
        update = model.getSmallBitmapsUpload();
        updateRatings = new float[maxPhotos];
        updateRatings = model.getPictureRatingsUpload();
        updateNames = new String[maxPhotos];
        updateNames = model.getPictureNamesUpload();

//////////////////////////////////////////
// ********** LIST VIEW RELATED **********
//////////////////////////////////////////
        for (int i = 0; i < maxPhotos; i++) {
            item = new InfoModeListRow(update[i], updateRatings[i], updateNames[i]);
            picturesList.add(item);
        }
        currentState = new ImageAdapterForInfoMode(getContext(), picturesList);
        listView.setAdapter(currentState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position > model.uploadedPhotos) { // empty photo but not the first one
                    Toast.makeText(getContext(), "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
                } else if (position == model.uploadedPhotos) { // clicking first empty photo opens an upload photo activity
                    Intent intent = new Intent(getContext(), ActivityUploadPhoto.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), ActivityOnePhoto.class);
                    // you need to inform the ActivityOnePhoto that you want to see photo at this specific position user clicked
                    String pos = String.valueOf(position);
                    intent.putExtra("Which Photo", pos); // passes the information about which photo in the list was clicked
                    startActivity(intent);
                }
            }
        });
        return rootView;
    }

}
