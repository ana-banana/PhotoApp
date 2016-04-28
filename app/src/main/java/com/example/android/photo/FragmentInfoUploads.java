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

public class FragmentInfoUploads extends Fragment {

    PhotoModel model;
    ImageAdapterForInfoMode currentState;
    private static int maxPhotos = 8;
    Bitmap[] update;
    float[] updateRatings;
    String[] updateNames;
    InfoModeListRow item;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.main_gallery_view, container, false);
        View rootView = inflater.inflate(R.layout.fragment_rates_uploads, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.fragment_rates_uploads_listView);
        model = PhotoModel.getInstance();
        ArrayList<InfoModeListRow> picturesList = new ArrayList<InfoModeListRow>();
        update = new Bitmap[maxPhotos];
        update = model.getBitmapsUpload();
        updateRatings = new float[maxPhotos];
        updateRatings = model.getPictureRatingsUpload();
        updateNames = new String[maxPhotos];
        updateNames = model.getPictureNamesUpload();
        for (int i = 0; i < maxPhotos; i++) {
            item = new InfoModeListRow(update[i], updateRatings[i], updateNames[i]);
            picturesList.add(item);
        }
        currentState = new ImageAdapterForInfoMode(getContext(), picturesList);
        //listView.setAdapter(currentState);
        listView.setAdapter(currentState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position >= model.uploadedPhotos) {
                    Toast.makeText(getContext(), "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
                } else {
                    //InfoModeListRow ratedPhotosRow = (InfoModeListRow) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getContext(), ActivityOnePhoto.class);
                    // you need to inform the ActivityOnePhoto that you want to see photo at this specific position user clicked
                    String pos = String.valueOf(position);
                    intent.putExtra("Which Photo", pos);
                    intent.putExtra("Based on", "rating");
                    intent.putExtra("Which View", "RatingsMode");
                    startActivity(intent);
                }
            }
        });
        return rootView;
    }

/*
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        Log.d("onItemClickListener", "I've been here");
        if (position >= model.uploadedPhotos) {
            Toast.makeText(getContext(), "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
        } else {
            InfoModeListRow ratedPhotosRow = (InfoModeListRow) adapter.getItemAtPosition(position);
            Intent intent = new Intent(getContext(), ActivityOnePhoto.class);
            // you need to inform the ActivityOnePhoto that you want to see photo at this specific position user clicked
            String pos = String.valueOf(position);
            intent.putExtra("Which Photo", pos);
            intent.putExtra("Based on", "upload");
            intent.putExtra("Which View", "RatingsMode");
            startActivity(intent);
        }
    }
*/
}
