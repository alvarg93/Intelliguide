package com.superevilmegateam.intelliguide.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superevilmegateam.intelliguide.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PlaceDetailsDescriptionFragment extends Fragment {

    private View rootView;
    private String description;
    private TextView descTextView;

    public PlaceDetailsDescriptionFragment() {
        // Required empty public constructor
    }

    public static PlaceDetailsDescriptionFragment getInstance(String description) {
        PlaceDetailsDescriptionFragment fragment = new PlaceDetailsDescriptionFragment();
        fragment.description = description;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootView = inflater.inflate(R.layout.fragment_place_details_description, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
    }

    private void findViews() {
        descTextView = (TextView) rootView.findViewById(R.id.place_details_description);
        descTextView.setText(description);
    }
}
