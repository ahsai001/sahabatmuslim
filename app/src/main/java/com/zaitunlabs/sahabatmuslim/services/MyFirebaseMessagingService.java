package com.zaitunlabs.sahabatmuslim.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zaitunlabs.sahabatmuslim.activities.HomeActivity;
import com.zaitunlabs.sahabatmuslim.R;
import com.zaitunlabs.zlcore.utils.NotificationUtils;

import java.util.Map;

/**
 * Created by ahsai on 8/22/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    final public static String smartFirebaseMessagingServiceTAG = "SmartFirebaseMessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String notifTitle = null;
        String notifBody=null;
        String clickAction=null;
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if(notification != null) {
            notifTitle = remoteMessage.getNotification().getTitle();
            notifBody = remoteMessage.getNotification().getBody();
        }

        Map<String, String> data = remoteMessage.getData();

        NotificationUtils.onMessageReceived(getBaseContext(),data, notifTitle, notifBody
        ,HomeActivity.class, null, null, R.string.app_name,R.mipmap.icon);
    }
}
