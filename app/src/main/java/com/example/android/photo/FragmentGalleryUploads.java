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

// Fragment that controls gallery grid type displaying based in order based on images upload time

public class FragmentGalleryUploads extends Fragment implements AdapterView.OnItemClickListener {

    PhotoModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        model = PhotoModel.getInstance();
        View rootView = inflater.inflate(R.layout.fragment_gallery_uploads, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.fragment_gallery_uploads_gridView); // grid view with photos
        ImageAdapterGalleryUpload currentState = ImageAdapterGalleryUpload.getInstance(getActivity().getApplicationContext());
        currentState.updateBitmaps();
        gridView.setAdapter(currentState);
        gridView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.fragment_gallery_uploads_gridView) {
            if (position > model.uploadedPhotos) { // empty photo but not the first one
                Toast.makeText(getContext(), "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
            } else if (position == model.uploadedPhotos) { // clicking first empty photo opens an upload photo activity
                Intent intent = new Intent(getContext(), ActivityUploadPhoto.class);
                startActivity(intent);
            } else {
                // you need to inform the ActivityOnePhoto that you want to see photo at this specific position user clicked
                Intent intent = new Intent(getContext(), ActivityOnePhoto.class);
                String pos = String.valueOf(position);
                intent.putExtra("Which Photo", pos); // passes the information about which photo on the grid was clicked
                startActivity(intent);
            }
        }
    }
}
