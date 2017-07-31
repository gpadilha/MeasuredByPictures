package com.momogui.measuredbypictures;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class DrawingView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private Path mPath;
    private List<Path> mPathList;
    private Paint mBitmapPaint;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    public DrawingView(Context context) {
        super(context);
        mPathList = ((ImageActivity)context).getPathList();
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mBitmap = ImageActivity.getSelectedPhoto((Activity) getContext());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);
    }


    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPathList = ((ImageActivity)context).getPathList();
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mBitmap = ImageActivity.getSelectedPhoto((Activity) getContext());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);
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

        canvas.drawBitmap(mBitmap, new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight()),
                new Rect(0, 0, getWidth(), getHeight()), mBitmapPaint);
        for(int i=0; i < mPathList.size(); i++) {
            setStrokeColor(i);
            canvas.drawPath(mPathList.get(i), mPaint);
        }
        ((ImageActivity)getContext()).updateButtonsVisibility();
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