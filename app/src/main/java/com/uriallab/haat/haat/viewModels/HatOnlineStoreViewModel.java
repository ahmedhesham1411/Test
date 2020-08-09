package com.uriallab.haat.haat.viewModels;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.HatServiceModel;
import com.uriallab.haat.haat.DataModels.TimeModel;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.Updates.HatOnlineStoreActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HatOnlineStoreViewModel {

    public ObservableInt rotate = new ObservableInt(0);

    public ObservableBoolean isNoData = new ObservableBoolean(false);

    private HatOnlineStoreActivity activity;

    public HatOnlineStoreViewModel(HatOnlineStoreActivity activity) {
        this.activity = activity;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            rotate.set(180);

        getHatServices();
    }

    private void getHatServices() {
        final LoadingDialog loadingDialog = new LoadingDialog();

        APIModel.getMethod(activity, "api/HaatCard", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                isNoData.set(true);
                switch (statusCode) {
                    case 400:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.has("error"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
                            else
                                Utilities.toastyError(activity, responseString + "    ");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getHatServices();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<HatServiceModel>() {
                }.getType();
                HatServiceModel data = new Gson().fromJson(responseString, dataType);

                if (data.getResult().getHaatCardCategory().size() > 0)
                    activity.initRecycler(data.getResult().getHaatCardCategory());
                else
                    isNoData.set(true);
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

    public void back(){
        Utilities.hideKeyboard(activity);
        activity.finish();
    }
}