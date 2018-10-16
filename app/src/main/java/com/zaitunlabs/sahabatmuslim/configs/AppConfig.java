package com.zaitunlabs.sahabatmuslim.configs;

/**
 * Created by ahmad s on 9/27/2015.
 */
public class AppConfig {
    public static final String DEBUG_VERSION = "debug";
    public static final String RELEASE_VERSION = "release";


    public static String app_version = RELEASE_VERSION;
    public static String appLandingURL = "https://zaitunlabs.com/sahabat-muslim/";

    public static boolean isDebugVersion(){
        return app_version.equalsIgnoreCase(DEBUG_VERSION);
    }

    public static boolean isReleaseVersion(){
        return app_version.equalsIgnoreCase(RELEASE_VERSION);
    }

    public static final String mainURL = "https://sahabatmuslim.zaitunlabs.com/";
    public static final String mainBrowserURL = "https://sahabatmuslim.zaitunlabs.com/";
    public static final String webServiceUrl = "https://zaitunlabs.com/sahabatmuslim/api/";
    public static final String downloadURL = "https://zaitunlabs.com/yourls/sahabatmuslim";


    //config result connection
    public static final int RESPONSE_SUCCESS = 1;
    public static final int RESPONSE_NEED_UPDATE = 2;
    public static final int RESPONSE_FAILED = 3;
}
