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
public class GalleryUploadsFragment extends Fragment implements AdapterView.OnItemClickListener {

    PhotoModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.main_gallery_view, container, false);
        View rootView = inflater.inflate(R.layout.fragment_gallery_uploads, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.fragment_gallery_uploads_gridView);
        model = PhotoModel.getInstance();
        //Bitmap defBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.emptyphotofaded);
        //model.setDefaults(defBitmap);
        //model.showByTime();

        ImageAdapterUpload currentState = ImageAdapterUpload.getInstance(getActivity().getApplicationContext(), model);

        currentState.updateBitmaps(model);
        gridView.setAdapter(currentState);
        gridView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.fragment_gallery_uploads_gridView) {
            if (position >= model.uploadedPhotos) {
                Toast.makeText(getContext(), "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
            } else {
                // you need to inform the OnePhotoActivity that you want to see photo at this specific position user clicked
                Intent intent = new Intent(getContext(), OnePhotoActivity.class);
                String pos = String.valueOf(position);
                intent.putExtra("Which Photo", pos);
                intent.putExtra("Based on", "upload");
                intent.putExtra("Which View", "GalleryMode");
                startActivity(intent);
            }
        }
    }
}
