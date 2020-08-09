package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.DriverRegisterModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.RegisterAsDriver.SecondStepActivity;
import com.uriallab.haat.haat.UI.Activities.StoreUsersReviewsActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.Utilities.Validations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class DriverRegisterFirstStepViewModel {

    public ObservableInt rotation = new ObservableInt(0);

    public ObservableField<String> userNameObservable = new ObservableField<>("");
    public ObservableField<String> phoneObservable = new ObservableField<>("");
    public ObservableField<String> emailObservable = new ObservableField<>("");
    public ObservableField<String> birthdayObservable = new ObservableField<>("");
    public ObservableField<String> userNameError = new ObservableField<>();
    public ObservableField<String> phoneError = new ObservableField<>();
    public ObservableField<String> emailError = new ObservableField<>();
    public ObservableField<String> birthdayError = new ObservableField<>();

    public int gender = 0;

    private Activity activity;

    public DriverRegisterFirstStepViewModel(Activity activity) {
        this.activity = activity;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            rotation.set(180);

        if (LoginSession.getUserData(activity).getResult().getUserData().getUser_GenderID() == 1)
            gender = 1;
        else if (LoginSession.getUserData(activity).getResult().getUserData().getUser_GenderID() == 2)
            gender = 2;

        userNameObservable.set(LoginSession.getUserData(activity).getResult().getUserData().getUser_Full_Nm());
        emailObservable.set(LoginSession.getUserData(activity).getResult().getUserData().getUser_Mail());
        phoneObservable.set(LoginSession.getUserData(activity).getResult().getUserData().getUser_Phone());
        birthdayObservable.set(LoginSession.getUserData(activity).getResult().getUserData().getUser_BirthDate());
    }

    public void nextStep() {
        if (validate())
            checkEmailRequest();
    }

    private void checkEmailRequest() {
        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("User_Mail", emailObservable.get());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Authorization/CheckEmail", jsonParams, new TextHttpResponseHandler() {
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
                                checkEmailRequest();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);
                Intent intent = new Intent(activity, SecondStepActivity.class);
                Gson gson = new Gson();
                DriverRegisterModel driverRegisterModel = new DriverRegisterModel();

                driverRegisterModel.setUser_BirthDate(birthdayObservable.get());
                driverRegisterModel.setUser_Full_Nm(userNameObservable.get());
                driverRegisterModel.setUser_GenderID(gender);
                driverRegisterModel.setUser_Phone(phoneObservable.get());
                driverRegisterModel.setUser_Mail(emailObservable.get());

                String myJson = gson.toJson(driverRegisterModel);
                intent.putExtra("myjson", myJson);
                activity.startActivity(intent);
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

        if (userNameObservable.get().isEmpty() || phoneObservable.get().isEmpty() ||
                emailObservable.get().isEmpty() || gender == 0) {

            if (userNameObservable.get().isEmpty())
                userNameError.set(activity.getResources().getString(R.string.required));

            if (emailObservable.get().isEmpty())
                emailError.set(activity.getResources().getString(R.string.required));

            if (phoneObservable.get().isEmpty())
                phoneError.set(activity.getResources().getString(R.string.required));

            if (birthdayObservable.get().isEmpty())
                birthdayError.set(activity.getResources().getString(R.string.enter_birthday));

            if (gender == 0)
                Utilities.toastyRequiredFieldCustom(activity, activity.getString(R.string.choose_gender));

            return false;
        }

        if (!Validations.isValidEmail(emailObservable.get())) {
            emailError.set(activity.getResources().getString(R.string.invalid_mail));
            return false;
        }

        return true;
    }

    public void back() {
        Utilities.hideKeyboard(activity);
        activity.finish();
    }

    public void getBirthday() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        birthdayObservable.set(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        birthdayError.set(null);
        dpd.show();
    }
}