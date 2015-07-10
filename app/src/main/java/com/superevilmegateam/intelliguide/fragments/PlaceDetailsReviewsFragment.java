package com.superevilmegateam.intelliguide.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.adapters.ReviewsListAdapter;
import com.superevilmegateam.intelliguide.database.items.Place;
import com.superevilmegateam.intelliguide.database.items.Review;

import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PlaceDetailsReviewsFragment extends Fragment {

    private View rootView;
    private List<Review> reviews;
    private Place place;
    private ListView reviewsListView;

    private ReviewListListener mListener;

    public PlaceDetailsReviewsFragment() {
        // Required empty public constructor
    }

    public static PlaceDetailsReviewsFragment getInstance(Place place) {
        PlaceDetailsReviewsFragment fragment = new PlaceDetailsReviewsFragment();
        fragment.reviews = place.getReviews();
        fragment.place = place;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootView = inflater.inflate(R.layout.fragment_place_details_reviews, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
        rootView.findViewById(R.id.place_details_add_review).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAddReviewPress(place);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        initList();
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ReviewListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void initList() {
        this.reviews = this.place.getReviews();
        reviewsListView = (ListView) rootView.findViewById(R.id.place_details_reviews_list);
        ReviewsListAdapter adapter = new ReviewsListAdapter(getActivity(),reviews);
        reviewsListView.setAdapter(adapter);
    }

    public interface ReviewListListener {
        void onAddReviewPress(Place place);
    }
}
