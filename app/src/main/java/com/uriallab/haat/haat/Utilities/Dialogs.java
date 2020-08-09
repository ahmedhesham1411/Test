package com.uriallab.haat.haat.Utilities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.widget.Toast;


import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.SplashActivity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by MAHMOUD on 12/12/2018.
 */

public abstract class Dialogs {


    public static Dialog noInternetDialog;


    public static void showLoginDialog(final Activity activity) {

    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.MyAlertDialogTheme));
        builder
                .setMessage(activity.getResources().getString(R.string.must_login))
                .setPositiveButton(activity.getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                IntentClass.goToActivity(activity, SplashActivity.class, null);
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton(activity.getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .create().show();
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