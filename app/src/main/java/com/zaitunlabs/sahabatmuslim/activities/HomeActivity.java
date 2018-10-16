package com.zaitunlabs.sahabatmuslim.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.zaitunlabs.sahabatmuslim.R;
import com.zaitunlabs.sahabatmuslim.configs.AppConfig;
import com.zaitunlabs.zlcore.activities.AppListActivity;
import com.zaitunlabs.zlcore.activities.BaseOnBoardingActivity;
import com.zaitunlabs.zlcore.activities.MessageListActivity;
import com.zaitunlabs.zlcore.activities.StoreActivity;
import com.zaitunlabs.zlcore.api.APIConstant;
import com.zaitunlabs.zlcore.models.InformationModel;
import com.zaitunlabs.zlcore.core.BaseActivity;
import com.zaitunlabs.zlcore.events.InfoCounterEvent;
import com.zaitunlabs.zlcore.fragments.GeneralWebViewFragment;
import com.zaitunlabs.zlcore.modules.about.AboutUs;
import com.zaitunlabs.zlcore.modules.shaum_sholat.ManageShaumSholatReminderReceiver;
import com.zaitunlabs.zlcore.services.FCMIntentService;
import com.zaitunlabs.zlcore.utils.CommonUtils;
import com.zaitunlabs.zlcore.utils.EventsUtils;

