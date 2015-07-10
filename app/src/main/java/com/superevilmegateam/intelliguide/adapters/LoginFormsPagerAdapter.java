package com.superevilmegateam.intelliguide.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.superevilmegateam.intelliguide.fragments.LoginFormFragment;
import com.superevilmegateam.intelliguide.fragments.RegisterFormFragment;

/**
 * Created by Lukasz on 2015-03-26.
 */
public class LoginFormsPagerAdapter extends FragmentPagerAdapter {

    private Fragment cntx;
    private LoginFormFragment loginFormFragment;
    private RegisterFormFragment registerFormFragment;


    public LoginFormsPagerAdapter(Fragment cntx) {
        super(cntx.getChildFragmentManager());
        this.cntx = cntx;
        loginFormFragment = new LoginFormFragment();
        registerFormFragment = new RegisterFormFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return loginFormFragment;
            case 1:
                return registerFormFragment;
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
