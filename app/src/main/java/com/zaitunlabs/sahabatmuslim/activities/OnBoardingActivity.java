package com.zaitunlabs.sahabatmuslim.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.zaitunlabs.sahabatmuslim.fragments.OnBoard1Fragment;
import com.zaitunlabs.sahabatmuslim.fragments.OnBoard2Fragment;
import com.zaitunlabs.sahabatmuslim.fragments.OnBoard3Fragment;
import com.zaitunlabs.zlcore.activities.BaseOnBoardingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahsai on 4/11/2018.
 */

public class OnBoardingActivity extends BaseOnBoardingActivity implements OnBoard1Fragment.OnFragmentInteractionListener,
OnBoard2Fragment.OnFragmentInteractionListener, OnBoard3Fragment.OnFragmentInteractionListener{
    @Override
    protected List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(OnBoard1Fragment.newInstance("",""));
        fragmentList.add(OnBoard2Fragment.newInstance("",""));
        fragmentList.add(OnBoard3Fragment.newInstance("",""));
        return fragmentList;
    }

    @Override
    protected void doGetStarted() {
        HomeActivity.start(this);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
