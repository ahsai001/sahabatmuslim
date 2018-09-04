package com.zaitunlabs.sahabatmuslim.cores;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.zaitunlabs.zlcore.utils.PermissionUtils;

import static android.view.View.GONE;

public class ZaitunSplashActivity extends Activity {
	CountDownTimer timer;
	RelativeLayout rellay;
	TextView tv;
	ImageView iv;

	PermissionUtils permissionUtils;

	// setting timer splash, next page class, code to run before start next activity
	int milisInFuture = 200;
	int milisInterval = 100;
	Class nextPageClass = null;
	Runnable codeToRunAfterSplashExpired = null;
	boolean isSplashDestroyedWhenFinish = true; //defaultnya destroyed
	
	
	public void setSplashDestroyedWhenFinish(boolean isSplashDestroyedWhenFinish) {
		this.isSplashDestroyedWhenFinish = isSplashDestroyedWhenFinish;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		rellay = new RelativeLayout(this);
		rellay.setBackgroundColor(Color.GRAY);
		rellay.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		//TextView
		tv = new TextView(this);
		tv.setText("This is splash screen");
		tv.setTextColor(Color.BLACK);
		LayoutParams tvParam = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tvParam.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		tv.setLayoutParams(tvParam);
		rellay.addView(tv);

		//Icon ImageVIew
		iv = new ImageView(this);
		iv.setVisibility(GONE);
		LayoutParams ivParam = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ivParam.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		iv.setLayoutParams(ivParam);
		rellay.addView(iv);

		setContentView(rellay);


		timer = new CountDownTimer(milisInFuture,milisInterval){
			@Override
			public void onFinish() {
				permissionUtils = PermissionUtils.checkPermissionAndGo(ZaitunSplashActivity.this, 1021, new Runnable() {
					@Override
					public void run() {
						if(codeToRunAfterSplashExpired != null){
							try {
								codeToRunAfterSplashExpired.run();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else{
							navigateToNextPage();
							closeSplashScreen(isSplashDestroyedWhenFinish);
						}
					}
				}, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,
						Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
						Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
			}
		};
		timer.start();
	}

	@Override
    protected void onResume() {
    	super.onResume();
    }
	
	protected void navigateToNextPage(){
		if(nextPageClass != null){
			try {
				Intent nextPageIntent = new Intent(ZaitunSplashActivity.this, nextPageClass);
				ZaitunSplashActivity.this.startActivity(nextPageIntent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void closeSplashScreen(boolean isSplashDestroyedWhenFinish){
		setSplashDestroyedWhenFinish(isSplashDestroyedWhenFinish);
		if(this.isSplashDestroyedWhenFinish) {
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		timer.cancel();
		super.onPause();
	}

	// Zaitun Splash API methods
	protected void setBackgroundImage(int resid) {
		tv.setVisibility(GONE);
		rellay.setBackgroundResource(resid);
	}

	protected void setBackgroundColor(int color){
		tv.setVisibility(GONE);
		rellay.setBackgroundColor(color);
	}

	protected void setCenterIcon(int resid){
		iv.setVisibility(View.VISIBLE);
		iv.setImageResource(resid);
	}

	protected void setRunnableCodeAfterSplashExpired(Runnable codeToRun) {
		codeToRunAfterSplashExpired = codeToRun;
	}
	
	protected void setNextPageClass(Class nextPageClass) {
		this.nextPageClass = nextPageClass;
	}
	
	protected void setMilisInFuture(int milisInFuture) {
		this.milisInFuture = milisInFuture;
	}
	
	protected void setMilisInterval(int milisInterval) {
		this.milisInterval = milisInterval;
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		permissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);
	}
}