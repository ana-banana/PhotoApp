package com.example.android.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.android.photo.R;

public class ImageAdapter extends BaseAdapter {
    private static int max = 8;
    private Context mContext;
    private static ImageAdapter mInstance;
    private Bitmap[] mThumbIds = new Bitmap[max];


    public static ImageAdapter getInstance(Context c, PhotoModel model) {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new ImageAdapter(c, model);
            return mInstance;
        }
    }

    public ImageAdapter(Context c, PhotoModel model) {
        mContext = c;
        //this.def = def;
        Bitmap[] update = model.getBitmaps();
        for (int i = 0; i < max; i++) {
            mThumbIds[i] = update[i];
        }
    }

    // update array of bitmaps
    public void updateBitmaps(PhotoModel model) {
        Bitmap[] update = model.getBitmaps();
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
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(390, 390));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(15, 15, 15, 15);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(mThumbIds[position]);

       // imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    /*private Integer[] mThumbIds = {
            R.drawable.emptyphoto,
            R.drawable.emptyphoto,
            R.drawable.emptyphoto,
            R.drawable.emptyphoto,
            R.drawable.emptyphoto,
            R.drawable.emptyphoto,
            R.drawable.emptyphoto,
            R.drawable.emptyphoto
    }; */



}