package com.zaitunlabs.sahabatmuslim.cores;

import com.zaitunlabs.zlcore.api.APIConstant;
import com.zaitunlabs.zlcore.api.models.AppListDataModel;
import com.zaitunlabs.zlcore.api.models.AppListModel;
import com.zaitunlabs.zlcore.api.models.AppListPagingModel;
import com.zaitunlabs.zlcore.api.models.InformationModel;
import com.zaitunlabs.zlcore.api.models.StoreDataModel;
import com.zaitunlabs.zlcore.api.models.StoreModel;
import com.zaitunlabs.zlcore.api.models.StorePagingModel;
import com.zaitunlabs.zlcore.core.BaseApplication;

/**
 * Created by ahsai on 3/15/2018.
 */

public class BaseApp extends BaseApplication {
    @Override
    public void onCreate() {
        addDBModelClass(InformationModel.class);
        addDBModelClass(AppListModel.class);
        addDBModelClass(AppListDataModel.class);
        addDBModelClass(AppListPagingModel.class);
        addDBModelClass(StoreModel.class);
        addDBModelClass(StoreDataModel.class);
        addDBModelClass(StorePagingModel.class);

        APIConstant.setApiAppid("1");
        APIConstant.setApiKey("1234fsdfgfsfdgsdfsdfgsdfg");
        APIConstant.setApiVersion("v1");
        super.onCreate();
    }


}
