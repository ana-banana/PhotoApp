package com.example.android.photo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TabHost;

import java.util.Objects;


public class MainActivityGalleryMode extends AppCompatActivity {

    final static String TAG = "TEST";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    String order;
    String startOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_photos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_mag);
        setSupportActionBar(toolbar);

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



        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});

        Bundle extra = getIntent().getExtras();
        startOrder = extra.getString("Based on");
        Log.d(TAG, startOrder);


        ImageButton addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityGalleryMode.this, UploadPhotoActivity.class);
                        intent.putExtra("Which View", "GalleryMode");
                        String lodTag = "Order when call addNewPhoto is " + order;
                        Log.d(TAG, lodTag);
                        intent.putExtra("Based on", order);
                        startActivity(intent);
                    }
                }
        );

        ImageButton ratedPhotoButton = (ImageButton) findViewById(R.id.imageButtonRating);
        ratedPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityGalleryMode.this, MainActivityRatingMode.class);
                        intent.putExtra("Based on", "rating");
                        startActivity(intent);
                    }
                }
        );

      /*  ImageButton galleryViewButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryViewButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityGalleryMode.this, MainActivityGalleryMode.class); // change gallery views to MainActivityGalleryMode or comment it out
                        startActivity(intent);

                    }
                }
        ); */

        ImageButton resetButton = (ImageButton) findViewById(R.id.imageButtonReset);
        resetButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoModel model = PhotoModel.getInstance();
                        model.resetPhotoModel();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
        );

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } */

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            Fragment fragment = null;
            switch (position) {
                case 0:
                    order = "upload";
                    fragment = new GalleryUploadsFragment();
                    break;
                case 1:
                    order = "upload";
                    //Log.d(TAG, "Changed order to upload where should not");
                    fragment = new GalleryRatingsFragment();
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
            //return null to display only the icon
            return null;
        }
    }
}
/*
        ImageButton basedOnRating = (ImageButton) findViewById(R.id.imageButtonOrderRating);
        basedOnRating.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.showByRating();
                        Intent intent = new Intent(MainActivityGalleryMode.this, MainActivityGalleryMode.class);
                        startActivity(intent);
                    }
                }
        );

        ImageButton basedOnUpload = (ImageButton) findViewById(R.id.imageButtonOrderUpload);
        basedOnUpload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.showByTime();
                        Intent intent = new Intent(MainActivityGalleryMode.this, MainActivityGalleryMode.class);
                        startActivity(intent);
                    }
                }
        );
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.activity_main_gallery_gridView) {
            if (position >= model.uploadedPhotos) {
                Toast.makeText(MainActivityGalleryMode.this, "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
            } else {
                // you need to inform the OnePhotoActivity that you want to see photo at this specific position user clicked
                Intent intent = new Intent(MainActivityGalleryMode.this, OnePhotoActivity.class);
                String pos = String.valueOf(position);
                intent.putExtra("Which Photo", pos);
                startActivity(intent);
            }
        }
    }

    ///////////////////////////////////v
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    //public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
      /*  private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        } */

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
     /*   public static PlaceholderFragment newInstance(int sectionNumber) {
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
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    } */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
   /* public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    } */






