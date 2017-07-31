package com.momogui.measuredbypictures;


import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_PICTURES;

public class GalleryFragment extends Fragment {

    private ImageAdapter mAdapter;
    private GridView mGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_gallery, container);
        mGridView = (GridView) rootView.findViewById(R.id.image_gallery);
        mAdapter = new ImageAdapter(getActivity());
        mAdapter.setGalleryItems(loagGalleryRecentPhotos());
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                startActivity(ImageActivity.newInstance(v.getContext(), ((GalleryItem)parent.getAdapter().getItem(position)).getUriWhithFileProvider(v.getContext()), android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });

        return rootView;
    }

    private List<GalleryItem> loagGalleryRecentPhotos(){
        List<GalleryItem> galleryList =  new ArrayList<>();
        File[] dirs = getActivity().getExternalFilesDirs(DIRECTORY_PICTURES);
        for (File dir: dirs) {
            String[] images = dir.list();
            for(int i = images.length-1; i >=0 ; i--){
                String imageFullPath = dir + "/" + images[i];
                galleryList.add(new GalleryItem(BitmapFactory.decodeFile(imageFullPath), Uri.parse(imageFullPath)));
                if(galleryList.size() == 40) return galleryList; //limit of 40 pictures on the main activity
            }
        }
        return galleryList;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.setGalleryItems(loagGalleryRecentPhotos());
        mAdapter.notifyDataSetChanged();
    }
}
