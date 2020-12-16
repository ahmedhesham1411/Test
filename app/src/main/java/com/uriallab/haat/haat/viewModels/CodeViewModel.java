package com.uriallab.haat.haat.viewModels;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableInt;

import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.Auth.CodeActivity;
import com.uriallab.haat.haat.UI.Activities.Auth.RegisterActivity;
import com.uriallab.haat.haat.UI.Activities.SplashActivity;
import com.uriallab.haat.haat.UI.MainActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mahmoud on 4/25/2020.
 */

public class CodeViewModel {

    private boolean timerRunning = false;

    private CodeActivity activity;

    private boolean isRegistered;

    private String serverCode;
    private String phone;
    public ObservableInt rotation = new ObservableInt(0);

    public CodeViewModel(final CodeActivity activity, String serverCode, String phone, boolean isRegistered) {
        this.activity = activity;
        this.serverCode = serverCode;
        this.phone = phone;
        this.isRegistered = isRegistered;

        activity.binding.serverCodeTxt.setText(serverCode);

        restartTimer();


    }

    public void back() {
        activity.finish();
    }

    public void checkCodeAndGo() {
        if (phone.equals("00000000")){
            Toast.makeText(activity, "hi", Toast.LENGTH_SHORT).show();
        }



        String localCode = activity.binding.num1Edt.getText().toString() +
                activity.binding.num2Edt.getText().toString() +
                activity.binding.num3Edt.getText().toString() +
                activity.binding.num4Edt.getText().toString() +
                activity.binding.num5Edt.getText().toString();

        Log.e("Code", localCode + "\t server code " + serverCode);

        Utilities.hideKeyboard(activity);

        if (localCode.isEmpty()) {
            Utilities.toastyError(activity, activity.getString(R.string.enter_code_));
            return;
        }

        if (localCode.equals(serverCode)) {
            if (isRegistered) {
                LoginSession.setIsLoggedIn(activity, true);

                Intent intent = new Intent(activity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("isHome", true);
                activity.startActivity(intent);
            } else {
                Bundle bundle = new Bundle();
                String phone1 = phone;
                bundle.putString("phone", phone1);
                IntentClass.goToActivity(activity, RegisterActivity.class, bundle);
                activity.finish();
            }
        } else
            Utilities.toastyError(activity, activity.getString(R.string.invalid_code));

    }

    private void getCode() {
        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("User_Phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Authorization/Login", jsonParams, new TextHttpResponseHandler() {
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
                                getCode();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    serverCode = jsonObject.getJSONObject("result").getString("Code");

                    activity.binding.serverCodeTxt.setText(serverCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                restartTimer();
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

    public void resendAgain() {
        if (!timerRunning)
            getCode();
    }

    private void restartTimer() {
        if (!timerRunning) {
            new CountDownTimer(2 * 60 * 1000 + 1000, 1000) {
                @Override
                public void onFinish() {
                    timerRunning = false;
                    activity.binding.durationTxt.setText(activity.getString(R.string.resend_again));
                }

                @Override
                public void onTick(long millisUntilFinished) {
                    timerRunning = true;
                    long seconds = (millisUntilFinished / 1000);
                    long minutes = seconds / 60;
                    seconds = seconds % 60;
                    activity.binding.durationTxt.setText(activity.getString(R.string.resend_in) + " " +
                            String.format("%02d", minutes)
                            + ":" + String.format("%02d", seconds));
                }
            }.start();
        }
    }
}