package com.example.android.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by andrey on 13/04/2016.
 */
public class FragmentGalleryRatings extends Fragment implements AdapterView.OnItemClickListener {

    PhotoModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.main_gallery_view, container, false);
        View rootView = inflater.inflate(R.layout.fragment_gallery_ratings, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.fragment_gallery_ratings_gridView);
        model = PhotoModel.getInstance();
        ImageAdapterGalleryRating currentState = ImageAdapterGalleryRating.getInstance(getActivity().getApplicationContext(), model);
        currentState.updateBitmaps(model);
        gridView.setAdapter(currentState);
        gridView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.fragment_gallery_ratings_gridView) {
            if (position >= model.uploadedPhotos) {
                Toast.makeText(getContext(), "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
            } else {
                // you need to inform the ActivityOnePhoto that you want to see photo at this specific position user clicked
                Intent intent = new Intent(getContext(), ActivityOnePhoto.class);
                String pos = String.valueOf(position);
                intent.putExtra("Which Photo", pos);
                intent.putExtra("Based on", "rating");
                intent.putExtra("Which View", "GalleryMode");
                startActivity(intent);
            }
        }
    }
}

   /* PhotoModel model;
    ImageAdapterForInfoMode currentState;
    private static int maxPhotos = 8;
    Bitmap[] update;
    float[] updateRatings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_ratings_view, container, false);
        ListView photosListView = (ListView) rootView.findViewById(R.id.activity_main_ratings_listView);
        model = PhotoModel.getInstance();
        //Bitmap defBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.emptyphotofaded);
        //model.setDefaults(defBitmap);
        ArrayList<InfoModeListRow> picturesList = new ArrayList<InfoModeListRow>();
        update = new Bitmap[maxPhotos];
        update = model.getBitmaps();
        updateRatings = new float[maxPhotos];
        updateRatings = model.getPictureRatings();
        for (int i = 0; i < maxPhotos; i++) {
            picturesList.add(new InfoModeListRow(update[i], updateRatings[i]));
        }
        currentState = ImageAdapterForInfoMode.getInstance(getContext(), picturesList);
        photosListView.setAdapter(currentState);
        photosListView.setOnItemClickListener(this);
        return rootView;
    }

    public class InfoModeListRow {
        private Bitmap photoBitm;
        private float photoRating;


        public InfoModeListRow(Bitmap photoBitm, float photoRating) {
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
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        if (position >= model.uploadedPhotos) {
            Toast.makeText(getContext(), "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
        } else {
            InfoModeListRow ratedPhotosRow = (InfoModeListRow) adapter.getItemAtPosition(position);
            Intent intent = new Intent(getContext(), ActivityOnePhoto.class);
            // you need to inform the ActivityOnePhoto that you want to see photo at this specific position user clicked
            String pos = String.valueOf(position);
            intent.putExtra("Which Photo", pos);
            startActivity(intent);
        }
    }

} */
