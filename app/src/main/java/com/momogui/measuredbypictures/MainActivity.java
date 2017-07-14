package com.momogui.measuredbypictures;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView mAllPhotos;
    private TextView mTakePhoto;
    private Uri mPhotoUri;

    private final int ACTION_PICK_PHOTO_GALLERY = 99;
    private final int ACTION_PICK_PHOTO_CAMERA = 98;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAllPhotos = (TextView) findViewById(R.id.all_photos);
        mTakePhoto = (TextView) findViewById(R.id.take_photo);

        mAllPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), ACTION_PICK_PHOTO_GALLERY);
            }
        });

        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Camera permission required for Marshmallow version
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, ACTION_PICK_PHOTO_CAMERA);
                } else {
                    Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    mPhotoUri = FileProvider.getUriForFile(MainActivity.this, "com.momogui.measuredbypictures.fileprovider" , getOutputMediaFile());
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                    startActivityForResult(captureIntent, ACTION_PICK_PHOTO_CAMERA);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ACTION_PICK_PHOTO_CAMERA:
//                if (resultCode == Activity.RESULT_OK) {
//                    //pic coming from camera
//                    Bitmap bitmap = null;
//                    try {
//                        Uri mfileUri = new Uri.Builder().build();
//                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mfileUri);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                break;

            case ACTION_PICK_PHOTO_GALLERY:

                if (resultCode == Activity.RESULT_OK) {
                    startActivity(ImageActivity.newInstance(this, mPhotoUri));
                    //Uri selectedImage = data.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
//                    try {
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mPhotoUri);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//                    // Get the cursor
//                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                    // Move to first row
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String imgDecodableString = cursor.getString(columnIndex);
//                    cursor.close();
//                    bitmap = BitmapFactory.decodeFile(imgDecodableString);

                }
                break;
        }

    }

    private File getOutputMediaFile(){
        File mediaStorageDir = new File(MainActivity.this.getFilesDir(), "photos");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }


}
