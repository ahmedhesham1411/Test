package com.uriallab.haat.haat.Utilities;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.Auth.LoginActivity;

/**
 * Created by MAHMOUD on 12/12/2018.
 */

public abstract class Dialogs {


    public static Dialog noInternetDialog;


    public static void showLoginDialog(final Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_must_login);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView alertText = dialog.findViewById(R.id.alert_text);
        TextView yes = dialog.findViewById(R.id.yes_id);
        TextView no = dialog.findViewById(R.id.no_id);

        alertText.setText(activity.getString(R.string.logout_));

        yes.setOnClickListener(v -> {
            IntentClass.goToActivityAndClear(activity, LoginActivity.class, null);
            dialog.dismiss();
        });

        no.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }


    public static void showLoading(Activity activity, LoadingDialog loadingDialog) {
        try {
            loadingDialog.show(((AppCompatActivity) activity).getSupportFragmentManager(), "showLoading");

        } catch (Exception e) {

        }
    }

    public static void dismissLoading(LoadingDialog loadingDialog) {
        try {
            if (loadingDialog != null)
                loadingDialog.dismiss();
            loadingDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showToast(String message, Activity activity) {
        if (message != null && !message.equals(""))
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public interface DialogListener {
        void onChoose(int pos);
    }
}