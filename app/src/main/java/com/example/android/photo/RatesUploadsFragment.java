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

/**
 * Created by andrey on 19/04/2016.
 */
public class RatesUploadsFragment extends Fragment implements AdapterView.OnItemClickListener {

    PhotoModel model;
    ImageRatingsAdapter currentState;
    private static int maxPhotos = 8;
    Bitmap[] update;
    float[] updateRatings;
    RatedPhotosRow item;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.main_gallery_view, container, false);
        View rootView = inflater.inflate(R.layout.fragment_rates_uploads, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.fragment_rates_uploads_listView);
        model = PhotoModel.getInstance();
        //Bitmap defBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.emptyphotofaded);
        //model.setDefaults(defBitmap);
        //model.showByTime();
        ArrayList<RatedPhotosRow> picturesList = new ArrayList<RatedPhotosRow>();
        update = new Bitmap[maxPhotos];
        update = model.getBitmapsUpload();
        updateRatings = new float[maxPhotos];
        updateRatings = model.getPictureRatingsUpload();
        for (int i = 0; i < maxPhotos; i++) {
            item = new RatedPhotosRow(update[i], updateRatings[i]);
            picturesList.add(item);
        }
        currentState = ImageRatingsAdapter.getInstance(getContext(), picturesList);
        //currentState.updateBitmaps(model);
        listView.setAdapter(currentState);
        listView.setOnItemClickListener(this);
        return rootView;
    }


    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        if (position >= model.uploadedPhotos) {
            Toast.makeText(getContext(), "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
        } else {
            RatedPhotosRow ratedPhotosRow = (RatedPhotosRow) adapter.getItemAtPosition(position);
            Intent intent = new Intent(getContext(), OnePhotoActivity.class);
            // you need to inform the OnePhotoActivity that you want to see photo at this specific position user clicked
            String pos = String.valueOf(position);
            intent.putExtra("Which Photo", pos);
            intent.putExtra("Based on", "upload");
            intent.putExtra("Which View", "RatingsMode");
            startActivity(intent);
        }
    }

}
