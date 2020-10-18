package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;

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

        if (LoginSession.isLoggedIn(activity))
            isDriver.set(LoginSession.getUserData(activity).getResult().getUserData().isIsDelivery());
    }

    public void appLanguage() {
        IntentClass.goToActivity(activity, LanguageActivity.class, null);
    }

    public void addCoupon() {
        if (LoginSession.isLoggedIn(activity))
            IntentClass.goToActivity(activity, AddCouponActivity.class, null);
        else
            Dialogs.showLoginDialog(activity);
    }

    public void complaintsList() {
        if (LoginSession.isLoggedIn(activity))
            IntentClass.goToActivity(activity, ComplaintListActivity.class, null);
        else
            Dialogs.showLoginDialog(activity);
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
        if (LoginSession.isLoggedIn(activity)) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_alert_subscribe);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView alertText = dialog.findViewById(R.id.alert_text);
            TextView yes = dialog.findViewById(R.id.yes_id);
            TextView no = dialog.findViewById(R.id.no_id);

            alertText.setText(activity.getString(R.string.subscribe_text));

            yes.setOnClickListener(v -> {
                IntentClass.goToActivity(activity, TermsAndConditionsActivity.class, null);
                //getRegisterUrl();
                dialog.dismiss();
            });

            no.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        } else
            Dialogs.showLoginDialog(activity);
    }

    public void back() {
        activity.finish();
    }
}