package com.superevilmegateam.intelliguide.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.database.items.MyUser;

public class ProfileFragment extends Fragment {

    private ProfileFragmentListener mListener;
    private View rootView;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return rootView = inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) rootView.findViewById(R.id.profile_username)).setText(MyUser.getCurrentUser().getUsername());
        ((TextView) rootView.findViewById(R.id.profile_email)).setText(MyUser.getCurrentUser().getEmail());
        rootView.findViewById(R.id.profile_my_places).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onMyPlacesPress();
            }
        });
        rootView.findViewById(R.id.profile_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onLogoutPress();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ProfileFragmentListener) activity;
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

    public interface ProfileFragmentListener {
        // TODO: Update argument type and name
        void onLogoutPress();
        void onMyPlacesPress();
    }

}
