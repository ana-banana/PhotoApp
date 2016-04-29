package com.example.android.photo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityProfile extends AppCompatActivity {

    TextView profileName; // name of the user
    TextView numberOfFriends; // how many friends has the user
    TextView numberOfPhotos; // how many photos did user uploaded

    ImageView profilePhoto; // photo of the user

    GridView usersFriends; // photos and names of user's friends

    ImageView seeGallery; // "button" to get back to the gallery

    ImageButton addNewPhotoButton; // Button to add new photos, launches ActivityUploadPhoto
    ImageButton galleryModeButton; // Button to switch to Gallery mode, launches ActivityModelGallery
    ImageButton infoModeButton; // Button to switch to Information mode, launches ActivityModelInfo
    ImageButton settingsButton; // Button to switch to ActivitySettings
    ImageButton toProfileButton; // Button to switch to user's profile

    public class FriendsGrid {
        private String friendName;
        private Bitmap friendPicture;

        public FriendsGrid(String fName, Bitmap fPicture) {
            friendName = fName;
            friendPicture = fPicture;
        }

        public String getFriendName() {
            return friendName;
        }

        public void setFriendName(String fName) {
            friendName = fName;
        }

        public Bitmap getFriendPicture() {
            return friendPicture;
        }

        public void setFriendPicture(Bitmap fPicture) {
            friendPicture = fPicture;
        }

    }

    public class FriendGridAdapter extends ArrayAdapter<FriendsGrid> {
        private Context context;

        public FriendGridAdapter(Context context, ArrayList<FriendsGrid> friendsGrids) {
            super(context, 0, friendsGrids);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FriendsGrid friendsGrid = getItem(position);
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.friend_single_grid, parent, false);
            }
            ((ImageView) convertView.findViewById(R.id.friend_picture_single_grid)).setImageBitmap(friendsGrid.getFriendPicture());
            ((TextView) convertView.findViewById(R.id.friend_name_single_grid)).setText(friendsGrid.getFriendName());
            return convertView;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfileModel profile = ProfileModel.getInstance();
        // set profile specific information

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_ap);
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

        profileName = (TextView) findViewById(R.id.profile_name);
        profileName.setText(profile.myProfile.getProfName());
        numberOfFriends = (TextView) findViewById(R.id.how_many_friends);
        String howManyFriends = "You have " + profile.getStringNumberOfFriends() + " friends";
        numberOfFriends.setText(howManyFriends);
        profilePhoto = (ImageView) findViewById(R.id.my_profile_photo);
        profilePhoto.setImageBitmap(profile.myProfile.getProfPicture());

        numberOfPhotos = (TextView)findViewById(R.id.go_to_gallery);
        PhotoModel model = PhotoModel.getInstance();
        String plural = " photo";
        int uploaded = model.uploadedPhotos;
        if (uploaded != 1) {plural = " photos";}
        String numPhotos = "You uploaded " + String.valueOf(model.uploadedPhotos) + plural;
        numberOfPhotos.setText(numPhotos);

        // set friends gridView
        ArrayList<FriendsGrid> friendsGrids = new ArrayList<FriendsGrid>();
        for (int i = 0; i < profile.getNumberOfFriends(); i++) {
            ProfileInfo thisFriend = profile.friends.get(i);
            friendsGrids.add(new FriendsGrid(thisFriend.getProfName(), thisFriend.getProfPicture()));
        }
        // Adapter for managing the data
        FriendGridAdapter adapter = new FriendGridAdapter(ActivityProfile.this, friendsGrids);
        // Gridview component to show data
        usersFriends = (GridView) findViewById(R.id.profile_friends_gridview);
        usersFriends.setAdapter(adapter);
        usersFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                //FriendsGrid friendsGrid = (FriendsGrid) adapter.getItemAtPosition(position);
                Toast.makeText(ActivityProfile.this, "Can not find this profile", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(ActivityProfile.this, FriendsProfile.class);
                //startActivity(intent);
            }
        });

        seeGallery = (ImageView) findViewById(R.id.imageViewMyPhotos);
        seeGallery.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityProfile.this, ActivityModeGallery.class);
                        startActivity(intent);
                    }
                }
        );

        LinearLayout manage = (LinearLayout) findViewById(R.id.profile_button_manage);
        manage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityProfile.this, ActivityManageProfile.class); // change settings to managing activity
                        startActivity(intent);
                    }
                });

        // Button for adding new photos and its listener
        addNewPhotoButton = (ImageButton) findViewById(R.id.imageButtonAddPhoto);
        addNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityProfile.this, ActivityUploadPhoto.class);
                        intent.putExtra("Which View", "GalleryMode"); // gallery mode or rating mode
                        intent.putExtra("Based on", "upload"); // which order to come back to
                        startActivity(intent);
                    }
                }
        );

        // Button for switching to ratings mode and its listener
        infoModeButton = (ImageButton) findViewById(R.id.imageButtonRating);
        infoModeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityProfile.this, ActivityModeInfo.class);
                        intent.putExtra("Based on", "rating"); // which order to come back
                        startActivity(intent);
                    }
                }
        );

        galleryModeButton = (ImageButton) findViewById(R.id.imageButtonGridView);
        galleryModeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityProfile.this, ActivityModeGallery.class);
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
                        Intent intent = new Intent(ActivityProfile.this, ActivitySettings.class);
                        startActivity(intent);
                    }
                }
        );

/*
        // Button to go to user's profile
        toProfileButton = (ImageButton) findViewById(R.id.imageButtonProfile);
        toProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ActivityProfile.this, ActivityProfile.class);
                        startActivity(intent);
                    }
                }
        );

    }
*/
    }
}

