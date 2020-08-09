package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class ChangePasswordViewModel {

    public ObservableInt rotation = new ObservableInt(0);

    public ObservableField<String> oldPasswordObservable = new ObservableField<>("");
    public ObservableField<String> newPasswordObservable = new ObservableField<>("");
    public ObservableField<String> confirmNewPasswordObservable = new ObservableField<>("");
    public ObservableField<String> oldPasswordError = new ObservableField<>();
    public ObservableField<String> newPasswordError = new ObservableField<>();
    public ObservableField<String> confirmNewPasswordError = new ObservableField<>();

    private Activity activity;

    public ChangePasswordViewModel(Activity activity) {
        this.activity = activity;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            rotation.set(180);

    }

    public void editPassword() {
        if (validate())
            editRequest();
    }

    private void editRequest() {
        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("currentPassword", oldPasswordObservable.get());
            jsonParams.put("newPassword", newPasswordObservable.get());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "services/app/Profile/ChangePassword", jsonParams, new TextHttpResponseHandler() {
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
                    case 500:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.has("error"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
                            else
                                Utilities.toastyError(activity, responseString + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                editRequest();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Utilities.toastySuccess(activity, activity.getString(R.string.changed_successfully));

                activity.finish();
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

    private boolean validate() {
        Utilities.hideKeyboard(activity);

        if (oldPasswordObservable.get().isEmpty() || newPasswordObservable.get().isEmpty() ||
                confirmNewPasswordObservable.get().isEmpty()) {

            if (oldPasswordObservable.get().isEmpty())
                oldPasswordError.set(activity.getResources().getString(R.string.required));

            if (newPasswordObservable.get().isEmpty())
                newPasswordError.set(activity.getResources().getString(R.string.required));

            if (confirmNewPasswordObservable.get().isEmpty())
                confirmNewPasswordError.set(activity.getResources().getString(R.string.required));

            return false;
        }

        if (newPasswordObservable.get().length() < 6 || confirmNewPasswordObservable.get().length() < 6) {

            if (newPasswordObservable.get().length() < 6)
                newPasswordError.set(activity.getResources().getString(R.string.password_minimum));

            if (confirmNewPasswordObservable.get().length() < 6)
                confirmNewPasswordError.set(activity.getResources().getString(R.string.password_minimum));

            return false;
        }

        if (!newPasswordObservable.get().equals(confirmNewPasswordObservable.get())) {

            newPasswordError.set(activity.getResources().getString(R.string.password_mismatch));
            confirmNewPasswordError.set(activity.getResources().getString(R.string.password_mismatch));

            return false;
        }

        return true;
    }

    public void back() {
        activity.finish();
    }
}