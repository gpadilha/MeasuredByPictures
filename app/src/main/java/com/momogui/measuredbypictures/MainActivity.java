package com.momogui.measuredbypictures;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView mAllPhotos;
    private TextView mTakePhoto;

    private final int ACTION_PICK_PHOTO_GALLERY = 99;
    private final int ACTION_PICK_PHOTO_CAMERA = 98;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //startActivity(new Intent(....));
                Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
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
                    // Callback onRequestPermissionsResult interceptadona Activity MainActivity
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, ACTION_PICK_PHOTO_CAMERA);
                } else {
                    // permission has been granted, continue as usual
                    Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
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
                if (resultCode == Activity.RESULT_OK) {
                    //pic coming from camera
                    Bitmap bitmap = null;
                    try {
                        Uri mfileUri = new Uri.Builder().build();
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mfileUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case ACTION_PICK_PHOTO_GALLERY:

                if (resultCode == Activity.RESULT_OK) {
                    //pick image from gallery
                    Uri selectedImage = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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


}