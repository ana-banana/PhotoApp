package com.example.android.photo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;

// Activity to view and apply settings options: order of displaying images, managing profile information and resetting gallery

public class ActivitySettings extends AppCompatActivity {
// ********** BUTTONS FOR the ACTION BAR **********
    ImageButton addNewPhotoButton; // Button to add new photos, launches ActivityUploadPhoto
    ImageButton galleryModeButton; // Button to switch to Gallery mode, launches ActivityModelGallery
    ImageButton infoModeButton; // Button to switch to Information mode, launches ActivityModelInfo
    ImageButton settingsButton; // Button to switch to ActivitySettings
    ImageButton toProfileButton; // Button to switch to user's profile

// ********** PROGRESS BAR RELATED **********
    private ProgressBar progressBar;
    Handler handler = new Handler(){
        public void handleMessage(Message m){
            Intent intent = new Intent(ActivitySettings.this, ActivityModeGallery.class);
            startActivity(intent);
        }
    }; // starts gallery activity when reset on background thread is finished

// ********** REUSABLE **********
    private PhotoModel model;

// one row of the list view with settings option
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
        model = PhotoModel.getInstance();

///////////////////////////////////////////
// ********** SETTING ACTION BAR **********
///////////////////////////////////////////

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

///////////////////////////////////////////////////////
// ********** LIST VIEW WITH SETTING OPTIONS **********
///////////////////////////////////////////////////////

// Setting Rows data
        ArrayList<SettingsRow> settingsRows = new ArrayList<SettingsRow>();
// image and text for "reset gallery" option
        settingsRows.add(new SettingsRow("Reset Gallery. Delete all uploads and set defaults", R.drawable.reset_gallery_settings));
// image and text for managing profile option
        settingsRows.add(new SettingsRow("Manage your profile", R.drawable.profile_picture_settings));
//image and text for changing order option
        if (model.getOrder()) { // order based on upload
            settingsRows.add(new SettingsRow("You see images in upload time order. Press to switch to showing images based on ratings", R.drawable.basedonupload));
        } else { // order based on rating
            settingsRows.add(new SettingsRow("You see images in rating order. Press to switch to showing images based on upload time", R.drawable.basedonrating));
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
                    case 0: // reset option
                        Thread myThread = new Thread(new ResetGalleryThread());
                        myThread.start(); // run on background thread
                        break;
                    case 1: // manage profile option
                        Intent intent1 = new Intent(ActivitySettings.this, ActivityManageProfile.class);
                        startActivity(intent1);
                        break;
                    case 2: // changing the order of images option
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

///////////////////////////////////////////////////
// ********** BUTTONS FOR the ACTION BAR **********
///////////////////////////////////////////////////

// Button for adding new photos and its listener
        addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivitySettings.this, ActivityUploadPhoto.class);
                        startActivity(intent);
                    }
                }
        );

// Button to switch to the gallery mode
        galleryModeButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryModeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivitySettings.this, ActivityModeGallery.class);
                        startActivity(intent);

                    }
                }
        );

// Button to switch to settings activity
        settingsButton = (ImageButton) findViewById(R.id.imageButtonSettings);
        settingsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

//////////////////////////////////////////////////////////
// ********** PROGRESS BAR & BACKGROUND THREAD **********
//////////////////////////////////////////////////////////
        progressBar = (ProgressBar)findViewById(R.id.progress_bar_settings);

    }

    public void resetGallery() {
        model.resetPhotoModel();
        this.runOnUiThread(new Runnable() { // when finished stops running progress bar on a main thread
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE); // to hide progress bar when finished
            }
        });

    }

// BACKGROUND THREAD FOR RESETTING
    public class ResetGalleryThread implements Runnable {
        @Override
        public void run() {
            ActivitySettings.this.runOnUiThread(new Runnable() {
                @Override
                public void run() { // runs progress bar on the main thread
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
            resetGallery();
            handler.sendEmptyMessage(1); // to notify that process is done
        }
    }

// TO DISABLE A BACK BUTTON
    @Override
    public void onBackPressed() {
    }

}


