package com.superevilmegateam.intelliguide.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.superevilmegateam.intelliguide.R;
import com.superevilmegateam.intelliguide.adapters.LoginFormsPagerAdapter;

public class LoginFragment extends Fragment {

    private View rootView;
    private ViewPager formsPager;

    private TextView loginTabIndicator;
    private TextView registerTabIndicator;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        preparePager();
        return rootView;
    }

    private void preparePager() {
        loginTabIndicator = (TextView) rootView.findViewById(R.id.login_login_indicator);
        registerTabIndicator = (TextView) rootView.findViewById(R.id.login_register_indicator);

        loginTabIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formsPager.setCurrentItem(0,true);
            }
        });

        registerTabIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formsPager.setCurrentItem(1,true);
            }
        });

        (formsPager = (ViewPager) rootView.findViewById(R.id.login_forms_pager)).setAdapter(new LoginFormsPagerAdapter(this));
        formsPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch(position) {
                    case 0:
                        loginTabIndicator.setTextColor(getResources().getColor(R.color.white));
                        registerTabIndicator.setTextColor(getResources().getColor(R.color.main_blue));
                        break;
                    case 1:
                        registerTabIndicator.setTextColor(getResources().getColor(R.color.white));
                        loginTabIndicator.setTextColor(getResources().getColor(R.color.main_blue));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                /**
                 * To be implemented, but does not break anything like this.
                 */
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /**
                 * To be implemented, but does not break anything like this.
                 */
            }
        });

    }

}
