package com.example.android.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class UploadPhotoActivity extends Activity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;

    ImageView imageToUpload;
    Button uploadImage;
    EditText uploadImageName;
    String uploasImageNameStr;
    PhotoInfo newPhoto;
    PhotoModel model;

    boolean cklicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_photo);

        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        imageToUpload.setImageResource(R.drawable.emptyphoto);
        uploadImage = (Button) findViewById(R.id.uploadImage);
        uploadImageName = (EditText) findViewById(R.id.editTextUploadName);

        model = PhotoModel.getInstance();

        imageToUpload.setOnClickListener(this);
        uploadImage.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageToUpload:
                // allows a gallery to be open:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                // "forResult" part allows to get back after you select an image
                break;
            case R.id.uploadImage:
                if (!cklicked) {
                    cklicked = true;
                    Bitmap myPictureUploading = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
                    uploasImageNameStr = uploadImageName.getText().toString();
                    newPhoto = new PhotoInfo(myPictureUploading, uploasImageNameStr);
                    model.addNewPhoto(newPhoto);
                    Intent intent = new Intent(UploadPhotoActivity.this, MainActivityGallery.class);
                    startActivity(intent);
                }
                break;
        }
    }

    // get called when a user has selected a picture from the gallery:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // to make sure it's the image we want and somethis was actually selected:
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData(); // uniform resource identifier
            imageToUpload.setImageURI(selectedImage);
        }
    }
/*
    private class UploadImage extends AsyncTask <Void, Void, Void> {

        Bitmap image;
        String name;

        public UploadImage(Bitmap image, String name) {
            this.image = image;
            this.name = name;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // to hold the byte representation of the image:
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream); // 100 is a quality of the image

            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ArrayList<NameValuePairs> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("image", encodedImage));
            dataToSend.add(new BasicNameValuePair("name", name));

            HttpParams httpRequestParams = getHttpRequestParams();

            HttpURLConnection client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "SavePicture.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        // what happens after code above was executed:
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private HttpParams getHttpRequestParams() {
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30); // timeout in 30 seconds
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
        return httpRequestParams;
    } */

}
