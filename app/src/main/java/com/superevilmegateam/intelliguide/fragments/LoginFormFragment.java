package com.superevilmegateam.intelliguide.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.superevilmegateam.intelliguide.R;

public class LoginFormFragment extends Fragment {

    private OnLoginFormListener mListener;
    private View rootView;
    private Button loginButton;

    public LoginFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login_form, container, false);


        loginButton = (Button) rootView.findViewById(R.id.login_form_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) rootView.findViewById(R.id.login_form_email_input)).getText().toString();
                String password = ((EditText) rootView.findViewById(R.id.login_form_password_input)).getText().toString();
                mListener.onLogin(email,password);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLoginFormListener) activity;
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
    public interface OnLoginFormListener {
        // TODO: Update argument type and name
        public void onLogin(String username, String password);
        public void onFacebookLogin();
    }

}
