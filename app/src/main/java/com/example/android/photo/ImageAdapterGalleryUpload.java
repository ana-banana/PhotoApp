package com.example.android.photo;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

// Adapter for the grid view in the gallery displaying photos in order based on images upload time

public class ImageAdapterGalleryUpload extends BaseAdapter {
    private static int max;  // max number of photos
    private Context mContext;
    private static ImageAdapterGalleryUpload mInstance;
    private Bitmap[] mThumbIds; // bitmaps to display

    public static ImageAdapterGalleryUpload getInstance(Context c, PhotoModel model) {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new ImageAdapterGalleryUpload(c, model);
            return mInstance;
        }
    }

    public ImageAdapterGalleryUpload(Context c, PhotoModel model) {
        mContext = c;
        max = model.maxPhotos; // as set in the model
        mThumbIds = new Bitmap[max];
        Bitmap[] update = model.getSmallBitmapsUpload(); // get bitmaps from the model
        for (int i = 0; i < max; i++) {
            mThumbIds[i] = update[i];
        }
    }

// update array of bitmaps when something was changed (called from outside)
    public void updateBitmaps(PhotoModel model) {
        Bitmap[] update = model.getSmallBitmapsUpload();
        for (int i = 0; i < max; i++) {
            mThumbIds[i] = update[i];
        }
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return position;
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