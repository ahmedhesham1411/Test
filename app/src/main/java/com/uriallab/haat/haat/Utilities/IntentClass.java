package com.uriallab.haat.haat.Utilities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uriallab.haat.haat.R;


/**
 * Created by MAHMOUD on 12/12/2018.
 */
public abstract class IntentClass {


    // go to another activity

    public static void goToActivity(Activity currentActivity, Class targetClass, Bundle value) {
        Intent intent = new Intent(currentActivity, targetClass);
        intent.putExtra("data", value);
        currentActivity.startActivity(intent);
        currentActivity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public static void toolBarSet(final Activity currentActivity, ImageView back_image, TextView text, String title) {
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentActivity.finish();
            }
        });
        text.setText(title);

    }


    public static void goToResultActivity(Activity currentActivity, Class targetClass, Bundle value) {

        Intent intent = new Intent(currentActivity, targetClass);
        intent.putExtra("data", value);
        currentActivity.startActivityForResult(intent, 111);
    }

    public static void goToActivityClearTop(Activity currentActivity, Class targetClass, Bundle value) {
        Intent intent = new Intent(currentActivity, targetClass);
        intent.putExtra("data", value);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        /*if (targetClass == SplashActivity.class) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }*/
        currentActivity.startActivity(intent);
    }

    public static void goToActivityNewTask(Activity currentActivity, Class targetClass, Bundle value) {
        Intent intent = new Intent(currentActivity, targetClass);
        intent.putExtra("data", value);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentActivity.startActivity(intent);
    }

    public static void goToStartForResult(Activity currentActivity, Class targetClass, int requestCode, Bundle value) {
        Intent intent = new Intent(currentActivity, targetClass);
        intent.putExtra("data", value);
        currentActivity.startActivityForResult(intent, requestCode);
    }

    public static void rateApp(Activity currentActivity) {
        Uri uri = Uri.parse("market://details?id=" + currentActivity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            currentActivity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            currentActivity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + currentActivity.getPackageName())));
        }
    }

    public static void goToActivityAndClear(Activity currentActivity, Class targetClass, Bundle value) {

        Intent intent = new Intent(currentActivity, targetClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("data", value);
        currentActivity.startActivity(intent);
    }

    public static void goToActivityAndClear(Context currentActivity, Class targetClass, Bundle value) {

        Intent intent = new Intent(currentActivity, targetClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("data", value);
        currentActivity.startActivity(intent);
    }

    public static void goToActivityAndClear(Activity currentActivity, Class targetClass, Bundle value, int enterAnim, int exitAnim) {

        Intent intent = new Intent(currentActivity, targetClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("data", value);
        currentActivity.startActivity(intent);
        currentActivity.overridePendingTransition(enterAnim, exitAnim);
    }

    // to open dial phone number

    public static void goTodialPhoneNumber(Activity currentActivity, String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + Utilities.mapNumber(phoneNumber)));
            if (intent.resolveActivity(currentActivity.getPackageManager()) != null) {
                currentActivity.startActivity(intent);
            }
        }catch (Exception e){
            Utilities.toastyError(currentActivity, currentActivity.getResources().getString(R.string.invalid_phone));
            e.printStackTrace();
        }
    }

    public static void goToFacebook(Activity activity, String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + id));
            activity.startActivity(intent);
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + id)));
        }

    }

    //go to fb ,twitter ,google plus ....etc

    public static void goToLink(Activity activity, String url) {
        try {
            if (url != null && !url.isEmpty()) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            }else
                Utilities.toastyError(activity, activity.getResources().getString(R.string.invalid_link));
        }catch (Exception e){
            Utilities.toastyError(activity, activity.getResources().getString(R.string.invalid_link));
            e.printStackTrace();
        }
    }

    public static void sendEmail(Activity activity, String email) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "");
        i.putExtra(Intent.EXTRA_TEXT, "");
        try {
            activity.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(activity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }


    }


    // go to other app with data
    public static void goSharedata(Activity activity, String text, String sendTo) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(sendIntent, sendTo));
    }

    // show marker on map
    public static void goMap(Context activity, Double lat, Double lng) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q=" + lat + "," + lng));
        activity.startActivity(intent);
    }

    // show marker on map
    public static void goMap(Context activity, String lat, String lng) {
        goMap(activity, Double.valueOf(lat), Double.valueOf(lng));
    }

    // go to whatsapp
    public static void goTowhatsApp(Activity activity, String smsNumber) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=+"+smsNumber+"&text=")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openAppOnGooglePlay(Activity activity) {
        final String appPackageName = activity.getPackageName();
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    // to open wifi settings and can change any action setting
    public static void goToOpenWifiSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }

    // to navigate
    public static void goToNavigate(Activity activity, Double current_lat, Double current_lng, Double destination_lat, Double destination_lng) {

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + current_lat + "," + current_lng + "&daddr=" + destination_lat + "," + destination_lng));
        activity.startActivity(intent);
    }


    //to open bluetooth
    public static void goToBluetooth(Activity activity) {

        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.bluetooth.BluetoothSettings");
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    public static void sendSms(Activity activity, String phoneNumber) {

        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                + phoneNumber)));

    }


    // to create event on mob
    public static void goToAddEvent(Activity activity, String title, String location, long begin, long end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }


    //add new contact

    public static void goToInsertContact(Activity activity, String name, String email) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }


    // send email to more than one with attachment

    public static void goTocomposeEmail(Activity activity, String[] addresses, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }
    //send email to one

    public static void goToEmail(Activity activity, String address, String subject) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", address, null));
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{address}); // String[] addresses
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        activity.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    //search any thing in any app on your mob

    public static void goToSearchWeb(Activity activity, String query) {
        if (query == null || query.isEmpty()) return;
        Intent intent = new Intent(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }

    public static void goToLocationOnMap(Activity activity, double lat, double lng) {
        if (lat != -99999 && lng != -99999) {
            String strUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + "Location" + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            activity.startActivity(intent);
        }
    }
}