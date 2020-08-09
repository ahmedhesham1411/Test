package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.RegisterUrlModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.AddCouponActivity;
import com.uriallab.haat.haat.UI.Activities.RegisterAsDriver.TermsAndConditionsActivity;
import com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo.ComplaintListActivity;
import com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo.ContactUsActivity;
import com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo.LanguageActivity;
import com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo.PrivacyUseActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.LoadingDialog;

import java.lang.reflect.Type;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;

public class SettingViewModel {

    public ObservableBoolean isDriver = new ObservableBoolean(false);

    public ObservableInt rotation = new ObservableInt(0);
    public ObservableInt arrowRotation = new ObservableInt(180);

    private Activity activity;

    public SettingViewModel(Activity activity) {
        this.activity = activity;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar")) {
            rotation.set(180);
            arrowRotation.set(0);
        }

        isDriver.set(LoginSession.getUserData(activity).getResult().getUserData().isIsDelivery());
    }

    public void appLanguage() {
        IntentClass.goToActivity(activity, LanguageActivity.class, null);
    }

    public void addCoupon() {
        IntentClass.goToActivity(activity, AddCouponActivity.class, null);
    }

    public void complaintsList() {
        IntentClass.goToActivity(activity, ComplaintListActivity.class, null);
    }

    public void contactUs() {
        IntentClass.goToActivity(activity, ContactUsActivity.class, null);
    }

    public void privacyPolicy() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isPolicyUse", false);
        IntentClass.goToActivity(activity, PrivacyUseActivity.class, bundle);
    }

    public void policyUSe() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isPolicyUse", true);
        IntentClass.goToActivity(activity, PrivacyUseActivity.class, bundle);
    }

    public void rateApp() {
        IntentClass.rateApp(activity);
    }

    public void howToWork() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_subscribe);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView alertText = dialog.findViewById(R.id.alert_text);
        TextView yes = dialog.findViewById(R.id.yes_id);
        TextView no = dialog.findViewById(R.id.no_id);

        alertText.setText(activity.getString(R.string.subscribe_text));

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentClass.goToActivity(activity, TermsAndConditionsActivity.class, null);
                //getRegisterUrl();
                dialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void back() {
        activity.finish();
    }

    private void getRegisterUrl() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, "Driver/GetRegisterUrl", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getRegisterUrl();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<RegisterUrlModel>() {
                }.getType();
                RegisterUrlModel data = new Gson().fromJson(responseString, dataType);

                IntentClass.goToLink(activity, data.getResult().getRegisterurl());
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
}