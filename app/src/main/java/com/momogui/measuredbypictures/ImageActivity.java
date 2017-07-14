package com.momogui.measuredbypictures;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

public class ImageActivity extends AppCompatActivity {

    private static final String EXTRA_PHOTO = "selected_photo";


    public static Bitmap getSelectedPhoto(Activity activity) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    activity.getContentResolver(),
                    (Uri) activity.getIntent().getParcelableExtra(EXTRA_PHOTO));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
    }

    public static Intent newInstance(Context context, Uri photoUri) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(EXTRA_PHOTO, photoUri);
        return intent;
    }
}
