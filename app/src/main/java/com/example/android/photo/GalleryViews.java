package com.example.android.photo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by andrey on 13/04/2016.
 */
public class GalleryViews extends FragmentActivity {
    ViewPager viewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_views);

        viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyAdapterSwipe(fragmentManager));

    }
}

class MyAdapterSwipe extends FragmentPagerAdapter {

    public MyAdapterSwipe(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // return fragment at this position
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new GalleryUploadsFragment();
                break;
            case 1:
                fragment = new GalleryRatingsFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2; // number of fragments
    }
}