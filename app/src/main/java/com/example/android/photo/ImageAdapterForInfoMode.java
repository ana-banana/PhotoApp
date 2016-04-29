package com.example.android.photo;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;

// Adapter for the list view in the gallery ("Details" tab)

public class ImageAdapterForInfoMode extends ArrayAdapter<InfoModeListRow> {
    private Context mContext;
    public ImageAdapterForInfoMode(Context context, ArrayList<InfoModeListRow> infoModeListRow) {
        super(context, 0, infoModeListRow);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InfoModeListRow infoModeListRow = getItem(position);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.ratings_single_row, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.rating_name)).setText(infoModeListRow.getPhotoName());
        ((TextView) convertView.findViewById(R.id.rating_rating)).setText(String.valueOf(infoModeListRow.getPhotoRating()));
        ((ImageView) convertView.findViewById(R.id.imageViewBitmap)).setImageBitmap(infoModeListRow.getPhotoBitm());
        ((RatingBar) convertView.findViewById(R.id.ratingBarImageRatingView)).setRating(infoModeListRow.getPhotoRating());
        return convertView;
    }
}


