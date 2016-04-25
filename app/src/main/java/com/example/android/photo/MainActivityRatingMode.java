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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.Objects;


public class MainActivityRatingMode extends AppCompatActivity {

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

        ImageButton addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityRatingMode.this, UploadPhotoActivity.class);
                        intent.putExtra("Which View", "GalleryMode");
                        intent.putExtra("Based on", order);
                        startActivity(intent);
                    }
                }
        );

       /* ImageButton ratedPhotoButton = (ImageButton) findViewById(R.id.imageButtonRating);
        ratedPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivityRatingMode.class);
                        startActivity(intent);
                    }
                }
        ); */

        ImageButton galleryViewButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryViewButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivityGalleryMode.class); // change gallery views to MainActivityGalleryMode or comment it out
                        intent.putExtra("Based on", "uploads");
                        startActivity(intent);

                    }
                }
        );

        ImageButton resetButton = (ImageButton) findViewById(R.id.imageButtonReset);
        resetButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoModel model = PhotoModel.getInstance();
                        model.resetPhotoModel();
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivityGalleryMode.class);
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
                    fragment = new RatesUploadsFragment();
                    break;
                case 1:
                    order = "upload";
                    //Log.d(TAG, "Changed order to upload where should not");
                    fragment = new RatesRatingsFragment();
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

/*implements AdapterView.OnItemClickListener {

    final static String TAG = "TEST";

    ListView photosListView;
    PhotoModel model;
    ImageRatingsAdapter currentState;
    private static int maxPhotos = 8;
    Bitmap[] update;
    float[] updateRatings;

    public class RatedPhotosRow {
        private Bitmap photoBitm;
        private float photoRating;


        public RatedPhotosRow(Bitmap photoBitm, float photoRating) {
            this.photoBitm = photoBitm;
            this.photoRating = photoRating;
        }

        public float getPhotoRating() {
            return photoRating;
        }

        public void setPhotoRating(float photoRating) {
            this.photoRating = photoRating;
        }

        public Bitmap getPhotoBitm() {
            return photoBitm;
        }

        public void setPhotoBitm(Bitmap photoBitm) {
            this.photoBitm = photoBitm;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Created MainActivityRatingMode");

        setContentView(R.layout.main_ratings_view);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_mar);
        setSupportActionBar(myToolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar);

        photosListView = (ListView) findViewById(R.id.activity_main_ratings_listView);
        model = PhotoModel.getInstance();

        ArrayList<RatedPhotosRow> picturesList = new ArrayList<RatedPhotosRow>();
        update = new Bitmap[maxPhotos];
        update = model.getBitmaps();
        Log.d(TAG, "Updated Bitmaps");
        updateRatings = new float[maxPhotos];
        updateRatings = model.getPictureRatings();
        Log.d(TAG, "Updated Ratings");
        for (int i = 0; i < maxPhotos; i++) {
            picturesList.add(new RatedPhotosRow(update[i], updateRatings[i]));
        }

        currentState = ImageRatingsAdapter.getInstance(this, picturesList);

        //currentState.updateBitmaps(model);

        photosListView.setAdapter(currentState);
        photosListView.setOnItemClickListener(this);

        ImageButton backToMain = (ImageButton) findViewById(R.id.imageButtonReset);
        backToMain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        ImageButton addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityRatingMode.this, UploadPhotoActivity.class);
                        intent.putExtra("Which View", "RatingMode");
                        startActivity(intent);
                    }
                }
        );

        /*ImageButton ratedPhotoButton = (ImageButton) findViewById(R.id.imageButtonRating);
        ratedPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivityRatingMode.class);
                        startActivity(intent);
                    }
                }
        );

        ImageButton galleryViewButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryViewButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivityGalleryMode.class);
                        startActivity(intent);

                    }
                }
        );

        ImageButton basedOnRating = (ImageButton) findViewById(R.id.imageButtonOrderRating);
        basedOnRating.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.showByRating();
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivityRatingMode.class);
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
                        Intent intent = new Intent(MainActivityRatingMode.this, MainActivityRatingMode.class);
                        startActivity(intent);
                    }
                }
        );
    }
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        if (position >= model.uploadedPhotos) {
            Toast.makeText(MainActivityRatingMode.this, "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
        } else {
            RatedPhotosRow ratedPhotosRow = (RatedPhotosRow) adapter.getItemAtPosition(position);
            Intent intent = new Intent(MainActivityRatingMode.this, OnePhotoActivity.class);
            // you need to inform the OnePhotoActivity that you want to see photo at this specific position user clicked
            String pos = String.valueOf(position);
            intent.putExtra("Which Photo", pos);
            startActivity(intent);
        }
    }
}
/*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.activity_main_ratings_listView) {
            if (position >= model.uploadedPhotos) {
                Toast.makeText(MainActivityRatingMode.this, "Sorry, there is no photo here", Toast.LENGTH_SHORT).show();
            } else {
                // you need to inform the OnePhotoActivity that you want to see photo at this specific position user clicked
                Intent intent = new Intent(MainActivityRatingMode.this, OnePhotoActivity.class);
                String pos = String.valueOf(position);
                intent.putExtra("Which Photo", pos);
                startActivity(intent);
            }
        }
    }



} */


