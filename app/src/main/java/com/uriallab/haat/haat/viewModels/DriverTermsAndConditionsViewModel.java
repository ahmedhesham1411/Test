package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.TermsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.RegisterAsDriver.FirstStepActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;

import java.lang.reflect.Type;

public class DriverTermsAndConditionsViewModel {

    public ObservableField<String> textObservable = new ObservableField<>();

    public ObservableInt rotation = new ObservableInt(0);

    public boolean isChecked = false;

    private Activity activity;

    public DriverTermsAndConditionsViewModel(Activity activity) {
        this.activity = activity;

        termsAndPrivacy();

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar")) {
            rotation.set(180);
        }
    }

    private void termsAndPrivacy() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, "Setting/GetCaptainTerms", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                termsAndPrivacy();
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

    public void nextStep(){
        if (isChecked)
            IntentClass.goToActivity(activity, FirstStepActivity.class, null);
        else
            Utilities.toastyError(activity, activity.getString(R.string.please_accept_condition));
    }

    public void back() {
        activity.finish();
    }
}