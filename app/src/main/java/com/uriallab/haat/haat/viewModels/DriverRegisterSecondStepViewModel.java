package com.uriallab.haat.haat.viewModels;

import android.content.Intent;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.CitiesModel;
import com.uriallab.haat.haat.DataModels.DriverRegisterModel;
import com.uriallab.haat.haat.DataModels.NationalityModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.RegisterAsDriver.SecondStepActivity;
import com.uriallab.haat.haat.UI.Activities.RegisterAsDriver.ThirdStepActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DriverRegisterSecondStepViewModel {

    public ObservableInt rotation = new ObservableInt(0);

    public ObservableField<String> idNumberObservable = new ObservableField<>("");
    public ObservableField<String> stcPhoneObservable = new ObservableField<>("");

    public ObservableField<String> idNumberError = new ObservableField<>();
    public ObservableField<String> stcPhoneError = new ObservableField<>();

    public List<String> nationalityList = new ArrayList<>();
    public List<String> nationalityIdList = new ArrayList<>();
    public List<String> regionList = new ArrayList<>();
    public List<String> regionIdList = new ArrayList<>();

    public List<String> cityList = new ArrayList<>();
    public List<String> cityIdList = new ArrayList<>();

    public List<String> identityList = new ArrayList<>();
    public List<String> identityIdList = new ArrayList<>();

    public String nationality = "0";
    public String region = "0";
    public String city = "0";
    public String identity = "0";

    private SecondStepActivity activity;

    private DriverRegisterModel driverRegisterModel;

    public DriverRegisterSecondStepViewModel(SecondStepActivity activity, DriverRegisterModel driverRegisterModel) {
        this.activity = activity;
        this.driverRegisterModel = driverRegisterModel;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            rotation.set(180);

        nationalityList.add(activity.getResources().getString(R.string.nationality));
        nationalityIdList.add("0");
        regionList.add(activity.getResources().getString(R.string.area));
        regionIdList.add("0");

        getIdentity();

        getNationality();

        getRegion();

    }

    public void nextStep() {
        if (validate()) {
            Intent intent = new Intent(activity, ThirdStepActivity.class);
            Gson gson = new Gson();

            driverRegisterModel.setUser_CountryID(nationality);
            driverRegisterModel.setUser_CityID(city);
            driverRegisterModel.setUser_RegionID(region);
            driverRegisterModel.setUser_NationalID_Type(identity);
            driverRegisterModel.setUser_STCPay_Acc(stcPhoneObservable.get());
            driverRegisterModel.setUser_NationalID(idNumberObservable.get());

            String myJson = gson.toJson(driverRegisterModel);
            intent.putExtra("myjson", myJson);
            activity.startActivity(intent);
        }
    }

    private boolean validate() {
        Utilities.hideKeyboard(activity);

        if (idNumberObservable.get().isEmpty() || stcPhoneObservable.get().isEmpty() ||
                nationality.equals("0") || region.equals("0") || city.equals("0") || identity.equals("0")) {

            if (idNumberObservable.get().isEmpty())
                idNumberError.set(activity.getResources().getString(R.string.required));

            if (stcPhoneObservable.get().isEmpty())
                stcPhoneError.set(activity.getResources().getString(R.string.required));

            if (nationality.equals("0"))
                Utilities.toastyRequiredFieldCustom(activity, activity.getString(R.string.nationality));

            if (region.equals("0"))
                Utilities.toastyRequiredFieldCustom(activity, activity.getString(R.string.please_choose_region));

            if (city.equals("0"))
                Utilities.toastyRequiredFieldCustom(activity, activity.getString(R.string.please_choose_city));

            if (identity.equals("0"))
                Utilities.toastyRequiredFieldCustom(activity, activity.getString(R.string.please_choose_identity_type));

            return false;
        }

        return true;
    }

    private void getNationality() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, "Setting/GetCountry", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, () -> getNationality());
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<NationalityModel>() {
                }.getType();
                NationalityModel data = new Gson().fromJson(responseString, dataType);

                for (int i = 0; i < data.getResult().getData().size(); i++) {
                    nationalityList.add(data.getResult().getData().get(i).getName());
                    nationalityIdList.add(data.getResult().getData().get(i).getId());
                }

                activity.nationalityAdapter.notifyDataSetChanged();
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

    private void getRegion() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, "Setting/GetRegions", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, () -> getRegion());
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<CitiesModel>() {
                }.getType();
                CitiesModel data = new Gson().fromJson(responseString, dataType);

                regionList.clear();
                regionIdList.clear();
                regionList.add(activity.getResources().getString(R.string.area));
                regionIdList.add("0");

                if (data.getResult().getData().size() > 0) {
                    for (int i = 0; i < data.getResult().getData().size(); i++) {
                        regionList.add(data.getResult().getData().get(i).getName());
                        regionIdList.add(data.getResult().getData().get(i).getId());
                    }
                }

                activity.areaAdapter.notifyDataSetChanged();
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
                activity.cityAdapter.notifyDataSetChanged();
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

    public void getIdentity() {
        final LoadingDialog loadingDialog = new LoadingDialog();

        APIModel.getMethod(activity, "Setting/GetIdentityTypes", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, () -> getIdentity());
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<NationalityModel>() {
                }.getType();
                NationalityModel data = new Gson().fromJson(responseString, dataType);

                identityList.clear();
                identityIdList.clear();
                identityList.add(activity.getResources().getString(R.string.please_choose_identity_type));
                identityIdList.add("0");

                if (data.getResult().getData().size() > 0) {
                    for (int i = 0; i < data.getResult().getData().size(); i++) {
                        identityList.add(data.getResult().getData().get(i).getName());
                        identityIdList.add(data.getResult().getData().get(i).getId());
                    }
                }
                activity.identityAdapter.notifyDataSetChanged();
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

    public void back() {
        Utilities.hideKeyboard(activity);
        activity.finish();
    }
}