import org.greenrobot.eventbus.Subscribe;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private WebViewFragment newFragment;
    private TextView messageItemView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);

        WebViewFragment oldFragment = (WebViewFragment)getSupportFragmentManager().findFragmentByTag(GeneralWebViewFragment.FRAGMENT_TAG);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(oldFragment != null){
            transaction.remove(oldFragment);
        }
        transaction.commit();
        transaction = null;


        transaction = getSupportFragmentManager().beginTransaction();
        newFragment = new WebViewFragment();
        newFragment.setArg(1,AppConfig.mainURL, null);
        transaction.replace(R.id.home_container, newFragment, WebViewFragment.FRAGMENT_TAG);
        transaction.commit();


        ManageShaumSholatReminderReceiver.start(this);

        messageItemView = (TextView) navigationView.getMenu().
                findItem(R.id.nav_message).getActionView();

        EventsUtils.register(this);
        reCountMessage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FCMIntentService.startSending(this, APIConstant.API_APPID, false);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Subscribe
    public void onEvent(InfoCounterEvent event){
        reCountMessage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventsUtils.unregister(this);
    }

    private void reCountMessage(){
        if(messageItemView != null) {
            messageItemView.setGravity(Gravity.CENTER_VERTICAL);
            messageItemView.setTypeface(null, Typeface.BOLD);
            messageItemView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            messageItemView.setText(""+InformationModel.unreadInfoCount());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_browser) {
            CommonUtils.openBrowser(this,AppConfig.mainBrowserURL);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            /*
            AboutUs.start(this,R.mipmap.icon,0,R.string.share_title,R.string.share_body_template,
                    0,R.string.feedback_mail_to, R.string.feedback_title, R.string.feedback_body_template,
                    0,R.raw.version_change_history, true, "https://zaitunlabs.com/sahabat-muslim/");*/

            AboutUs.start(this,R.mipmap.icon,0,R.string.share_title,R.string.share_body_template,
                    0,R.string.feedback_mail_to, R.string.feedback_title, R.string.feedback_body_template,
                    0,R.raw.version_change_history, true, AppConfig.appLandingURL,
                    false, null, null,getString(R.string.feedback_mail_to),0, null,
                    R.color.colorPrimary,ContextCompat.getColor(this,android.R.color.white),ContextCompat.getColor(this,android.R.color.white),AppConfig.appLandingURL);

        } else if (id == R.id.nav_app_list) {
            //WebViewActivity.start(this, APIConstant.ZAITUNLABS_OTHER_APPS,"","","otherapps");
            AppListActivity.start(this);
        } else if (id == R.id.nav_store) {
            StoreActivity.start(this);
        } else if (id == R.id.nav_message) {
            MessageListActivity.start(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void start(Context context){
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }


    public static class WebViewFragment extends GeneralWebViewFragment{
        @Override
        protected View getCustomProgressBar() {
            return null;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            webView.addJavascriptInterface(new WebAppInterface(this.getActivity()), "sahabatmuslim");
            webView.getSettings().setUserAgentString("android");
        }

        private class WebAppInterface {
            Activity activity;

            /** Instantiate the interface and set the context */
            WebAppInterface(Activity c) {
                activity = c;
            }

            /** Show a toast from the web page */
            @JavascriptInterface
            public void showToast(String toast) {
                CommonUtils.showToast(activity,toast);
            }


            @JavascriptInterface
            public void showInfo(String title, String info) {
                CommonUtils.showInfo(activity,title,info);
            }

            @JavascriptInterface
            public void webDescription(String desc) {
                //Toast.makeText(activity.getBaseContext(), desc, Toast.LENGTH_SHORT).show();
            }

            @JavascriptInterface
            public void showActionBar(String title) {
                if(!TextUtils.isEmpty(title)){
                    ((HomeActivity)activity).getSupportActionBar().setTitle(title);
                }
                ((HomeActivity)activity).getSupportActionBar().show();
            }

            @JavascriptInterface
            public void reload(){
            }

            @JavascriptInterface
            public void shareKajian(String title, String body){
                String _title = "";

                try {
                    _title = URLDecoder.decode(title,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String _body = "";

                try {
                    _body = URLDecoder.decode(body,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                CommonUtils.shareContent(activity,"share via :",_title,_body);
            }

            @JavascriptInterface
            public void openMaps(final String latLong){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CommonUtils.navigateGMaps(activity,latLong);

                    }
                });

            }

            @JavascriptInterface
            public void remindMe(final String titleAndUstadz,final String dateX,final String dateXString,final String starttimeX,final String endtimeX,final String locationX){
            /*
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setType("vnd.android.cursor.item/event")
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, Calendar.getInstance().getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, Calendar.getInstance().getTimeInMillis()+60*60*1000)
                    .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY , false) // just included for completeness
                    .putExtra(CalendarContract.Events.TITLE, "My Awesome Event")
                    .putExtra(CalendarContract.Events.DESCRIPTION, "Heading out with friends to do something awesome.")
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, "Earth")
                    .putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=10")
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                    .putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE)
                    .putExtra(Intent.EXTRA_EMAIL, "my.friend@example.com");
            activity.startActivity(intent);
            */


                final String dateTimeString = dateXString+" "+starttimeX+" - "+endtimeX;


                CommonUtils.showDialog2Option(activity, activity.getText(R.string.title_tambah_pengingat).toString(),
                        activity.getText(R.string.message_tambah_pengingat).toString(),
                        activity.getText(R.string.lanjutkan).toString(), new Runnable() {
                            @Override
                            public void run() {
                                Date startTime = null;
                                Date endTime = null;

                                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                sf.setLenient(true);

                                try {
                                    startTime = sf.parse(dateX+" "+starttimeX);
                                } catch (ParseException e) {
                                    //do nothing
                                    e.printStackTrace();
                                }

                                try {
                                    endTime = sf.parse(dateX+" "+endtimeX);
                                } catch (ParseException e) {
                                    //do nothing
                                    e.printStackTrace();
                                }


                                String _titleAndUstadz = "";

                                try {
                                    _titleAndUstadz = URLDecoder.decode(titleAndUstadz,"UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                String _locationX = "";

                                try {
                                    _locationX = URLDecoder.decode(locationX,"UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                addEvent(activity,_titleAndUstadz,startTime,endTime,_locationX, dateTimeString);
                            }
                        }, activity.getText(R.string.batalkan).toString(), new Runnable() {
                            @Override
                            public void run() {

                            }
                        });

            }
            @JavascriptInterface
            public void callNumber(final String number){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CommonUtils.callNumber(activity,number);
                    }
                });
            }




            // Add an event to the calendar of the user.
            private void addEvent(Context context, String titleAndUstadz, Date startTime, Date endTime, String location, String dateTimeString) {
                Calendar beginTime = Calendar.getInstance();
                try {
                    ContentResolver cr = context.getContentResolver();
                    ContentValues values = new ContentValues();

                    if(startTime != null) {
                        values.put(CalendarContract.Events.DTSTART, startTime.getTime());
                    }

                    if(endTime != null) {
                        values.put(CalendarContract.Events.DTEND, endTime.getTime());
                    }

                    values.put(CalendarContract.Events.TITLE, titleAndUstadz);
                    values.put(CalendarContract.Events.EVENT_LOCATION, location);

                    values.put(CalendarContract.Events.CALENDAR_ID, 1);


                    values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
                    Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);


                    // Save the eventId into the Task object for possible future delete.
                    long _eventId = Long.parseLong(uri.getLastPathSegment());
                    // Add a 5 minute, 1 hour and 1 day reminders (3 reminders)
                    //setReminder(cr, _eventId, 5);
                    setReminder(cr, _eventId, 60);
                    //setReminder(cr, _eventId, 1440);
                    if(_eventId > 0){
                        CommonUtils.showToast(context,context.getText(R.string.message_sukses_add_reminder).toString() + " pada "+dateTimeString);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // routine to add reminders with the event
            public void setReminder(ContentResolver cr, long eventID, int timeBefore) {
                try {
                    ContentValues values = new ContentValues();
                    values.put(CalendarContract.Reminders.MINUTES, timeBefore);
                    values.put(CalendarContract.Reminders.EVENT_ID, eventID);
                    values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                    Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
                    Cursor c = CalendarContract.Reminders.query(cr, eventID,new String[]{CalendarContract.Reminders.MINUTES});
                    if (c.moveToFirst()) {
                    }
                    c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
