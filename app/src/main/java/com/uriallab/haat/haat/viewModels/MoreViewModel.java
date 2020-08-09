package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.uriallab.haat.haat.LocalNotification.TrackingDelegate;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.Auth.LoginActivity;
import com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo.CommentsActivity;
import com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo.EditProfileActivity;
import com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo.MyAccountDetailsActivity;
import com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo.SettingActivity;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.Utilities;

public class MoreViewModel {

    public ObservableBoolean isDriver = new ObservableBoolean(false);

    public ObservableField<String> userName = new ObservableField<>();

    public ObservableField<String> orderNumObservable = new ObservableField<>();

    public ObservableField<String> totalRate = new ObservableField<>();

    public ObservableInt rotation = new ObservableInt(0);
    public ObservableInt rotationRating = new ObservableInt(0);

    public ObservableInt rating = new ObservableInt(1);

    private Activity activity;

    public MoreViewModel(Activity activity) {
        this.activity = activity;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("en"))
            rotation.set(180);
        else
            rotationRating.set(180);

        if (LoginSession.isLoggedIn(activity)) {
            try {
                if (LoginSession.getUserData(activity).getResult().getUserData().isIsDelivery()) {
                    isDriver.set(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            userName.set(LoginSession.getUserData(activity).getResult().getUserData().getUser_Full_Nm());
            totalRate.set(String.valueOf(LoginSession.getUserData(activity).getResult().getUserData().getUser_Count_Rate()));

            try {
                rating.set(LoginSession.getUserData(activity).getResult().getUserData().getUser_Rate());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!LoginSession.getUserData(activity).getResult().getUserData().isIsDelivery())
                orderNumObservable.set(String.valueOf(LoginSession.getUserData(activity).getResult().getAllorders()));
        }
    }

    public void accountDetails() {
        if (LoginSession.isLoggedIn(activity)) {
            if (LoginSession.getUserData(activity).getResult().getUserData().isIsDelivery())
                IntentClass.goToActivity(activity, MyAccountDetailsActivity.class, null);
            else
                Utilities.toastyError(activity, activity.getString(R.string.only_driver));
        }
    }

    public void openComment() {
        IntentClass.goToActivity(activity, CommentsActivity.class, null);
    }

    public void editProfile() {
        if (LoginSession.isLoggedIn(activity))
            IntentClass.goToActivity(activity, EditProfileActivity.class, null);
    }

    public void setting() {
        if (LoginSession.isLoggedIn(activity))
            IntentClass.goToActivity(activity, SettingActivity.class, null);
    }

    public void logout() {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_logout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView alertText = dialog.findViewById(R.id.alert_text);
        TextView yes = dialog.findViewById(R.id.yes_id);
        TextView no = dialog.findViewById(R.id.no_id);

        alertText.setText(activity.getString(R.string.logout));

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSession.clearData(activity);
                IntentClass.goToActivityAndClear(activity, LoginActivity.class, null);
                try {
                    Intent myService = new Intent(activity, TrackingDelegate.class);
                    activity.stopService(myService);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
}