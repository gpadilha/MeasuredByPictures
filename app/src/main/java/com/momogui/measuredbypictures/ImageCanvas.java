package com.momogui.measuredbypictures;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ImageCanvas extends Thread {
    private SurfaceHolder surfaceHolder;
    private ImageView imageView;
    private boolean run = false;

    public ImageCanvas(SurfaceHolder s, ImageView m)
    {
        surfaceHolder = s;
        imageView = m;
    }

    public void setRunning(boolean r)
    {
        run = r;
    }

    public void run()
    {
        Canvas canvas;
        while(run)
        {
            canvas = null;
            try
            {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    imageView.onDraw(canvas);
                }
            }
            finally
            {
                if(canvas != null)
                {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
