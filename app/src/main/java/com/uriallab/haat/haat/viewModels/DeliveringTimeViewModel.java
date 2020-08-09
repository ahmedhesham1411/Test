package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.TimeModel;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.makeOrder.DeliveringTimeActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.databinding.ObservableInt;

public class DeliveringTimeViewModel {
    public ObservableInt rotate = new ObservableInt(0);

    private DeliveringTimeActivity activity;

    public ArrayList<Integer> timeListId = new ArrayList<>();

    public DeliveringTimeViewModel(DeliveringTimeActivity activity) {
        this.activity = activity;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            rotate.set(180);

        getTimeRequest();
    }

    private void getTimeRequest() {
        final LoadingDialog loadingDialog = new LoadingDialog();

        APIModel.getMethod(activity, "Client/GetDuration", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
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
                                getTimeRequest();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<TimeModel>() {
                }.getType();
                TimeModel data = new Gson().fromJson(responseString, dataType);

                ArrayList<String> timeList = new ArrayList<>();

                timeListId.clear();
                for (int i = 0; i < data.getResult().getDurations().size(); i++) {
                    timeList.add(data.getResult().getDurations().get(i).getDuration_Nm());
                    timeListId.add(data.getResult().getDurations().get(i).getDurationUID());
                }
                activity.wheelTime(timeList);
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

    public void confirmTime() {
        activity.confirmTime();
    }

    public void back() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("time", "none");
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }
}