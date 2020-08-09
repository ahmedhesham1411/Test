package com.uriallab.haat.haat.SharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.uriallab.haat.haat.DataModels.UserModel;


/**
 * Created by MAHMOUD on 12/12/2018.
 */

public class LoginSession {
    private static final String USER_DATA_KEY = "userData";
    private static final String IS_LOGIN_KEY = "isLogin";
    private static final String ACCESS_TOKEN_KEY = "accessTokenKey";
    private static final String EXPIRE_KEY = "expireKey";
    private static final String TOKEN = "password";


    private static SharedPreferences loginFile;

    private static void initLoginSharedPreference(Context context) {
        loginFile = context.getSharedPreferences("loginFile", Context.MODE_PRIVATE);
    }

    public static void setToken(Activity activity, String token) {
        initLoginSharedPreference(activity);
        SharedPreferences.Editor editor = loginFile.edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public static String getToken(Activity activity) {
        initLoginSharedPreference(activity);
        String token = loginFile.getString(TOKEN, "");
        return token;
    }

    public static String getToken(Context activity) {
        initLoginSharedPreference(activity);
        String token = loginFile.getString(TOKEN, "");
        return token;
    }

    public static void setUserData(Activity activity, UserModel user, String accessToken, String expireKey) {
        initLoginSharedPreference(activity);
        SharedPreferences.Editor editor = loginFile.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(USER_DATA_KEY, json);
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.putString(EXPIRE_KEY, expireKey);
        editor.putBoolean(IS_LOGIN_KEY, true);
        editor.apply();
    }

    public static void setUserData(Activity activity, UserModel user) {
        initLoginSharedPreference(activity);
        SharedPreferences.Editor editor = loginFile.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(USER_DATA_KEY, json);
        editor.putBoolean(IS_LOGIN_KEY, true);
        editor.apply();
    }

    public static UserModel getUserData(Context activity) {
        initLoginSharedPreference(activity);
        Gson gson = new Gson();
        String json = loginFile.getString(USER_DATA_KEY, "");
        UserModel userModel = gson.fromJson(json, UserModel.class);

        if (userModel != null)
            return userModel;

        return new UserModel();
    }

    public static void setIsLoggedIn(Context activity, boolean isLoggedIn) {
        initLoginSharedPreference(activity);
        SharedPreferences.Editor editor = loginFile.edit();
        editor.putBoolean(IS_LOGIN_KEY, isLoggedIn);
        editor.apply();
    }

    public static boolean isLoggedIn(Activity activity) {
        initLoginSharedPreference(activity);
        return loginFile.getBoolean(IS_LOGIN_KEY, false);
    }

    public static boolean isLoggedIn(Context activity) {
        initLoginSharedPreference(activity);
        return loginFile.getBoolean(IS_LOGIN_KEY, false);
    }

    public static void clearData(Context activity) {
        initLoginSharedPreference(activity);
        SharedPreferences.Editor editor = loginFile.edit();
        editor.clear();
        editor.apply();
    }

}