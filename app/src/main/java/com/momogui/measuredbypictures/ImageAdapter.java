package com.momogui.measuredbypictures;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter{
    private Context mContext;
    private List<GalleryItem> galleryItems;

    public ImageAdapter(Context c) {
        mContext = c;
        galleryItems = new ArrayList();

        File[] dirs = mContext.getFilesDir().listFiles();
        for (File dir: dirs) {
            String[] images = dir.list();
            for(String image : images) {
                String imageFullPath = dir + "/" + image;
                galleryItems.add(new GalleryItem(BitmapFactory.decodeFile(imageFullPath), Uri.parse(imageFullPath)));
            }
        }
    }

    public int getCount() {
        return galleryItems.size();
    }

    public GalleryItem getItem(int position) {
        return galleryItems.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(GalleryItem.THUMBNAIL_SIZE, GalleryItem.THUMBNAIL_SIZE));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(galleryItems.get(position).getThumbnail());

        return imageView;
    }

}
