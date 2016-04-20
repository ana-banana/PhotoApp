package com.example.android.photo;

/**
 * Created by andrey on 18/04/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {

    }

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Hi I've changed it");// (getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
        /*View rootView = inflater.inflate(R.layout.main_gallery_view, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.activity_main_gallery_gridView);
        PhotoModel model = PhotoModel.getInstance();
        ImageAdapterRating currentState = ImageAdapterRating.getInstance(getContext(), model);
        currentState.updateBitmaps(model);
        gridView.setAdapter(currentState);
        return rootView;*/
    }
}
