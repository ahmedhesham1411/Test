package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.FavModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.makeOrder.FavouritesActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import androidx.databinding.ObservableInt;

public class FavouritesViewModel {

    public ObservableInt rotate = new ObservableInt(0);

    private FavouritesActivity activity;

    public FavouritesViewModel(FavouritesActivity activity){
        this.activity = activity;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            rotate.set(180);

        getFav();
    }

    private void getFav() {
        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("User_ID", LoginSession.getUserData(activity).getResult().getUserData().getUserUID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Client/GetFavoriteLocations", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getFav();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<FavModel>() {
                }.getType();
                FavModel data = new Gson().fromJson(responseString, dataType);
                if (data.getResult().getFavoritelocations().size() > 0)
                    activity.initRecycler(data.getResult().getFavoritelocations());
                else
                    Utilities.toastyError(activity, activity.getString(R.string.no_data));
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
       activity.onBackPressed();
    }
}