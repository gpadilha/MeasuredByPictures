package com.momogui.measuredbypictures;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class ImageActivity extends Activity {

    private static final String EXTRA_PHOTO = "selected_photo";
    private static final String PHOTO_FROM = "photo_from";

    private ArrayList<Path> mPathList;
    private DrawingView drawingView;
    private Paint mPaint;


//    public static String getSelectedPhotoPath(Activity activity) {
//        Uri uri = activity.getIntent().getParcelableExtra(EXTRA_PHOTO);
//
//        //if image from gallery
//        if(activity.getIntent().getExtras().getString(PHOTO_FROM, "").equals(Intent.ACTION_PICK)){
//            Cursor cursor = activity.getContentResolver().query(uri,new String[]{MediaStore.Images.Media.DATA},null,null,null);
//            int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            File f = new File(cursor.getString(index));
//            return f.getAbsolutePath();
//
//        }else{//image from camera
//            File f = new File(activity.getFilesDir(), "photos");
//            f = new File(f, uri.getLastPathSegment());
//            return f.getAbsolutePath();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_image);
        drawingView = new DrawingView(this);
        setContentView(drawingView);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);
    }

    public static Intent newInstance(Context context, Uri photoUri, String action) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(EXTRA_PHOTO, photoUri);
        intent.putExtra(PHOTO_FROM, action);
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




    public class DrawingView extends View {

        private Bitmap  mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        public DrawingView(Context context) {
            super(context);
            mPath = new Path();
            mPathList = new ArrayList<>();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            mBitmap = ImageActivity.getSelectedPhoto((Activity) getContext());
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mCanvas = new Canvas();
            mCanvas.drawBitmap(mBitmap, new Rect(0, 0, w, h), new Rect(0, 0, getWidth(), getHeight()), null);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            for(int i=0; i < mPathList.size(); i++) {
                setStrokeColor(i);
                canvas.drawPath(mPathList.get(i), mPaint);
            }
        }

        private void touchStart(float x, float y) {
            setStrokeColor(mPathList.size());
            mPath = new Path();
            mPathList.add(mPath);
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touchMove(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;
            }
        }

        private void touchUp() {
            mPath.lineTo(mX, mY);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchStart(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchMove(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touchUp();
                    invalidate();
                    if(mPathList.size() == 3){
                        mPathList.remove(2);
                        Toast.makeText(getContext(), R.string.max_lines, Toast.LENGTH_LONG).show();
                        return true;
                    }
                    break;
            }
//            PathMeasure measure1 = new PathMeasure(mPathList.get(0), false);
//            PathMeasure measure2 = new PathMeasure(mPathList.get(1), false);
//            measure1.getLength();
            return true;
        }

        private void setStrokeColor(int i){
            switch (i){
                case 0:
                    mPaint.setColor(Color.rgb(0, 226, 194));
                    break;
                default:
                    mPaint.setColor(Color.rgb(255, 26, 135));
                    break;
            }
        }
    }
}
