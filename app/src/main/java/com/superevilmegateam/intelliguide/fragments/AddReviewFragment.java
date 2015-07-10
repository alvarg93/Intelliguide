package com.superevilmegateam.intelliguide.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.database.items.Place;
import com.superevilmegateam.intelliguide.database.items.Review;

public class AddReviewFragment extends Fragment {

    private Place place;
    private View rootView;

    private AddReviewListener mListener;

    public AddReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param place Place to add review to
     * @return A new instance of fragment AddReviewFragment.
     */
    public static AddReviewFragment newInstance(Place place) {
        AddReviewFragment fragment = new AddReviewFragment();
        fragment.place = place;
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return rootView = inflater.inflate(R.layout.fragment_add_review,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rootView.findViewById(R.id.add_review_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewText = ((EditText)rootView.findViewById(R.id.add_review_text)).getText().toString();
                int rating = (int) ((RatingBar) rootView.findViewById(R.id.add_review_rating)).getRating();
                if(!reviewText.isEmpty()) {
                    Review review = new Review();
                    review.setContent(reviewText);
                    review.setStars(rating);
                    review.put("aboutPlace", place);
                    mListener.onSubmitReview(place, review);
                } else {
                    Toast.makeText(getActivity(),R.string.review_empty,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddReviewListener) activity;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface AddReviewListener {
        // TODO: Update argument type and name
        public void onSubmitReview(Place place, Review review);
    }

}
