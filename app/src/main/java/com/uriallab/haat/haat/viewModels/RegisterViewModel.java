package com.uriallab.haat.haat.viewModels;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.NationalityModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.Auth.RegisterActivity;
import com.uriallab.haat.haat.UI.MainActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.Utilities.Validations;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mahmoud on 4/22/2020.
 */

public class RegisterViewModel {

    public ObservableField<String> userNameObservable = new ObservableField<>("");
    public ObservableField<String> phoneObservable = new ObservableField<>("");
    public ObservableField<String> emailObservable = new ObservableField<>("");
    public ObservableField<String> birthdayObservable = new ObservableField<>("");
    public ObservableField<String> passwordObservable = new ObservableField<>("");
    public ObservableField<String> confirmPasswordObservable = new ObservableField<>("");
    public ObservableField<String> userNameError = new ObservableField<>();
    public ObservableField<String> phoneError = new ObservableField<>();
    public ObservableField<String> emailError = new ObservableField<>();
    public ObservableField<String> birthdayError = new ObservableField<>();
    public ObservableField<String> passwordError = new ObservableField<>();
    public ObservableField<String> confirmPasswordError = new ObservableField<>();

    private RegisterActivity activity;

    private int mYear, mMonth, mDay;

    public int gender = 0;
    public String city = "0";
    public String region = "0";

    private String phone;

    public List<String> regionList = new ArrayList<>();
    public List<String> regionIdList = new ArrayList<>();
    public List<String> cityList = new ArrayList<>();
    public List<String> cityIdList = new ArrayList<>();
    public ObservableInt rotation = new ObservableInt(0);

    public RegisterViewModel(RegisterActivity activity, String phone) {
        this.activity = activity;
        this.phone = phone;

        getRegion();
    }

    public void register() {

        if (validate())
            registerRequest();

    }

    public void back() {
        activity.finish();
    }

    private void registerRequest() {
        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("User_Full_Nm", userNameObservable.get());
            jsonParams.put("User_Phone", phone);
//            jsonParams.put("User_BirthDate", birthdayObservable.get());
            jsonParams.put("User_GenderID", gender);
            jsonParams.put("User_Mail", emailObservable.get());
            jsonParams.put("User_CityID", city);
            jsonParams.put("User_RegionID", region);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Authorization/Register", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    case 400:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.getJSONObject("error").has("Message"))
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
                            if (jsonObject.getJSONObject("error").has("Message"))
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
                                registerRequest();
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
                    LoginSession.setIsLoggedIn(activity, true);
                    LoginSession.setToken(activity, jsonObject.getJSONObject("result").getString("User_AccessToken"));

                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("isHome", true);
                    activity.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        if (userNameObservable.get().isEmpty() || emailObservable.get().isEmpty() || region.equals("0") || city.equals("0")) {
            if (userNameObservable.get().isEmpty())
                userNameError.set(activity.getResources().getString(R.string.required));
            if (emailObservable.get().isEmpty())
                emailError.set(activity.getResources().getString(R.string.required));

            if (region.equals("0"))
                Utilities.toastyError(activity, activity.getResources().getString(R.string.please_choose_region));

            if (city.equals("0"))
                Utilities.toastyError(activity, activity.getResources().getString(R.string.please_choose_city));

            return false;
        }

        if (!Validations.isValidEmail(emailObservable.get())) {
            emailError.set(activity.getResources().getString(R.string.invalid_mail));
            return false;
        }


        return true;
    }

    public void getBirthday() {
        Utilities.hideKeyboard(activity);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

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


    private void getRegion() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, "Setting/GetRegions", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getRegion();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<NationalityModel>() {
                }.getType();
                NationalityModel data = new Gson().fromJson(responseString, dataType);

                regionList.clear();
                regionIdList.clear();
                regionList.add(activity.getResources().getString(R.string.please_choose_region));
                regionIdList.add("0");

                if (data.getResult().getData().size() > 0) {
                    for (int i = 0; i < data.getResult().getData().size(); i++) {
                        regionList.add(data.getResult().getData().get(i).getName());
                        regionIdList.add(data.getResult().getData().get(i).getId());
                    }
                }
                activity.adapterRegion.notifyDataSetChanged();
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

    public void getCity() {
        final LoadingDialog loadingDialog = new LoadingDialog();

        APIModel.getMethod(activity, "Setting/GetCities?regionId=" + region, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, () -> getCity());
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<NationalityModel>() {
                }.getType();
                NationalityModel data = new Gson().fromJson(responseString, dataType);

                cityList.clear();
                cityIdList.clear();
                cityList.add(activity.getResources().getString(R.string.please_choose_city));
                cityIdList.add("0");

                if (data.getResult().getData().size() > 0) {
                    for (int i = 0; i < data.getResult().getData().size(); i++) {
                        cityList.add(data.getResult().getData().get(i).getName());
                        cityIdList.add(data.getResult().getData().get(i).getId());
                    }
                }
                activity.adapterCity.notifyDataSetChanged();
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