package com.zaitunlabs.sahabatmuslim.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.zaitunlabs.sahabatmuslim.R;
import com.zaitunlabs.sahabatmuslim.configs.AppConfig;
import com.zaitunlabs.sahabatmuslim.cores.ZaitunSplashActivity;
import com.zaitunlabs.zlcore.api.models.CheckVersionModel;
import com.zaitunlabs.sahabatmuslim.webservice.SalimWebService;
import com.zaitunlabs.zlcore.utils.CommonUtils;
import com.zaitunlabs.zlcore.utils.HttpClientUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ahmad s on 10/23/2015.
 */
public class InitApp extends ZaitunSplashActivity{
    Call<CheckVersionModel> callableObj=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMilisInFuture(200);
        setMilisInterval(100);
        setBackgroundColor(ContextCompat.getColor(this,android.R.color.white));
        setCenterIcon(R.drawable.splash_icon);
        final Class nextClass = HomeActivity.class;
        setRunnableCodeAfterSplashExpired(new Runnable() {
            @Override
            public void run() {
                if(CommonUtils.isOnline(InitApp.this)){
                    Retrofit restAdapter = new Retrofit.Builder()
                            .baseUrl(AppConfig.webServiceUrl)
                            .addConverterFactory(GsonConverterFactory.create()).client(HttpClientUtils.getHTTPClient(InitApp.this,"v1"))
                            .build();

                    SalimWebService api = restAdapter.create(SalimWebService.class);
                    callableObj = api.checkVersion(CommonUtils.getVersionCode(InitApp.this));
                    callableObj.enqueue(new Callback<CheckVersionModel>() {
                        @Override
                        public void onResponse(Call<CheckVersionModel> call, final Response<CheckVersionModel> response) {
                            if(response.body().getStatus()==AppConfig.RESPONSE_SUCCESS){
                                goToNextClass(nextClass);
                            }else if(response.body().getStatus()==AppConfig.RESPONSE_NEED_UPDATE){
                                //show dialog download opsi
                                setSplashDestroyedWhenFinish(false);
                                CommonUtils.showDialog3Option(InitApp.this,
                                        response.body().getTitle(),
                                        response.body().getMessage(),
                                        getString(R.string.download_option_dialog_init), new Runnable() {
                                            @Override
                                            public void run() {
                                                CommonUtils.openBrowser(InitApp.this, response.body().getDetail());
                                                closeSplashScreen(true);
                                            }
                                        }, getString(R.string.close_option_dialog_init), new Runnable() {
                                            @Override
                                            public void run() {
                                                closeSplashScreen(true);
                                            }
                                        }, getString(R.string.use_existing_option_dialog_init), new Runnable() {
                                            @Override
                                            public void run() {
                                                goToNextClass(nextClass);
                                            }
                                        });
                            }else{
                                goToNextClass(nextClass);
                            }
                        }

                        @Override
                        public void onFailure(Call<CheckVersionModel> call, Throwable t) {
                            goToNextClass(nextClass);
                        }
                    });

                }else{
                    goToNextClass(nextClass);
                }
            }
        });
    }

    private void goToNextClass(Class nextClass){
        setNextPageClass(nextClass);
        navigateToNextPage();
        closeSplashScreen(true);
    }

    @Override
    protected void onDestroy() {
        if(callableObj!=null){
            callableObj.cancel();
            callableObj = null;
        }
        super.onDestroy();
    }
}
