package com.momogui.measuredbypictures;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class GalleryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_gallery, container);
        GridView gridview = (GridView) rootView.findViewById(R.id.image_gallery);
        gridview.setAdapter(new ImageAdapter(getActivity()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                startActivity(ImageActivity.newInstance(v.getContext(), ((GalleryItem)parent.getAdapter().getItem(position)).getUriWhithFileProvider(v.getContext()), android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });

        return rootView;
    }

}
