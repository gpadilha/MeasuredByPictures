package com.momogui.measuredbypictures;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends Activity {

    private static final String EXTRA_PHOTO = "selected_photo";
    private static final String EXTRA_PHOTO_PATH = "selected_photo_path";

    private Button mUndoButton;
    private Button mShowResultButton;
    private ArrayList<Path> mPathList;
    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPathList = new ArrayList<>();
        setContentView(R.layout.activity_image);
        drawingView = (DrawingView) findViewById(R.id.drawing_view);
        mUndoButton = (Button) findViewById(R.id.undo_button);
        mShowResultButton = (Button) findViewById(R.id.show_result_button);

        updateButtonsVisibility();

        mUndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(mPathList.size()-1);
                drawingView.invalidate();
            }
        });

        mShowResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPathList.size() != 2){
                    Toast.makeText(v.getContext(), R.string.need_two_lines, Toast.LENGTH_LONG).show();
                }else{
                    startActivity(ResultActivity.newInstance(v.getContext(), mPathList));
                }
            }
        });
    }

    public void updateButtonsVisibility() {
        if(mPathList.size()>0) {
            mUndoButton.setVisibility(View.VISIBLE);
        }else{
            mUndoButton.setVisibility(View.GONE);
        }

        if(mPathList.size() == 2) {
            mShowResultButton.setVisibility(View.VISIBLE);
        }else{
            mShowResultButton.setVisibility(View.GONE);
        }
    }

    public static Intent newInstance(Context context, Uri photoUri, String imagePath) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(EXTRA_PHOTO, photoUri);
        intent.putExtra(EXTRA_PHOTO_PATH, imagePath);
        return intent;
    }

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

    public static String getSelectedPhotoPath(Activity activity) {
        return activity.getIntent().getStringExtra(EXTRA_PHOTO_PATH);
    }

    public List<Path> getPathList(){
        return mPathList;
    }


}
