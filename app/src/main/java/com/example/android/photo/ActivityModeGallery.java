package com.example.android.photo;
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

// Activity that controls gallery mode, both for ordering based on upload and based on ratings

public class ActivityModeGallery extends AppCompatActivity {

// ********** SWIPE RELATED **********

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

// ********** BUTTONS FOR the ACTION BAR **********

    ImageButton addNewPhotoButton; // Button to add new photos, launches ActivityUploadPhoto
    //ImageButton infoModeButton; // Button to switch to Information mode, launches ActivityModelInfo
    ImageButton settingsButton; // Button to switch to ActivitySettings
    ImageButton toProfileButton; // Button to switch to user's profile

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_photos);

///////////////////////////////////////////
// ********** SETTING ACTION BAR **********
///////////////////////////////////////////

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

///////////////////////////////////////////
// ********** SETTING TABS **********
///////////////////////////////////////////
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

///////////////////////////////////////////////////
// ********** BUTTONS FOR the ACTION BAR **********
///////////////////////////////////////////////////

// Button for adding new photos and its listener
        addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityModeGallery.this, ActivityUploadPhoto.class);
                        finish();
                        startActivity(intent);
                    }
                }
        );
// Button is currently disabled
      /*  ImageButton galleryViewButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryViewButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityModeGallery.this, ActivityModeGallery.class); // change gallery views to ActivityModeGallery or comment it out
                        startActivity(intent);

                    }
                }
        );
*/
// Button to switch to settings
        settingsButton = (ImageButton) findViewById(R.id.imageButtonSettings);
        settingsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityModeGallery.this, ActivitySettings.class);
                        finish();
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
                        finish();
                        startActivity(intent);
                    }
                }
        );

    }

    // TO DISABLE A BACK BUTTON
    @Override
    public void onBackPressed() {
    }

    // TO RETURN A FRAGMENT CORRESPONDING TO ONE OF THE TABS.
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        PhotoModel model = PhotoModel.getInstance();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
// getItem is called to instantiate the fragment for the given page.
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    if (model.getOrder()) { // order based on upload
                        fragment = new FragmentGalleryUploads();
                    } else { // order based on rating
                        fragment = new FragmentGalleryRatings();
                    }
                    break;
                case 1:
                    if (model.getOrder()) { // order based on upload
                        fragment = new FragmentInfoUploads();
                    } else { // order based on rating
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

//  TO SET TEXT FOR TABS
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