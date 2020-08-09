package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.TermsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;

import java.lang.reflect.Type;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class PrivacyUseViewModel {

    public ObservableField<String> textObservable = new ObservableField<>();
    public ObservableField<String> titleObservable = new ObservableField<>();

    public ObservableInt rotation = new ObservableInt(0);

    private Activity activity;

    public PrivacyUseViewModel(Activity activity, boolean isPolicyUse) {
        this.activity = activity;

        if (isPolicyUse) {
            termsAndPrivacy("Setting/GetTerms");
            titleObservable.set(activity.getString(R.string.policy));
        } else {
            termsAndPrivacy("Setting/GetPolicies");
            titleObservable.set(activity.getString(R.string.privacy));
        }

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar")) {
            rotation.set(180);
        }
    }

    private void termsAndPrivacy(final String url) {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                termsAndPrivacy(url);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<TermsModel>() {
                }.getType();
                TermsModel data = new Gson().fromJson(responseString, dataType);

                textObservable.set(data.getResult().getItmes());

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

    public void back() {
        activity.finish();
    }
}