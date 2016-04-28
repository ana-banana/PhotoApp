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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

// Activity that controls gallery mode, both for ordering based on upload and based on ratings
public class ActivityModeGallery extends AppCompatActivity {
    final static String TAG = "TEST";
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    ImageButton addNewPhotoButton; // Button to add new photos, launches ActivityUploadPhoto
    //ImageButton infoModeButton; // Button to switch to Information mode, launches ActivityModelInfo
    ImageButton settingsButton; // Button to switch to ActivitySettings
    ImageButton toProfileButton; // Button to switch to user's profile

    String order; /* stores current order of showing images: based on upload/ratings;
                      useful to know what order to come back to from side activities */

    String startOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_photos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_mag);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        // to set a custom action bar:
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        // to make it actually fit parent:
        //actionBar.setCustomView(R.layout.action_bar);
        View vAction = LayoutInflater
                .from(actionBar.getThemedContext())
                .inflate(R.layout.action_bar, null);
        android.support.v7.app.ActionBar.LayoutParams params = new android.support.v7.app.ActionBar.LayoutParams(
                android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(vAction, params);
        /* SectionPagerAdapter defined at the end. Adapter that will return a fragment for each of the three
           primary sections of the activity: */
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter:
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        // set tab icons:
        //tabLayout.getTabAt(0).setIcon(R.drawable.basedonupload);
        //tabLayout.getTabAt(1).setIcon(R.drawable.star);

       // Bundle extra = getIntent().getExtras();
       // startOrder = extra.getString("Based on");
       // Log.d(TAG, startOrder);

        // Button for adding new photos and its listener
        addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityModeGallery.this, ActivityUploadPhoto.class);
                      //  intent.putExtra("Which View", "GalleryMode"); // gallery mode or rating mode
                      //  String lodTag = "Order when call addNewPhoto is " + order;
                      //  Log.d(TAG, lodTag);
                      //  intent.putExtra("Based on", order); // which order to come back to
                        startActivity(intent);
                    }
                }
        );

/*
        // Button for switching to ratings mode and its listener
        infoModeButton = (ImageButton) findViewById(R.id.imageButtonRating);
        infoModeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityModeGallery.this, ActivityModeInfo.class);
                     //   intent.putExtra("Based on", "rating"); // which order to come back
                        startActivity(intent);
                    }
                }
        );
*/

      /*  ImageButton galleryViewButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryViewButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityModeGallery.this, ActivityModeGallery.class); // change gallery views to ActivityModeGallery or comment it out
                        startActivity(intent);

                    }
                }
        ); */


        // Button to reset the gallery (delete all the uploaded photos, set defaults) and its listener
        settingsButton = (ImageButton) findViewById(R.id.imageButtonSettings);
        settingsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //    PhotoModel model = PhotoModel.getInstance();
                    //    model.resetPhotoModel();
                    //    Intent intent = getIntent();
                    //    finish();
                    //    startActivity(intent);
                        Intent intent = new Intent(ActivityModeGallery.this, ActivitySettings.class);
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
                        Intent intent = new Intent(ActivityModeGallery.this, ActivityProfile.class);
                        startActivity(intent);
                    }
                }
        );

    }

    // to return a fragment corresponding to one of the tabs.
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        PhotoModel model = PhotoModel.getInstance();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        /* getItem is called to instantiate the fragment for the given page.
           Return a PlaceholderFragment (defined as a static inner class below). */
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    if (model.getOrder()) { // order based on upload
                        fragment = new FragmentGalleryUploads();
                    } else {
                        fragment = new FragmentGalleryRatings();
                    }
                    break;
                case 1:
                    if (model.getOrder()) { // order based on upload
                        fragment = new FragmentInfoUploads();
                    } else {
                        fragment = new FragmentInfoRatings();
                    }
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
                    return "Gallery";
                case 1:
                    return "Photo Details";
            }
            //return null to display only the icons:
            return null;
        }
    }
}