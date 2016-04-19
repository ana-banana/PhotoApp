package com.example.android.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by andrey on 13/04/2016.
 */
public class FragmentA extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.main_gallery_view, container, false);
        View rootView = inflater.inflate(R.layout.main_gallery_view, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.activity_main_gallery_gridView);
        PhotoModel model = PhotoModel.getInstance();
        Bitmap defBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.emptyphotofaded);
        model.setDefaults(defBitmap);
        ImageAdapter currentState = ImageAdapter.getInstance(getActivity().getApplicationContext(), model);
        currentState.updateBitmaps(model);
        gridView.setAdapter(currentState);
        return rootView;
    }
}
