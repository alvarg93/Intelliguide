package com.superevilmegateam.intelliguide.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.superevilmegateam.intelliguide.R;

public class RegisterFormFragment extends Fragment {

    private OnRegisterFormListener mListener;
    private View rootView;
    private Button registerButton;

    public RegisterFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_register_form, container, false);

        registerButton = (Button) rootView.findViewById(R.id.register_form_btn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) rootView.findViewById(R.id.register_form_nick_input)).getText().toString();
                String email = ((EditText) rootView.findViewById(R.id.register_form_email_input)).getText().toString();
                String password = ((EditText) rootView.findViewById(R.id.register_form_password_input)).getText().toString();
                String password2 = ((EditText) rootView.findViewById(R.id.register_form_password_repeat)).getText().toString();
                if(password.equals(password2) && !username.isEmpty() && !email.isEmpty()) {
                    mListener.onRegister(username, email, password);
                } else {
                    Toast.makeText(getActivity(),"Podane hasła nie są jednakowe lub nie podano wszystkich danych",Toast.LENGTH_SHORT);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRegisterFormListener) activity;
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
    public interface OnRegisterFormListener {
        // TODO: Update argument type and name
        public void onFacebookRegister();
        public void onRegister(String username, String email, String pass);
    }

}
