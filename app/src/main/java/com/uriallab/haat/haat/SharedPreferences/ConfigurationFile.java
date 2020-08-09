package com.uriallab.haat.haat.SharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;


/**
 * Created by MAHMOUD on 12/12/2018.
 */

public class ConfigurationFile {
    private static final String LANGUAGE_KEY = "languageKey";

    private static final String CHAT_STATUS = "chat_status";
    private static final String DRIVER_CHAT_STATUS = "driver_chat_status";
    private static final String NEW_ORDER_STATUS = "NEW_ORDER_status";
    private static final String NEW_OFFER_STATUS = "NEW_OFFER_STATUS";
    private static final String OFFERS_STATUS = "OFFERS_STATUS";
    private static final String Active_Journey_STATUS = "Active_Journey_STATUS";

    private static SharedPreferences configFile;

    public static String getCurrentLanguage(Context context) {
        initConfigSharedPreference(context);
        return configFile.getString(LANGUAGE_KEY, Locale.getDefault().getLanguage());
    }

    public static void setCurrentLanguage(Context context, String language) {
        initConfigSharedPreference(context);

        if (language.equals(""))
            language = Locale.getDefault().getLanguage();

        if (language.equals("en_US"))
            language = "en_US";

        if (language.equals("ar"))
            language = "ar";

        try {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config, null);
        } catch (NullPointerException a) {
            a.printStackTrace();
        } catch (RuntimeException a) {
            a.printStackTrace();
        }

        SharedPreferences.Editor editor = configFile.edit();
        editor.putString(LANGUAGE_KEY, language);
        editor.apply();
    }

    public static void setIsChatActive(Activity activity, boolean isActive) {
        initConfigSharedPreference(activity);
        SharedPreferences.Editor editor = configFile.edit();
        editor.putBoolean(CHAT_STATUS, isActive);
        editor.apply();
    }

    public static boolean getIsChatActive(Context activity) {
        initConfigSharedPreference(activity);
        return configFile.getBoolean(CHAT_STATUS, false);
    }

    public static void setIsDriverChatActive(Activity activity, boolean isActive) {
        initConfigSharedPreference(activity);
        SharedPreferences.Editor editor = configFile.edit();
        editor.putBoolean(DRIVER_CHAT_STATUS, isActive);
        editor.apply();
    }

    public static boolean getIsDriverChatActive(Context activity) {
        initConfigSharedPreference(activity);
        return configFile.getBoolean(DRIVER_CHAT_STATUS, false);
    }

    public static void setIsNewOrderActive(Activity activity, boolean isActive) {
        initConfigSharedPreference(activity);
        SharedPreferences.Editor editor = configFile.edit();
        editor.putBoolean(NEW_ORDER_STATUS, isActive);
        editor.apply();
    }

    public static boolean getIsNewOrderActive(Context activity) {
        initConfigSharedPreference(activity);
        return configFile.getBoolean(NEW_ORDER_STATUS, false);
    }

    public static void setIsNewOfferActive(Activity activity, boolean isActive) {
        initConfigSharedPreference(activity);
        SharedPreferences.Editor editor = configFile.edit();
        editor.putBoolean(NEW_OFFER_STATUS, isActive);
        editor.apply();
    }

    public static boolean getIsNewOfferActive(Context activity) {
        initConfigSharedPreference(activity);
        return configFile.getBoolean(NEW_OFFER_STATUS, false);
    }

    public static void setIsOffersActive(Activity activity, boolean isActive) {
        initConfigSharedPreference(activity);
        SharedPreferences.Editor editor = configFile.edit();
        editor.putBoolean(OFFERS_STATUS, isActive);
        editor.apply();
    }

    public static boolean getIsOffersActive(Context activity) {
        initConfigSharedPreference(activity);
        return configFile.getBoolean(OFFERS_STATUS, false);
    }

    public static void setIsActiveJourney(Activity activity, boolean isActive) {
        initConfigSharedPreference(activity);
        SharedPreferences.Editor editor = configFile.edit();
        editor.putBoolean(Active_Journey_STATUS, isActive);
        editor.apply();
    }

    public static boolean getIsActiveJourney(Context activity) {
        initConfigSharedPreference(activity);
        return configFile.getBoolean(Active_Journey_STATUS, false);
    }

    private static void initConfigSharedPreference(Context context) {
        configFile = context.getSharedPreferences("configFile", Context.MODE_PRIVATE);
    }
}