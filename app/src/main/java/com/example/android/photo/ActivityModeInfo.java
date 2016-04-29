package com.example.android.photo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;


public class ActivityModeInfo extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    ImageButton addNewPhotoButton; // Button to add new photos, launches ActivityUploadPhoto
    ImageButton galleryModeButton; // Button to switch to Gallery mode, launches ActivityModelGallery
    ImageButton settingsButton; // Button to switch to ActivitySettings
    ImageButton toProfileButton; // Button to switch to user's profile

    String order;
    String startOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_photos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_mag);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.basedonupload);
        tabLayout.getTabAt(1).setIcon(R.drawable.star);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        // to make it actually fit parent:
        View vAction = LayoutInflater
                .from(actionBar.getThemedContext())
                .inflate(R.layout.action_bar, null);
        android.support.v7.app.ActionBar.LayoutParams params = new android.support.v7.app.ActionBar.LayoutParams(
                android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(vAction, params);

        Bundle extra = getIntent().getExtras();
        startOrder = extra.getString("Based on");

        addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityModeInfo.this, ActivityUploadPhoto.class);
                        intent.putExtra("Which View", "GalleryMode");
                        //intent.putExtra("Based on", order);
                        startActivity(intent);
                    }
                }
        );

       /* ImageButton ratedPhotoButton = (ImageButton) findViewById(R.id.imageButtonRating);
        ratedPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityModeInfo.this, ActivityModeInfo.class);
                        startActivity(intent);
                    }
                }
        ); */

        galleryModeButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryModeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityModeInfo.this, ActivityModeGallery.class);
                        //intent.putExtra("Based on", "uploads");
                        startActivity(intent);
                    }
                }
        );

        // Button to switch to settings view
        settingsButton = (ImageButton) findViewById(R.id.imageButtonSettings);
        settingsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityModeInfo.this, ActivitySettings.class);
                        startActivity(intent);
                    }
                }
        );

        // Button to go to user's profile
        toProfileButton = (ImageButton) findViewById(R.id.imageButtonProfile);
        toProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityModeInfo.this, ActivityProfile.class);
                        startActivity(intent);
                    }
                }
        );

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            Fragment fragment = null;
            switch (position) {
                case 0:
                    order = "upload";
                    fragment = new FragmentInfoUploads();
                    break;
                case 1:
                    order = "upload";
                    //Log.d(TAG, "Changed order to upload where should not");
                    fragment = new FragmentInfoRatings();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Upload Order";
                case 1:
                    return "Rating Order";
            }
            // return null to display only the icon
            return null;
        }

    }
}



