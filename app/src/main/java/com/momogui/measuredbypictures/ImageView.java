package com.momogui.measuredbypictures;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ImageView extends SurfaceView implements SurfaceHolder.Callback{
    private ImageCanvas canvasThread;

    public ImageView(Context context) {
        super(context);
        getHolder().addCallback(this);
        canvasThread = new ImageCanvas(getHolder(), this);
        setFocusable(true);
    }

    public ImageView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        getHolder().addCallback(this);
        canvasThread = new ImageCanvas(getHolder(), this);
        setFocusable(true);
    }

    protected void onDraw(Canvas canvas) {
//        Log.d("ondraw", "ondraw");
       // Paint p = new Paint();
        Bitmap photo = ImageActivity.getSelectedPhoto((Activity)getContext());
        Rect canvasSize = new Rect(0, 0, getWidth(), getHeight());
        //canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(photo, null, canvasSize, null);
       // p.setColor(Color.RED);
        //canvas.drawLine(0, 0, 100, 100, p);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        canvasThread.setRunning(true);
        canvasThread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        canvasThread.setRunning(false);
        while (retry)
        {
            try
            {
                canvasThread.join();
                retry = false;
            }
            catch (InterruptedException e) {

            }
        }
    }
}
