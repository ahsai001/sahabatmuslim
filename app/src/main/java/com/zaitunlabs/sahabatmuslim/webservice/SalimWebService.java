package com.zaitunlabs.sahabatmuslim.webservice;

import com.zaitunlabs.zlcore.api.models.CheckVersionModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by ahmad s on 10/23/2015.
 */
public interface SalimWebService {
    @FormUrlEncoded
    @POST("checkversion.php")
    Call<CheckVersionModel> checkVersion(@Field("versioncode") int versioncode);
}
