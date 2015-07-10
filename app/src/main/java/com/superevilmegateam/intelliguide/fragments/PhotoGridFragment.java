package com.superevilmegateam.intelliguide.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.adapters.PhotoGridAdapter;
import com.superevilmegateam.intelliguide.database.items.GalleryObject;

import java.util.List;


public class PhotoGridFragment extends Fragment{

    private View rootView;

    private List<GalleryObject> photosList;

    private RecyclerView photoGrid;

    public PhotoGridFragment() {

    }

    public static PhotoGridFragment newInstance(List<GalleryObject> photos) {
        PhotoGridFragment fragment = new PhotoGridFragment();
        fragment.photosList = photos;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_photo_grid, container, false);

        prepareActionButton();
        prepareGrid();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void prepareActionButton() {
        /**
         * Emmpty couse of reasons.
         * No, srsly, this should do nothing.
         */
    }

    private void prepareGrid() {
        photoGrid = (RecyclerView) rootView.findViewById(R.id.photo_grid);

        photoGrid.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        photoGrid.setItemAnimator(new DefaultItemAnimator());

        PhotoGridAdapter adapter = new PhotoGridAdapter(getActivity(), photosList);
        photoGrid.setAdapter(adapter);
    }
}