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
import com.uriallab.haat.haat.Utilities.IntentClass;
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
    public List<Integer> nationalityIdList = new ArrayList<>();
    public List<String> areaList = new ArrayList<>();
    public List<Integer> areaIdList = new ArrayList<>();

    public int nationality = 0;
    public int area = 0;

    private SecondStepActivity activity;

    private DriverRegisterModel driverRegisterModel;

    public DriverRegisterSecondStepViewModel(SecondStepActivity activity, DriverRegisterModel driverRegisterModel) {
        this.activity = activity;
        this.driverRegisterModel = driverRegisterModel;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            rotation.set(180);

        nationalityList.add(activity.getResources().getString(R.string.nationality));
        nationalityIdList.add(0);
        areaList.add(activity.getResources().getString(R.string.area));
        areaIdList.add(0);

        getNationality();

        getArea();

    }

    public void nextStep() {
        if (validate()) {
            Intent intent = new Intent(activity, ThirdStepActivity.class);
            Gson gson = new Gson();

            driverRegisterModel.setUser_CountryID(nationality);
            driverRegisterModel.setUser_CityID(area);
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
                nationality == 0 || area == 0) {

            if (idNumberObservable.get().isEmpty())
                idNumberError.set(activity.getResources().getString(R.string.required));

            if (stcPhoneObservable.get().isEmpty())
                stcPhoneError.set(activity.getResources().getString(R.string.required));

            if (nationality == 0)
                Utilities.toastyRequiredFieldCustom(activity, activity.getString(R.string.nationality));

            if (area == 0)
                Utilities.toastyRequiredFieldCustom(activity, activity.getString(R.string.area));

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
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getNationality();
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

                for (int i = 0; i < data.getResult().getCountry().size(); i++) {
                    nationalityList.add(data.getResult().getCountry().get(i).getCountry_Ar_Nm());
                    nationalityIdList.add(data.getResult().getCountry().get(i).getCountryUID());
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

    private void getArea() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, "Setting/GetCities", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getArea();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<CitiesModel>() {
                }.getType();
                CitiesModel data = new Gson().fromJson(responseString, dataType);

                areaList.clear();
                areaIdList.clear();
                areaList.add(activity.getResources().getString(R.string.area));
                areaIdList.add(0);

                if (data.getResult().getCities().size() > 0) {
                    for (int i = 0; i < data.getResult().getCities().size(); i++) {
                        areaList.add(data.getResult().getCities().get(i).getCity_Ar_Nm());
                        areaIdList.add(data.getResult().getCities().get(i).getCityUID());
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

    public void back() {
        Utilities.hideKeyboard(activity);
        activity.finish();
    }
}