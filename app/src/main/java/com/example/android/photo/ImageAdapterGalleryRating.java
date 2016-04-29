package com.example.android.photo;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

// Adapter for the grid view in the gallery displaying photos in order based on images ratings

public class ImageAdapterGalleryRating extends BaseAdapter {
    private static int max; // max number of photos
    private Context mContext;
    private static ImageAdapterGalleryRating mInstance;
    private Bitmap[] mThumbIds; // bitmaps to display

    public static ImageAdapterGalleryRating getInstance(Context c, PhotoModel model) {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new ImageAdapterGalleryRating(c, model);
            return mInstance;
        }
    }

    public ImageAdapterGalleryRating(Context c, PhotoModel model) {
        mContext = c;
        max = model.maxPhotos; // max number as set in model
        mThumbIds = new Bitmap[max];
        Bitmap[] update = model.getSmallBitmapsRating(); // get bitmaps from the model
        for (int i = 0; i < max; i++) {
            mThumbIds[i] = update[i];
        }
    }

// update array of bitmaps when something was changed (called from outside)
    public void updateBitmaps(PhotoModel model) {
        Bitmap[] update = model.getSmallBitmapsRating();
        for (int i = 0; i < max; i++) {
            mThumbIds[i] = update[i];
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
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(380, 380));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(mThumbIds[position]);
        return imageView;
    }
}