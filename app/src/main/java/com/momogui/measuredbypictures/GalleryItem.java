package com.momogui.measuredbypictures;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;

public class GalleryItem {

    private Bitmap picture;
    private Uri pictureUri;
    private Bitmap thumbnail;
    public static final int THUMBNAIL_SIZE = 250;

    public GalleryItem (Bitmap picture, Uri pictureUri){
        this.picture = picture;
        this.pictureUri = pictureUri;
        this.thumbnail = Bitmap.createScaledBitmap(this.picture, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public Uri getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(Uri pictureUri) {
        this.pictureUri = pictureUri;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Uri getUriWhithFileProvider(Context context){
        return FileProvider.getUriForFile(context, "com.momogui.measuredbypictures.fileprovider" , new File(this.pictureUri.getPath()));
    }
}
