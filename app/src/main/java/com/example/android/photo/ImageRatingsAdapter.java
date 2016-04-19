package com.example.android.photo;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageRatingsAdapter extends ArrayAdapter<MainActivityRatingMode.RatedPhotosRow> {
    private Context mContext;
    private static int max = 8;
    private static ImageRatingsAdapter mInstance;

    private Bitmap[] mThumbIds = new Bitmap[max];
    private float[] mRatings = new float[max];

    public static ImageRatingsAdapter getInstance(Context c, ArrayList<MainActivityRatingMode.RatedPhotosRow> ratedPhotosRow) {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new ImageRatingsAdapter(c, ratedPhotosRow);
            return mInstance;
        }
    }

    public ImageRatingsAdapter (Context context, ArrayList<MainActivityRatingMode.RatedPhotosRow> ratedPhotosRow) {
        super(context, 0, ratedPhotosRow);
        this.mContext = context;

        //mContext = c;
        //Bitmap[] update = model.getBitmaps();
        //float[] updateRatings = model.getPictureRatings();
        //for (int i = 0; i < max; i++) {
        //    mThumbIds[i] = update[i];
        //    mRatings[i] = updateRatings[i];
        //}
    }

    // update array of bitmaps
    //public void updateData(PhotoModel model) {
    //    Bitmap[] update = model.getBitmaps();
    //    float[] updateRating = model.getPictureRatings();
    //    for (int i = 0; i < max; i++) {
    //        mThumbIds[i] = update[i];
    //        mRatings[i] = updateRating[i];
    //    }
    //}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainActivityRatingMode.RatedPhotosRow ratedPhotosRow = getItem(position);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.ratings_single_row, parent, false);
        }
        ((ImageView) convertView.findViewById(R.id.imageViewBitmap)).setImageBitmap(ratedPhotosRow.getPhotoBitm());
        ((RatingBar) convertView.findViewById(R.id.ratingBarImageRatingView)).setRating(ratedPhotosRow.getPhotoRating());
        return convertView;
    }
}
/*
{

    private Context mContext;
    private static ImageRatingsAdapter mInstance;
    private Bitmap[] mThumbIds = new Bitmap[max];
    private float[] mRatings = new float[max];


    public static ImageRatingsAdapter getInstance(Context c, PhotoModel model) {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new ImageRatingsAdapter(c, model);
            return mInstance;
        }
    }

    public ImageRatingsAdapter(Context c, PhotoModel model) {
        mContext = c;
        //this.def = def;
        Bitmap[] update = model.getBitmaps();
        float[] updateRatings = model.getPictureRatings();
        for (int i = 0; i < max; i++) {
            mThumbIds[i] = update[i];
            mRatings[i] = updateRatings[i];
        }
    }

    // update array of bitmaps
    public void updateData(PhotoModel model) {
        Bitmap[] update = model.getBitmaps();
        float[] updateRating = model.getPictureRatings();
        for (int i = 0; i < max; i++) {
            mThumbIds[i] = update[i];
            mRatings[i] = updateRating[i];
        }
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        RatingBar ratingBar;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            ratingBar = new RatingBar(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
            ratingBar.setLayoutParams(new GridView.LayoutParams(350, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            //imageView.setPadding(20, 20, 20, 20);
        } else {
            imageView = (ImageView) convertView;
            ratingBar = (RatingBar) convertView;
        }
        imageView.setImageBitmap(mThumbIds[position]);
        ratingBar.setRating(mRatings[position]);

        // imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LevelsButtonRow levelsButtonRow = getItem(position);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_levelbutton_single_row, parent, false);
        }
        ((ImageView) convertView.findViewById(R.id.imageViewLevelMessage)).setImageResource(levelsButtonRow.getLevelMessagePic());
        ((TextView) convertView.findViewById(R.id.levelTextView)).setText(levelsButtonRow.getLevelNumber());
        ((ImageView) convertView.findViewById(R.id.imageViewLevelWall)).setImageResource(levelsButtonRow.getLevelWallPic());
        return convertView;
    }

} */


