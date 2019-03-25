package com.zaitunlabs.salim.activities;

import android.os.Bundle;

import com.zaitunlabs.salim.R;
import com.zaitunlabs.zlcore.activities.BaseSplashActivity;
import com.zaitunlabs.zlcore.api.APIConstant;
import com.zaitunlabs.zlcore.utils.CommonUtils;


/**
 * Created by ahmad s on 10/23/2015.
 */

public class InitApp extends BaseSplashActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackgroundPaneColor(android.R.color.white);
        setImageIcon(R.drawable.splash_icon);
        setBottomTextView(getString(R.string.app_name)+" v"+ CommonUtils.getVersionName(this), android.R.color.black);
    }

    @Override
    protected String getCheckVersionUrl() {
        return APIConstant.API_CHECK_VERSION;
    }

    @Override
    protected boolean doNextAction() {
        HomeActivity.start(this);
        return true;
    }

    @Override
    protected int getMinimumSplashTimeInMS() {
        return 3000;
    }
}