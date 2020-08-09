package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.LoginModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.Auth.CodeActivity;
import com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo.PrivacyUseActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by Mahmoud on 4/22/2020.
 */

public class LoginViewModel {

    public ObservableField<String> phoneObservable = new ObservableField<>("");
    public ObservableField<String> phoneObservableError = new ObservableField<>();

    private Activity activity;

    public LoginViewModel(Activity activity) {
        this.activity = activity;
    }

    public void login() {

        if (validate())
            loginRequest();

    }

    private void loginRequest() {

        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            String phone = "+966" + phoneObservable.get();
            jsonParams.put("User_Phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Authorization/Login", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    case 401:
                        Type dataType = new TypeToken<LoginModel>() {
                        }.getType();
                        LoginModel data = new Gson().fromJson(responseString, dataType);

                        boolean isRegistered = true;
                        if (data.getResult().getUser_AccessToken().equals(""))
                            isRegistered = false;
                        else
                            LoginSession.setToken(activity, data.getResult().getUser_AccessToken());

                        Bundle bundle = new Bundle();
                        bundle.putString("code", data.getResult().getCode());
                        String phone = "+966" + phoneObservable.get();
                        bundle.putString("phone", phone);
                        bundle.putBoolean("isRegistered", isRegistered);
                        phoneObservable.set("");
                        IntentClass.goToActivity(activity, CodeActivity.class, bundle);
                        break;
                    case 400:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.getJSONObject("error").has("Message"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
                            else
                                Utilities.toastyError(activity, responseString + "  ");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                loginRequest();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<LoginModel>() {
                }.getType();
                LoginModel data = new Gson().fromJson(responseString, dataType);

                boolean isRegistered = true;
                if (data.getResult().getUser_AccessToken().equals(""))
                    isRegistered = false;
                else
                    LoginSession.setToken(activity, data.getResult().getUser_AccessToken());

                Bundle bundle = new Bundle();
                bundle.putString("code", data.getResult().getCode());
                String phone = "+966" + phoneObservable.get();
                bundle.putString("phone", phone);
                bundle.putBoolean("isRegistered", isRegistered);
                phoneObservable.set("");
                IntentClass.goToActivity(activity, CodeActivity.class, bundle);
            }

            @Override
            public void onStart() {
                super.onStart();
                Dialogs.showLoading(activity, loadingDialog);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Dialogs.dismissLoading(loadingDialog);
            }
        });
    }

    private boolean validate() {

        Utilities.hideKeyboard(activity);

        boolean flag = true;

        if (phoneObservable.get().isEmpty()) {
            flag = false;

            phoneObservableError.set(activity.getResources().getString(R.string.enter_phone_number));
        }

        return flag;
    }

    public void register() {
    }

    public void policy() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isPolicyUse", true);
        IntentClass.goToActivity(activity, PrivacyUseActivity.class, bundle);
    }
}