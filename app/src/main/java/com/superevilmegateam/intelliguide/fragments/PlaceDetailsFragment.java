package com.superevilmegateam.intelliguide.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.software.shell.fab.ActionButton;
import com.superevilmegateam.intelliguide.MainActivity;
import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.adapters.PlaceDetailsPagerAdapter;
import com.superevilmegateam.intelliguide.database.Database;
import com.superevilmegateam.intelliguide.database.items.Place;
import com.superevilmegateam.intelliguide.views.LockableViewPager;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PlaceDetailsFragment extends Fragment {

    private View rootView;

    private Place place;
    private ImageView mainImage;
    private TextView titleTextView;
    private View editBtn;
    private LockableViewPager viewPager;
    private ActionButton verifyBtn;
    private boolean editMode;
    private PlaceDetailsListener mListener;

    public PlaceDetailsFragment() {
        // Required empty public constructor
    }

    public static PlaceDetailsFragment getInstance(Place place, boolean editMode) {
        PlaceDetailsFragment fragment = new PlaceDetailsFragment();
        fragment.place = place;
        fragment.editMode = editMode;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootView = inflater.inflate(R.layout.fragment_place_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
        prepareModeratorMode(((MainActivity) getActivity()).isModeratorModeOn());
    }

    private void findViews() {
        mainImage = (ImageView) rootView.findViewById(R.id.place_details_image);
        if (place.getPreviewPhoto() != null)
            ImageLoader.getInstance().displayImage(place.getPreviewPhoto(), mainImage);

        titleTextView = (TextView) rootView.findViewById(R.id.place_details_title);
        titleTextView.setText(place.getName());

        editBtn = rootView.findViewById(R.id.place_details_edit_btn);
        if(editMode) {
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onEditBtnPress(place);
                }
            });
        } else {
            editBtn.setVisibility(View.GONE);
        }

        viewPager = (LockableViewPager) rootView.findViewById(R.id.place_details_pager);
        PlaceDetailsPagerAdapter adapter = new PlaceDetailsPagerAdapter(this, this.place);
        viewPager.setAdapter(adapter);

        verifyBtn = (ActionButton) rootView.findViewById(R.id.place_details_verify_btn);
        verifyBtn.setImageResource(place.getBoolean("verified") ? R.drawable.close : R.drawable.tick);


    }

    private void prepareModeratorMode(boolean moderatorMode) {
        if (moderatorMode) {
            verifyBtn.setVisibility(View.VISIBLE);
            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Database.getInstance().changeVerificationStatus(place, new Database.ObjectUpdateListener() {
                        @Override
                        public void onUpdateComplete() {
                            verifyBtn.setImageResource(place.getBoolean("verified") ? R.drawable.close : R.drawable.tick);
                        }
                    });
                }
            });

            //viewPager.lock();
        } else {
            verifyBtn.setVisibility(View.GONE);
            //viewPager.unlock();
        }
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (PlaceDetailsListener) activity;
        } catch (ClassCastException e) {
            Log.e("Fragment", "Could not cast to PlaceSelectionListener");
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface PlaceDetailsListener {
        void onEditBtnPress(Place place);
    }
}
