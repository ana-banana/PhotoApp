package com.example.android.photo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ActivitySettings extends AppCompatActivity {

    ImageButton addNewPhotoButton; // Button to add new photos, launches ActivityUploadPhoto
    ImageButton galleryModeButton; // Button to switch to Gallery mode, launches ActivityModelGallery
    ImageButton infoModeButton; // Button to switch to Information mode, launches ActivityModelInfo
    ImageButton settingsButton; // Button to switch to ActivitySettings
    ImageButton toProfileButton; // Button to switch to user's profile

    String order; /* stores current order of showing images: based on upload/ratings;
                      useful to know what order to come back to from side activities */


    public class SettingsRow {
        private String settingsDescription;
        private int settingsPic;

        public SettingsRow(String descr, int pict) {
            settingsDescription = descr;
            settingsPic = pict;
        }

        public String getSettingsDescription() {
            return settingsDescription;
        }

        public void setSettingsDescription(String descr) {
            settingsDescription = descr;
        }

        public int getSettingsPic() {
            return settingsPic;
        }

        public void setSettingsPic(int setPic) {
            settingsPic = setPic;
        }
    }

    public class SettingsRowAdapter extends ArrayAdapter<SettingsRow> {
        private Context context;

        public SettingsRowAdapter (Context context, ArrayList<SettingsRow> settingsRows) {
            super(context, 0, settingsRows);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SettingsRow settingsRow = getItem(position);
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.activity_settings_content_single_row, parent, false);
            }
            ((ImageView) convertView.findViewById(R.id.settings_imageButton)).setImageResource(settingsRow.getSettingsPic());
            ((TextView) convertView.findViewById(R.id.settings_text_description)).setText(settingsRow.getSettingsDescription());
            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_as);
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

        // Setting Rows data
        ArrayList<SettingsRow> settingsRows = new ArrayList<SettingsRow>();
        settingsRows.add(new SettingsRow("Reset Gallery. Delete all uploads and set defaults", R.drawable.reset_gallery_settings));
        settingsRows.add(new SettingsRow("Manage your profile", R.drawable.profile_picture_settings));
        final PhotoModel model = PhotoModel.getInstance();
        if (model.getOrder()) { // order based on upload
            settingsRows.add(new SettingsRow("Switch to showing images based on ratings", R.drawable.basedonrating));
        } else {
            settingsRows.add(new SettingsRow("Switch to showing images based on upload time", R.drawable.basedonupload));
        }


        // Adapter for managing the data
        SettingsRowAdapter adapter = new SettingsRowAdapter(ActivitySettings.this, settingsRows);
        // Listview component
        ListView settingListView = (ListView) findViewById(R.id.activity_settings_listView);
        settingListView.setAdapter(adapter);
        settingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                switch (position) {
                    case 0: // reset
                    case 1: // manage profile
                        Intent intent1 = new Intent(ActivitySettings.this, ActivityProfile.class);
                        startActivity(intent1);
                        break;
                    case 2: // changing the order
                        if (model.getOrder()) { // order based on upload
                            model.setOrder(false); // set order to ratings
                            Intent intent2 = getIntent();
                            finish();
                            startActivity(intent2);
                        } else {
                            model.setOrder(true); // set order to upload
                            Intent intent2 = getIntent();
                            finish();
                            startActivity(intent2);
                        }
                        break;
                }
            }
        });

        // Button for adding new photos and its listener
        addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivitySettings.this, ActivityUploadPhoto.class);
                        intent.putExtra("Which View", "GalleryMode"); // gallery mode or rating mode
                        intent.putExtra("Based on", "upload"); // which order to come back to
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
                        Intent intent = new Intent(ActivitySettings.this, ActivityModeInfo.class);
                        intent.putExtra("Based on", "upload"); // which order to come back
                        startActivity(intent);
                    }
                }
        ); */

        galleryModeButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryModeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivitySettings.this, ActivityModeGallery.class);
                        intent.putExtra("Based on", "upload"); // which order to come back
                        startActivity(intent);

                    }
                }
        );


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
                        Intent intent = new Intent(ActivitySettings.this, ActivitySettings.class);
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
                        Intent intent = new Intent(ActivitySettings.this, ActivityProfile.class);
                        startActivity(intent);
                    }
                }
        );

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
*/
/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_manage_profile) {
            Intent intentRules = new Intent(this, ActivityProfile.class);
            //intentRules.putExtra("Message", "Do not let the ball drop by bouncing it off the paddle.");
            startActivity(intentRules);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}


